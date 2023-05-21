import React, {useEffect, useState} from 'react';
import './create.css';
import {Button} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import {ALL_BUILDINGS_API, ALL_ROOMS_API, CREATE_ROOM_API, ELIGIBLE_OWNERS_API} from "../../endpoints";

import Alert from 'react-bootstrap/Alert';

function Create(props) {
    const [rooms, setRooms] = useState([]);
    const [users, setUsers] = useState([]);
    const [buildings, setBuildings] = useState([]);


    const [tippersSpaceId, setTippersSpaceId] = useState('');
    const [userId, setUserId] = useState('');
    const [response, setResponse] = useState({wasSuccess: false, show: false, responseMessage: ""});

    function createRoomOwnership() {
        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            // TODO: Use the actual roomType, description etc when available
            body: JSON.stringify({ userId: userId, tippersSpaceId: tippersSpaceId, roomType: 'STUDY', roomName: rooms.roomDescription })
        };
        fetch(CREATE_ROOM_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setResponse({wasSuccess: data.success, show: true, responseMessage: data.message});
            });
    }

    function getRoomsFromBuildingSpaceId(buildingSpaceId) {
        const url = ALL_ROOMS_API + buildingSpaceId;
        fetch(url).then((res) => res.json())
                    .then((data) => {
                          setRooms(data);
                    });

    }

    useEffect(() => {
        fetch(ELIGIBLE_OWNERS_API)
            .then((res) => res.json())
            .then((data) => {
                setUsers(data);
            });
         fetch(ALL_BUILDINGS_API)
              .then((res) => res.json())
              .then((data) => {
                    setBuildings(data);
                    });
    }, []);

    return (
        <section className="createRoom">
            <Alert show={response.show} variant={response.wasSuccess ? 'success' : 'danger'}
                   onClose={() => setResponse({...response, show: false})}
                   dismissible>
                {response.responseMessage}
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
                <Form.Select aria-label="Default select example" onChange={(e) => getRoomsFromBuildingSpaceId(e.target.value)} >
                    <option value=''>Select Building</option>
                    {buildings.map((item, index) => {
                        return (
                            <option key={index} value={item.roomId}>{item.roomDescription}</option>
                        );
                    })}
                </Form.Select>
                <Form.Select aria-label="Default select example" onChange={(e) => setTippersSpaceId(e.target.value)}>
                    <option value=''>Select Room</option>
                    {rooms.map((item, index) => {
                        return (
                            <option key={index} value={item.roomId}>{item.roomDescription}</option>
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