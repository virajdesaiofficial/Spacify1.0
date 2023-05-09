import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import './signin.css';
import {IoLogoGoogle} from "react-icons/all";
import {FORGOT_PASSWORD_API, SEND_VERIFICATION_API, SIGNIN_USER_API, USER_NAME_KEY} from "../../endpoints";
import Alert from "react-bootstrap/Alert";

function SignIn(props) {
    const initialState = {
        password: "",
        userName: "",
        rememberMe: false,
        wasSuccess: false,
        show: false,
        responseMessage: "",
    };
    const [state, setState] = useState(initialState);

    const handleFetch = (url) => {
        if (state.userName) {
            fetch(url)
                .then((res) => res.json())
                .then((data) => {
                    setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message});
                });
        } else {
            setState({...state, wasSuccess: false, show: true, responseMessage: "Please enter user name!"});
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const requestObj = {
            password: state.password,
            userId : state.userName
        };

        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestObj)
        };
        fetch(SIGNIN_USER_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                if (data.success) {
                    global.sessionStorage.setItem(USER_NAME_KEY, state.userName);
                    if (state.rememberMe && global.localStorage) {
                        global.localStorage.setItem(USER_NAME_KEY, state.userName);
                    }
                }
                setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message});
            });
    }

    const handleSendVerification = (e) => {
        e.preventDefault();
        const url = SEND_VERIFICATION_API + state.userName;
        handleFetch(url);
    }

    const handleForgetPassword = (e) => {
        e.preventDefault();
        const url = FORGOT_PASSWORD_API + state.userName;
        handleFetch(url);
    }

    const handleLogout = (e) => {
        if (global.localStorage) {
            global.localStorage.removeItem(USER_NAME_KEY);
        }
        global.sessionStorage.removeItem(USER_NAME_KEY);
        window.location.reload();
    }

    const handleGoogleSignIn = () => {
        console.log("google sign in");
    }

    let loggedUserName = global.sessionStorage.getItem(USER_NAME_KEY);

    if (loggedUserName) {
        return (
            <section className="sign-in">
                <p>You are signed in!</p>
                <Button onClick={(e) => handleLogout(e)}>Sign Out!</Button>
            </section>
        );
    } else {
        return (
            <section className="sign-in">
                <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                       onClose={() => setState({...state, show: false})}
                       dismissible>
                    {state.responseMessage}
                </Alert>
                <div className="google-sign-in">
                    <h2>Sign in with</h2>
                    <IoLogoGoogle className="logo" onClick={handleGoogleSignIn}>Google</IoLogoGoogle>
                    <h4 style={{paddingTop: "0.7rem"}}>or:</h4>
                </div>
                <div className="sign-in-row">
                    <Form className="sign-in-form" onSubmit={e => handleSubmit(e)}>
                        <Form.Group className="mb-3" controlId="formBasicuserName">
                            <Form.Label>User Name</Form.Label>
                            <Form.Control type="text" placeholder="Enter user name"
                                          onChange={e => setState({...state, userName: e.target.value})}/>
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" placeholder="Password"
                                          onChange={e => setState({...state, password: e.target.value})}/>
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicCheckbox">
                            <Form.Check type="checkbox" label="Remember me" onClick={() => setState({...state, rememberMe: !state.rememberMe})}/>
                        </Form.Group>
                        <Button id="sign-in-button" variant="primary" type="submit"
                                disabled={state.password === "" || state.userName === ""}>
                            SIGN IN
                        </Button>
                    </Form>
                </div>
                <p>Not a member? <a href="signUp">Register</a></p>
                <p>Trouble signing in? <a href="" onClick={(e) => handleForgetPassword(e)}>Forgot Password</a></p>
                <p><a href="" onClick={(e) => handleSendVerification(e)}>Send Verification Link</a></p>
            </section>
        );
    }
}

export default SignIn;