import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Link, Outlet} from "react-router-dom";
import React from "react";
import './navigation.css';
import logo from '../../assets/logo_final.png';
import {USER_NAME_KEY} from "../../endpoints";

function Navigation() {

    const signedInStatus = () => {
        if (global.sessionStorage.getItem(USER_NAME_KEY)) {
            return "Sign Out";
        } else {
            return "Sign In";
        }
    };

    return (
        <div>
            <Navbar variant="dark">
                <Container>
                    <Navbar.Brand><Link to={"/"} className="link"><img src={logo}  alt="Spacify Logo"/></Link></Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link><Link to={"/create"} className="link">Create Room</Link></Nav.Link>
                        <Nav.Link><Link to={"/rules"} className="link">Rule</Link></Nav.Link>
                        <Nav.Link><Link to={"/reserve"} className="link">Reserve</Link></Nav.Link>
                        <Nav.Link><Link to={"/subscribe"} className="link">Subscribe</Link></Nav.Link>
                        <Nav.Link><Link to={"/team"} className="link">Team</Link></Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link><Link to={"/signIn"} className="link">{signedInStatus()}</Link></Nav.Link>
                        <Nav.Link><Link to={"/user"} className="link">Profile</Link></Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}

export default Navigation;