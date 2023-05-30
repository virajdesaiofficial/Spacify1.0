import React, {useState} from "react";
import "./password.css";
import Form from "react-bootstrap/Form";
import LoadingSpinner from "../../utilities/LoadingSpinner";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import {CHANGE_PASSWORD_API} from "../../../endpoints";
import {Col, Row} from "react-bootstrap";

const Rooms = (props) => {
    const initState = {
        loading: false,
        show: false,
        responseMessage: '',
        wasSuccess: false,
        newPassword: '',
        verifyPassword: '',
        oldPassword: ''
    }

    const [state, setState] = useState(initState);

    const isDisabled = () => {
        return state.newPassword === '' || state.oldPassword === '' || state.newPassword !== state.verifyPassword ;
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setState({...state, loading: true});
        const requestBody = {
            userId: props.userId,
            oldPassword: state.oldPassword,
            newPassword: state.newPassword
        };

        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        };

        fetch(CHANGE_PASSWORD_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
            });
    };


    return (
        <div className="password">
            <LoadingSpinner show={state.loading} />
            <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                   onClose={() => setState({...state, show: false})}
                   dismissible>
                {state.responseMessage}
            </Alert>
            <h2 className="rooms-header">Change Password</h2>
            <Form className="change-from" onSubmit={e => handleSubmit(e)}>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label className="required-field" >Old Password</Form.Label>
                    <Form.Control type="password" placeholder="Enter old password" onChange={e => setState({...state, oldPassword: e.target.value})}/>
                </Form.Group>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="formBasicPassword">
                        <Form.Label className="required-field" >New Password</Form.Label>
                        <Form.Control type="password" placeholder="Enter new password" onChange={e => setState({...state, newPassword: e.target.value})}/>
                    </Form.Group>
                    <Form.Group as={Col} controlId="formBasicPassword">
                        <Form.Label className="required-field" >Verify New Password</Form.Label>
                        <Form.Control type="password" placeholder="Re enter new Password" onChange={e => setState({...state, verifyPassword: e.target.value})}/>
                    </Form.Group>
                </Row>
                <Button id="submit-button" variant="primary" type="submit" disabled={isDisabled()} >
                    Change Password
                </Button>
                {state.newPassword === state.verifyPassword ? state.oldPassword === '' || state.newPassword === '' ? <p>Please fill the mandatory fields!</p> : <p /> : <p>New passwords do not match!</p>}
            </Form>
        </div>
    );
};

export default Rooms;
