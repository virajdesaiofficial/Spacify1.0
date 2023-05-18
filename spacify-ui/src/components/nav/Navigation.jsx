import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Link, Outlet} from "react-router-dom";
import React from "react";
import './navigation.css';
import logo from '../../assets/logo_final.png';

function Navigation() {
    return (
        <div>
            <Navbar variant="dark">
                <Container>
                    <Navbar.Brand href="/"><img src={logo}  alt="Spacify Logo"/></Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link><Link to={"/create"} className="link">Create Room</Link></Nav.Link>
                        <Nav.Link><Link to={"/rules"} className="link">Rule</Link></Nav.Link>
                        <Nav.Link><Link to={"/reserve"} className="link">Reserve</Link></Nav.Link>
                        <Nav.Link><Link to={"/subscribe"} className="link">Subscribe</Link></Nav.Link>
                        <Nav.Link><Link to={"/team"} className="link">Team</Link></Nav.Link>
                    </Nav>
                    <Nav>
                        <Nav.Link><Link to={"/signIn"} className="link">Sign In</Link></Nav.Link>
                        <Nav.Link><Link to={"/user"} className="link">Profile</Link></Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}

export default Navigation;