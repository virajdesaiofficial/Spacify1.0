import React, {useState} from 'react';
import './signup.css';
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {IoLogoGoogle} from "react-icons/all";
import {Col, Row} from "react-bootstrap";
import {REGISTER_USER_API} from "../../endpoints";
import Alert from "react-bootstrap/Alert";

// import { useNavigate} from "react-router-dom";

function SignUp(props) {
    // const navigate = useNavigate();
    const [state, setState] = useState({
        password: "",
        verifyPassword: "",
        emailId: "",
        firstName: "",
        lastName: "",
        macAddress: "",
        agreedTC: false,
        agreedPD: false,
        userId: ""
    });
    const [response, setResponse] = useState({wasSuccess: false, show: false, responseMessage: ""});

    const handleSubmit = (e) => {
        e.preventDefault();
        const requestObj = {
            password: state.password,
            emailId: state.emailId,
            userId: state.userId,
            firstName: state.firstName,
            lastName: state.lastName,
            macAddress: state.macAddress
        };

        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestObj)
        };
        fetch(REGISTER_USER_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setResponse({wasSuccess: data.success, show: true, responseMessage: data.message});
            });
        // figure out the redirect between screens
        // if (response.wasSuccess) {
        //     navigate("/signin");
        // }
        // console.log("submitted");
    }

    const handleGoogleSignUp = () => {
        console.log("google sign up");
    };

    const disabled = state.userId === "" || state.password === "" || state.firstName === "" || state.emailId === "" || state.macAddress === "" ||
        !state.agreedTC || !state.agreedPD || state.password !== state.verifyPassword;

    let message = "";
    if (state.password !== state.verifyPassword) {
        message = "Passwords do not match!";
    } else if (!state.agreedTC) {
        message = "Please read and agree the Terms and Conditions of use.";
    } else if (!state.agreedPD) {
        message = "Please read and agree to the Privacy Disclaimer.";
    } else {
        message = "Please fill all the mandatory fields.";
    }

    return (
        <section className="sign-up">
            <Alert show={response.show} variant={response.wasSuccess ? 'success' : 'danger'}
                   onClose={() => setResponse({...response, show: false})}
                   dismissible>
                {response.responseMessage}
            </Alert>
            <div className="sign-up-header">
                <h2>Sign up with</h2>
                <IoLogoGoogle className="logo" onClick={handleGoogleSignUp}>Google</IoLogoGoogle>
                <h4 style={{paddingTop: "0.7rem"}}>or:</h4>
            </div>
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
                            <Form.Check type="checkbox" label="Terms and Conditions" className="required-field" onChange={(e) => setState({...state, agreedTC: !state.agreedTC})} />
                        </Form.Group>
                        <Form.Group as={Col} controlId="formBasicCheckbox2">
                            <Form.Check type="checkbox" label="Privacy Disclosure" className="required-field" onChange={(e) => setState({...state, agreedPD: !state.agreedPD})}/>
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