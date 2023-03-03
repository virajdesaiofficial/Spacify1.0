import React from 'react';
import './reservation.css';
import ReactDatetimeClass from "react-datetime";
import "react-datetime/css/react-datetime.css";
import moment from "moment";
import {Col, Row} from "react-bootstrap";
import Container from "react-bootstrap/Container";

function Reservation(props) {
    const yesterday = moment().subtract( 1, 'day' );
    const valid = function( current ){
        return current.isAfter( yesterday );
    };
    const inputProps = {
        placeholder: "Select date and time",
    };

    return (
        <Container>
            <Row id="main_row">
                <Col>
                    <div>Rooms which can be reserved</div>
                    <li>
                        <ul>room 1</ul>
                        <ul>room 2</ul>
                        <ul>room 3</ul>
                        <ul>room 4</ul>
                    </li>
                </Col>
                <Col>
                    <Row>
                        <Col>
                            <div>Room rules and other details</div>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <div style={{display: "inline-flex", gap: "2rem"}}>
                                <div>
                                    <label>From</label>
                                    <ReactDatetimeClass isValidDate={valid} inputProps={inputProps} />
                                </div>
                                <div>
                                    <label>To</label>
                                    <ReactDatetimeClass isValidDate={valid} inputProps={inputProps} />
                                </div>
                            </div>
                        </Col>
                    </Row>
                </Col>
            </Row>
        </Container>
    );
}

export default Reservation;