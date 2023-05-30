import React, {useState} from 'react';
import './profile.css';
import {Button, Col, Form, Row} from 'react-bootstrap';
import {ImCross, IoIosAddCircle} from "react-icons/all";
import {SAVE_USER_PROFILE_API} from "../../../endpoints";
import Alert from "react-bootstrap/Alert";

function Profile(props) {
  const createMacAddressList = () => {
    let macAddresses = [];
    for (let i=0; i<props.macAddressList.length; i++) {
      macAddresses.push(props.macAddressList[i]);
    }
    return macAddresses;
  }

  const initialState = {
    firstName: props.user.firstName,
    lastName: props.user.lastName,
    totalIncentives: props.user.totalIncentives,
    show: false,
    responseMessage: undefined,
    wasSuccess: false,
    newMacAddresses: createMacAddressList(),
  }

  const [state, setState] = useState(initialState);

  const areEqual = () => {
    let newMacAddresses = [];
    state.newMacAddresses.map(mac => {
      if (mac !== "") {
        newMacAddresses.push(mac);
      }
    });

    if (newMacAddresses.length !== props.macAddressList.length) {
      return false;
    }
    for (let i=0; i<newMacAddresses.length; i++) {
      if (newMacAddresses[i] !== props.macAddressList[i]) {
        return false;
      }
    }
    return true;
  };

  const macAddressVerify = () => {
    const regex1 = /^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$/;
    const regex2 = /^([0-9a-f]{2}[:-]){5}([0-9a-f]{2})$/;
    return state.newMacAddresses.filter(mac => !regex1.test(mac) && !regex2.test(mac)).length !== 0;
  }

  const saveDisabled = () => {
    return (state.firstName === props.user.firstName && state.lastName === props.user.lastName && areEqual()) || macAddressVerify();
  }

  const discardDisabled = () => {
    return state.firstName === props.user.firstName && state.lastName === props.user.lastName && areEqual();
  }

  const handleSave = () => {
    const user = {
      user: {
        userId: props.user.userId,
        firstName: state.firstName === props.user.firstName ? undefined : state.firstName,
        lastName: state.lastName === props.user.lastName ? undefined : state.lastName
      },
      macAddresses: areEqual() ? undefined : state.newMacAddresses.filter(mac => mac !== "")
    };

    const requestHeader = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(user)
    };

    fetch(SAVE_USER_PROFILE_API, requestHeader)
        .then((res) => res.json())
        .then((data) => {
          setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
        });
  };

  const handleDiscard = () => {
    setState(initialState);
  };

  const handleMacAddressChange = (index, value) => {
    let newMacAddresses = [];
    for (let i=0; i<state.newMacAddresses.length; i++) {
      if (i === index) {
        newMacAddresses.push(value);
      } else {
        newMacAddresses.push(state.newMacAddresses[i]);
      }
    }
    setState({...state, newMacAddresses});
  }

  const displayMacAddresses = () => {
    if (state.newMacAddresses) {
      let macAddresses = [];
      state.newMacAddresses.map((mac, i) => {
        macAddresses.push(
            <div className="mac-address-div">
              <Form.Control type="text"
                            className="mac-address-input"
                            value={mac}
                            placeholder="Enter your device's Mac Address"
                            onChange={e => handleMacAddressChange(i, e.target.value)} />
              <ImCross className="delete-cross" onClick={e => removeMacAddress(i)}/>
            </div>
        );
      });
      return macAddresses;
    } else {
      return (
          <div> No registered Mac Addresses found</div>
      );
    }
  }

  const addNewMacAddress = () => {
    if (state.newMacAddresses) {
      let newMacAddresses = state.newMacAddresses;
      newMacAddresses.push('');
      setState({...state, newMacAddresses});
    } else {
      setState({...state, newMacAddresses: ['']});
    }
  };

  const removeMacAddress = (index) => {
    let newMacAddresses = [];
    for (let i=0; i<state.newMacAddresses.length; i++) {
      if (i !== index) {
        newMacAddresses.push(state.newMacAddresses[i]);
      }
    }
    setState({...state, newMacAddresses});
  }

  return (
    <div className="profile">
      <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
             onClose={() => setState({...state, show: false})}
             dismissible>
        {state.responseMessage}
      </Alert>
      <h2>User Profile</h2>
      <Form>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="firstName">
            <Form.Label className="form-label">First Name</Form.Label>
            <Form.Control type="text" value={state.firstName} onChange={(e) => setState({...state, firstName: e.target.value})} />
          </Form.Group>
          <Form.Group as={Col} controlId="lastName">
            <Form.Label className="form-label">Last Name</Form.Label>
            <Form.Control type="text" value={state.lastName} onChange={(e) => setState({...state, lastName: e.target.value})} />
          </Form.Group>
        </Row>
        <Form.Group className="mb-3" controlId="email">
          <Form.Label className="form-label">Email Address</Form.Label>
          <Form.Control type="email" value={props.user.email} disabled={true} />
        </Form.Group>
        <Row className="mb-3">
          <Form.Group as={Col} controlId="userId">
            <Form.Label className="form-label">User Name</Form.Label>
            <Form.Control type="text" value={props.user.userId} disabled={true} />
          </Form.Group>
          <Form.Group as={Col} controlId="access">
            <Form.Label className="form-label">Access Level</Form.Label>
            <Form.Control type="text" value={props.user.accessLevel} disabled={true} />
          </Form.Group>
        </Row>
        <Form.Group className="mb-3" controlId="macAddress">
          <Form.Label className="form-label mac-address-label">Mac Address</Form.Label>
          {displayMacAddresses()}
          <br />
          <IoIosAddCircle style={{color: 'lightgreen'}} size='40px' onClick={e => addNewMacAddress()} />
        </Form.Group>
        <div className="btn">
          <Button variant="primary" onClick={handleSave} disabled={saveDisabled()}>
            Save Changes
          </Button>{" "}
          <Button variant="secondary" onClick={handleDiscard} disabled={discardDisabled()}>
            Discard Changes
          </Button>
        </div>
      </Form>
      {macAddressVerify() ? <p>Please enter valid Mac Addresses</p> : <p />}
    </div>
  );

}

export default Profile;
