import React, {useEffect, useState} from 'react';
import './create.css';
import {Button, Col, Row} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import {ALL_BUILDINGS_API, ALL_ROOMS_API, CREATE_ROOM_API, USER_NAME_KEY} from "../../endpoints";
import Alert from 'react-bootstrap/Alert';
import SignInDisclaimer from "../utilities/SignInDisclaimer";
import LoadingSpinner from "../utilities/LoadingSpinner";

function Create(props) {
    const initialState = {
        rooms: [],
        buildings: [],
        tippersSpaceId: '',
        wasSuccess: false,
        show: false,
        responseMessage: '',
        loading: false
    };

    const [state, setState] = useState(initialState);

    const userName = global.sessionStorage.getItem(USER_NAME_KEY);

    function createRoomOwnership() {
        setState({...state, loading: true});
        const requestBody = {
            userId: userName,
            tippersSpaceId: state.tippersSpaceId,
            roomType: 'STUDY',
            roomName: state.tippersSpaceId !== '' ? state.rooms.find(room => room.roomId === parseInt(state.tippersSpaceId)).roomDescription : undefined
        };

        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        };
        fetch(CREATE_ROOM_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
            });
    }

    function getRoomsFromBuildingSpaceId(buildingSpaceId) {
        setState({...state, loading: true});
        const url = ALL_ROOMS_API + buildingSpaceId;
        fetch(url).then((res) => res.json())
            .then((data) => {
                setState({...state, rooms: data, loading: false});
            });

    }

    useEffect(() => {
        setState({...state, loading: true});
        fetch(ALL_BUILDINGS_API)
            .then((res) => res.json())
            .then((data) => {
                setState({...state, buildings: data, loading: false})
            });
    }, []);

    const display = () => {
        if (userName) {
            return (
                <section className="createRoom">
                    <LoadingSpinner show={state.loading} />
                    <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                           onClose={() => setState({...state, show: false})}
                           dismissible>
                        {state.responseMessage}
                    </Alert>
                    <h2 style={{margin: '1rem'}}>Create your room!</h2>
                    <Form>
                        <Row className="mb-3">
                            <Form.Group as={Col} controlId="building">
                                <Form.Label className="form-label">Building</Form.Label>
                                <Form.Select aria-label="Default select example" onChange={(e) => getRoomsFromBuildingSpaceId(e.target.value)} >
                                    <option value=''>Select Building</option>
                                    {state.buildings.map((item, index) => {
                                        return (
                                            <option key={index} value={item.roomId}>{item.roomDescription}</option>
                                        );
                                    })}
                                </Form.Select>
                            </Form.Group>
                            <Form.Group as={Col} controlId="room">
                                <Form.Label className="form-label">Room</Form.Label>
                                <Form.Select aria-label="Default select example" onChange={(e) => setState({...state, tippersSpaceId: e.target.value})}>
                                    <option value=''>Select Room</option>
                                    {state.rooms.map((item, index) => {
                                        return (
                                            <option key={index} value={item.roomId}>{item.roomDescription}</option>
                                        );
                                    })}
                                </Form.Select>
                            </Form.Group>
                        </Row>
                    </Form>
                    <div id="createButton">
                        <Button variant="primary" size="lg" onClick={() => createRoomOwnership()} disabled={state.tippersSpaceId === ''} >Create!</Button>
                    </div>
                </section>
            );
        } else {
            return (
                <div style={{textAlign: 'center'}}>
                    <SignInDisclaimer
                        header="Please sign in to create your room!"
                    />
                </div>
            );
        }
    }

    return display();
}

export default Create;