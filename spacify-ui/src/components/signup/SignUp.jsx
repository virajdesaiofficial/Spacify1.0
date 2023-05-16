import React, {useState} from 'react';
import './signup.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import {Col, Row} from "react-bootstrap";
import {REGISTER_USER_API} from "../../endpoints";
import Alert from "react-bootstrap/Alert";
import LoadingSpinner from "../utilities/LoadingSpinner";

function SignUp(props) {
    const [state, setState] = useState({
        password: "",
        verifyPassword: "",
        emailId: "",
        firstName: "",
        lastName: "",
        macAddress: "",
        agreedTC: false,
        agreedPD: false,
        userId: "",
        showModal: false,
        showModal1: false,
        loading: false,
        wasSuccess: false,
        show: false,
        responseMessage: ""
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        setState({...state, loading: true});
        const requestObj = {
            password: state.password,
            emailId: state.emailId,
            userId: state.userId,
            firstName: state.firstName,
            lastName: state.lastName,
            macAddress: state.macAddress.toLowerCase()
        };

        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestObj)
        };
        fetch(REGISTER_USER_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
            });
    }

    // mac address validation
    const regex1 = /^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$/;
    const regex2 = /^([0-9a-f]{2}[:-]){5}([0-9a-f]{2})$/;
    const macAddressValid = regex1.test(state.macAddress) || regex2.test(state.macAddress);

    const disabled = state.userId === "" || state.password === "" || state.firstName === "" || state.emailId === "" || !macAddressValid ||
        !state.agreedTC || !state.agreedPD || state.password !== state.verifyPassword;

    let message = "";
    if (state.password !== state.verifyPassword) {
        message = "Passwords do not match!";
    } else if (!state.agreedTC) {
        message = "Please read and accept the Terms and Conditions.";
    } else if (!state.agreedPD) {
        message = "Please read and accept the Privacy Disclaimer.";
    } else if (!macAddressValid) {
        message = "Please enter valid Mac Address.";
    } else {
        message = "Please fill all the mandatory fields.";
    }

    const handlePrivacy = (e) => {
        e.preventDefault();
        setState({...state, showModal: true});
    }

    const handleTerms = (e) => {
        e.preventDefault();
        setState({...state, showModal1: true});
    }

    const privacyContent = "Using the UCI campus WiFi network generates logs about your device connecting to the WiFi infrastructure. " +
        "OIT uses such data for operational and security purposes. OIT may further anonymize and share such data with research teams building campus level smart services. " +
        "You may use this application to opt-out all or some of your devices from sharing the anonymized Wi-Fi connection information. Please visit the OIT WiFi Security Page (https://www.oit.uci.edu) for more information";

    return (
        <section className="sign-up">
            <LoadingSpinner show={state.loading} />
            <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                   onClose={() => setState({...state, show: false})}
                   dismissible>
                {state.responseMessage}
            </Alert>

            <Modal show={state.showModal}>
                <Modal.Header>
                    <Modal.Title>Privacy Disclosure</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {privacyContent}
                    <br />
                    Your presence and location will be known to the application when you are connected to the UCI WiFi. This is necessary for you to benefit from Spacify and for us to provide you with Spacify's features.
                    You can always decline this disclosure and stop the signing in process here.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setState({...state, agreedPD: false, showModal: false})}>
                        Decline
                    </Button>
                    <Button variant="primary" onClick={() => setState({...state, agreedPD: true, showModal: false})}>
                        Accept
                    </Button>
                </Modal.Footer>
            </Modal>

            <Modal show={state.showModal1}>
                <Modal.Header>
                    <Modal.Title>Terms & Conditions</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <ul>
                        <li>Each user can create one account using an unique email.</li>
                        <li>Incentives and credit will be awarded on the sole discretion of Spacify.</li>
                        <li>The incentive structure can be changed by Spacify without the need of providing prior notice to the user.</li>
                        <li>The application is to be used as a standalone application. Any integration with other applications need written approval from Spacify.</li>
                        <li>Users found violating Spacify's intended use will be banned from using the application.</li>
                        <li>The Spacify team can be reached via email on <a href = "mailto: spacifycorp@gmail.com">spacifycorp@gmail.com</a> for any further information.</li>
                    </ul>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setState({...state, agreedTC: false, showModal1: false})}>
                        Decline
                    </Button>
                    <Button variant="primary" onClick={() => setState({...state, agreedTC: true, showModal1: false})}>
                        Accept
                    </Button>
                </Modal.Footer>
            </Modal>

            <h2 className="sign-up-header">Sign Up!</h2>
            <div className="sign-up-row">
                <Form className="sign-up-form" onSubmit={e => handleSubmit(e)}>
                    <Row className="mb-3">
                        <Form.Group as={Col} controlId="formFirstName">
                            <Form.Label className="required-field" >First Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter your first name" onChange={e => setState({...state, firstName: e.target.value})}/>
                        </Form.Group>
                        <Form.Group as={Col} controlId="formSecondName">
                            <Form.Label>Last Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter your last name" onChange={e => setState({...state, lastName: e.target.value})}/>
                        </Form.Group>
                    </Row>
                    <Form.Group className="mb-3" controlId="text">
                        <Form.Label className="required-field" >User Name</Form.Label>
                        <Form.Control type="text" placeholder="Enter user name" onChange={e => setState({...state, userId: e.target.value})}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formBasicEmail">
                        <Form.Label className="required-field" >Email address</Form.Label>
                        <Form.Control type="email" placeholder="Enter email" onChange={e => setState({...state, emailId: e.target.value})}/>
                        <Form.Text className="text-muted" >
                            We'll never share your email with anyone else.
                        </Form.Text>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formBasicPassword">
                        <Form.Label className="required-field" >Password</Form.Label>
                        <Form.Control type="password" placeholder="Set Password" onChange={e => setState({...state, password: e.target.value})}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formVerifyPassword">
                        <Form.Label className="required-field" >Re-Enter Password</Form.Label>
                        <Form.Control type="password" placeholder="Verify Password" onChange={e => setState({...state, verifyPassword: e.target.value})}/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="formMacAddress">
                        <Form.Label className="required-field" >Mac Address</Form.Label>
                        <Form.Control type="text" placeholder="Enter your device's Mac Address" onChange={e => setState({...state, macAddress: e.target.value})}/>
                    </Form.Group>

                    <Row className="mb-3">
                        <Form.Group as={Col} controlId="formBasicCheckbox1">
                            <Form.Check type="checkbox" label="Terms and Conditions" checked={state.agreedTC} className="required-field" onClick={(e) => handleTerms(e)} />
                        </Form.Group>
                        <Form.Group as={Col} controlId="formBasicCheckbox2">
                            <Form.Check type="checkbox" label="Privacy Disclosure" checked={state.agreedPD} className="required-field" onClick={(e) => handlePrivacy(e)}/>
                        </Form.Group>
                    </Row>

                    <Button id="sign-up-button" variant="primary" type="submit" disabled={disabled} >
                        SIGN UP
                    </Button>
                    {disabled ? <p>{message}</p> : <p/>}
                </Form>
            </div>
            <p>Already a member? <a href="signIn">Sign In</a></p>
        </section>
    );
}

export default SignUp;