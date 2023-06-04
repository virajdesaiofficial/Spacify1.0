import React from 'react';
import {Link} from "react-router-dom";

function SignInDisclaimer(props) {
    return (
        <div>
            <h2>{props.header}</h2>
            <div>Click <Link style={{color: 'lightblue'}} to={"/signIn"}>here</Link> to sign in!</div>
            <div>If you are not a user, register yourself <Link style={{color: 'lightblue'}} to={"/signUp"}>here!</Link></div>
        </div>
    );
}

export default SignInDisclaimer;