import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Link, Outlet} from "react-router-dom";
import React from "react";
import './navigation.css';

function Navigation() {
    return (
        <div>
            <Navbar bg="dark" variant="dark">
                <Container>
                    <Navbar.Brand href="/">Spacify</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link><Link to={"/"} className="link">Home</Link></Nav.Link>
                        <Nav.Link><Link to={"/reservation"} className="link">Reserve</Link></Nav.Link>
                        <Nav.Link><Link to={"/team"} className="link">Team</Link></Nav.Link>
                        <Nav.Link><Link to={"/user"} className="link">Profile</Link></Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <Outlet />
        </div>
    );
}

export default Navigation;