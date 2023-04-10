import React, {useEffect, useState} from 'react';
import './create.css';
import {Button} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import buildings from "./buildings";
import {ALL_ROOMS_API, CREATE_ROOM_API, ELIGIBLE_OWNERS_API} from "../../endpoints";

import Alert from 'react-bootstrap/Alert';

function Create(props) {
    const [rooms, setRooms] = useState([]);
    const [users, setUsers] = useState([]);

    const [tippersSpaceId, setTippersSpaceId] = useState('');
    const [userId, setUserId] = useState('');
    const [response, setResponse] = useState('');
    const [wasSuccess, setWasSuccess] = useState(false);
    const [show, setShow] = useState(false);

    function createRoomOwnership() {
        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            // TODO: Use the actual roomType, description etc when available
            body: JSON.stringify({ userId: userId, tippersSpaceId: tippersSpaceId, roomType: 'STUDY', roomName: 'mock name' })
        };
        fetch(CREATE_ROOM_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setWasSuccess(data.success);
                setResponse(data.message);
                setShow(true);
            });
    }

    useEffect(() => {
        fetch(ELIGIBLE_OWNERS_API)
            .then((res) => res.json())
            .then((data) => {
                setUsers(data);
            });
        fetch(ALL_ROOMS_API)
            .then((res) => res.json())
            .then((data) => {
                setRooms(data);
            });
    }, []);

    return (
        <section className="createRoom">
            <Alert show={show} variant={wasSuccess ? 'success' : 'danger'} onClose={() => setShow(false)} dismissible>
                {response}
            </Alert>
            <div id="createRoomTitle">
                <h2>Create your room!</h2>
            </div>
            <div id="selection">
                <Form.Select aria-label="Default select example" onChange={(e) => setUserId(e.target.value)} >
                    <option value=''>Select your User ID here</option>
                    {users.map((item, index) => {
                        return (
                            <option key={index} value={item.userId}>{item.userId}</option>
                        );
                    })}
                </Form.Select>
                <Form.Select aria-label="Default select example" >
                    <option value=''>Select Building</option>
                    {buildings.map((item, index) => {
                        return (
                            <option key={index} value={item.buildingId}>{item.name}</option>
                        );
                    })}
                </Form.Select>
                <Form.Select aria-label="Default select example" onChange={(e) => setTippersSpaceId(e.target.value)}>
                    <option value=''>Select Room</option>
                    {rooms.map((item, index) => {
                        return (
                            <option key={index} value={item.tippersSpaceId}>{item.name}</option>
                        );
                    })}
                </Form.Select>
            </div>
            <div id="createButton">
                <Button variant="primary" size="lg" onClick={createRoomOwnership} disabled={userId === '' || tippersSpaceId === ''} >Create!</Button>
            </div>
        </section>
    );
}

export default Create;