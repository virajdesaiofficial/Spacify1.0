import React, {useEffect, useState} from 'react';
import './rules.css';
import {GET_OWNED_ROOMS_API, UPDATE_RULES_API, USER_NAME_KEY} from "../../endpoints";
import LoadingSpinner from "../utilities/LoadingSpinner";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import SignInDisclaimer from "../utilities/SignInDisclaimer";

function Rules() {
    const addedRule = {
        ruleId: undefined,
        incentive: '',
        thresholdValue: '',
        fired: false
    };
    const initialState = {
        rooms: [],
        selectedRoom: '',
        loading: false,
        selectedRule: '',
        wasSuccess: false,
        responseMessage: '',
        show: false,
        initialThresholdValue: undefined,
        initialIncentive: undefined,
        addedRule,
    };

    const [state, setState] = useState(initialState);

    useEffect(() => {
        const userName = global.sessionStorage.getItem(USER_NAME_KEY);
        if (!userName) {
            return;
        }
        const url = GET_OWNED_ROOMS_API + userName;
        setState({...state, loading: true});
        fetch(url)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, rooms: data, loading: false});
            });
    }, []);

    // list of available rules. get from backend later, need to have rule name mapped to different parameters it requires.
    // show input for all parameters for that rule
    const ruleIds = ["Occupancy Rule", "Duration Rule"];

    const handleRuleSelect = (value) => {
        if (state.selectedRoom.rules) {
            let incentive = 0;
            let thresholdValue = 0;
            state.selectedRoom.rules.map(rule => {
                if (rule.ruleId === value) {
                    incentive = rule.incentive;
                    thresholdValue = rule.thresholdValue;
                }
            });

            if (incentive !== 0) {
                setState({
                    ...state,
                    selectedRule: value,
                    initialThresholdValue: thresholdValue,
                    initialIncentive: incentive,
                    addedRule: {
                        ruleId: value,
                        incentive,
                        thresholdValue
                    }
                });
            } else {
                setState({
                    ...state,
                    selectedRule: value,
                    initialThresholdValue: undefined,
                    initialIncentive: undefined,
                    addedRule: {
                        ...addedRule,
                        ruleId: value,
                    }
                });
            }
        } else {
            setState({
                ...state,
                selectedRule: value,
                initialThresholdValue: undefined,
                initialIncentive: undefined,
                addedRule: {
                    ...addedRule,
                    ruleId: value,
                }});
        }
    };

    const selectRule = () => {
        if (state.selectedRoom) {
            return (
                <Form.Select aria-label="select" className="rule-select"  onChange={(e) => handleRuleSelect(e.target.value)} >
                    <option value='' selected={!state.selectedRule} >Select Rule to Edit or Add</option>
                    {ruleIds.map((item, index) => {
                        return (
                            <option key={index} value={item}>{item}</option>
                        );
                    })}
                </Form.Select>
            );
        } else {
            return (<div />);
        }
    };

    const handleThresholdChange = (value) => {
        setState({...state, addedRule: {...state.addedRule, thresholdValue: value}});
    };

    const handleIncentiveChange = (value) => {
        setState({...state, addedRule: {...state.addedRule, incentive: value}});
    };

    const handleRoomSelect = (e) => {
        const roomId = e.target.value;
        let selected = undefined;
        state.rooms.map(room => {
            if (""+room.roomId === roomId) {
                selected = room;
            }
        });
        setState({...state,
            selectedRoom: selected,
            selectedRule: '',
            initialIncentive: undefined,
            initialThresholdValue: undefined,
            addedRule
        });
    };

    const handleUpdateRuleCall = (rules) => {
        const room = {
            userId: global.sessionStorage.getItem(USER_NAME_KEY),
            roomId: state.selectedRoom.roomId,
            rules: rules
        };
        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(room)
        };
        fetch(UPDATE_RULES_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
            });
    };

    const handleSave = () => {
        setState({...state, loading: true});

        let rules = state.selectedRoom.rules;
        if (rules) {
            let ruleUpdated = false;
            rules.map(rule => {
                if (rule.ruleId === state.addedRule.ruleId) {
                    ruleUpdated = true;
                    rule.incentive = state.addedRule.incentive;
                    rule.threshold = state.addedRule.thresholdValue;
                }
            });

            if (!ruleUpdated) {
                rules.push(state.addedRule);
            }
        } else {
            rules = [state.addedRule];
        }

        handleUpdateRuleCall(rules);
    };

    const getPercentageOptions = () => {
        let options = [];
        for (let i=50; i<=100; i+=5)
            options.push(<option key={i} value={i} selected={i === state.addedRule.thresholdValue}>{i}%</option>);
        return options;
    }

    const getIncentiveOptions = () => {
        let options = [];
        for (let i=10; i<=50; i+=5)
            options.push(<option key={i} value={i} selected={i === state.addedRule.incentive} >{i} points</option>);
        return options;
    }

    const handleDelete = () => {
        setState({...state, loading: true});

        let newRules = [];
        if (state.selectedRoom.rules) {
            state.selectedRoom.rules.map(rule => {
                if (rule.ruleId !== state.addedRule.ruleId) {
                    newRules.push(rule);
                }
            });
        }

        handleUpdateRuleCall(newRules);
    };

    const displayDeleteButton = () => {
        if (state.initialThresholdValue !== undefined) {
            return (
                <Button id="delete-button" variant="danger" onClick={e => handleDelete()}>
                    Delete Rule
                </Button>
            );
        } else {
            return (<div />);
        }
    };

    const displayRuleParameters = () => {
        if (state.selectedRule) {
            const disabledSave = state.addedRule.incentive === '' || state.addedRule.thresholdValue === '' ||
                (state.initialIncentive === state.addedRule.incentive && state.initialThresholdValue === state.addedRule.thresholdValue);
            return (
                <div>
                    <div className = "rules-input-container">
                        <label className="rules-label">Threshold</label>
                        <Form.Select aria-label="select" className="rule-select"  onChange={(e) => handleThresholdChange(e.target.value)} >
                            <option key={1} value=''>Choose percentage</option>
                            {getPercentageOptions()}
                        </Form.Select>
                    </div>
                    <div className = "rules-input-container">
                        <label className="rules-label" htmlFor="maxOccupants">Incentive</label>
                        <Form.Select aria-label="select" className="rule-select"  onChange={(e) => handleIncentiveChange(e.target.value)} >
                            <option key={1} value=''>Choose points</option>
                            {getIncentiveOptions()}
                        </Form.Select>
                    </div>
                    <Button id="save-button" variant="primary" type="submit" onClick={e => handleSave()} disabled={disabledSave}>
                        Save Rule
                    </Button>
                    {displayDeleteButton()}
                </div>
            );
        }
    };

    const display = () => {
        if (global.sessionStorage.getItem(USER_NAME_KEY)) {
            return (
                <div className="rules-container">
                    <h1 className="rules-header">Rule Management</h1>
                    <Form.Select aria-label="select" className="rule-select" onChange={(e) => handleRoomSelect(e)} >
                        <option value=''>Select the room</option>
                        {state.rooms.map((item, index) => {
                            return (
                                <option key={index} value={item.roomId}>{item.roomName}</option>
                            );
                        })}
                    </Form.Select>
                    {selectRule()}
                    {displayRuleParameters()}
                </div>
            );
        } else {
            return (
                <div style={{textAlign: 'center'}}>
                    <SignInDisclaimer
                        header = "Please Sign In to create rules for your rooms!"
                    />
                </div>
            );
        }
    }

    return (
        <section className="rules" >
            <LoadingSpinner show={state.loading} />
            <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                   onClose={() => setState({...state, show: false})}
                   dismissible>
                {state.responseMessage}
            </Alert>
            {display()}
        </section>
  );
}

export default Rules;
