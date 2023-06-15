import React from 'react';
import viraj from '../../assets/viraj.jpg';
import sanchi from '../../assets/sanchi.jpg';
import himanshu from '../../assets/Himanshu.jpg';
import samruddhi from '../../assets/samruddhi.jpg';
import './team.css';
import {Col, Row} from "react-bootstrap";

function Team(props) {
    return (
        <div className="team">
            <Row className="main">
                <h1 className="main-title">Team TechTippers!</h1>
            </Row>
            <Row className="desc">
                <h3 className="secondary-title">Who we are</h3>
                <p className="description">We are a team of creatively diverse, driven, innovative software engineers
                    striving to make lasting impact on the world.</p>
            </Row>
            <br />
            <Row className="team-members">
                <Col className="team member-1">
                    <img className="team-image" src={viraj} alt="Viraj Desai" />
                    <p className="name">Viraj Desai</p>
                    <span className="position">Co-Founder</span>
                    <span><a href={"https://www.linkedin.com/in/viraj-desai28/"} target="_blank">LinkedIn</a></span>
                </Col>

                <Col className="team member-2">
                    <img className="team-image" src={sanchi} alt="Sanchi Gupta" />
                    <p className="name">Sanchi Gupta</p>
                    <span className="position">Co-Founder</span>
                    <span><a href={"https://www.linkedin.com/in/sanchi-arun-gupta/"} target="_blank">LinkedIn</a></span>
                </Col>

                <Col className="team member-3">
                    <img className="team-image" src={himanshu} alt="Himanshu Thallam" />
                    <p className="name">Himanshu Thallam</p>
                    <span className="position">Co-Founder</span>
                    <span><a href={"https://www.linkedin.com/in/himanshuthallam/"} target="_blank">LinkedIn</a></span>
                </Col>

                <Col className="team member-4">
                    <img className="team-image" src={samruddhi} alt="Samruddhi Dharankar" />
                    <p className="name">Samruddhi Dharankar</p>
                    <span className="position">Co-Founder</span>
                    <span><a href={"https://www.linkedin.com/in/samruddhi-dharankar/"} target="_blank">LinkedIn</a></span>
                </Col>
            </Row>
        </div>
    );
}

export default Team;