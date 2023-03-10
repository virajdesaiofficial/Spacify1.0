import React, {useEffect, useState} from 'react';
import './rules.css';
import Form from "react-bootstrap/Form";

function Rules(props) {
    const [rooms, setRooms] = useState([]);
    const [selectedRoomId, setSelectedRoomId] = useState('');


    useEffect(() => {
        fetch("http://127.0.0.1:8080/api/v1/room/all")
            .then((res) => res.json())
            .then((data) => {
                setRooms(data);
            });
    }, []);

    return (
        <section id="rules">
            Rules creation/ edit page
            <div>
                <Form.Select aria-label="Default select example" onChange={(e) => setSelectedRoomId(e.target.value)}>
                    <option value=''>Select Room</option>
                    {rooms.map((item, index) => {
                        return (
                            <option key={index} value={item.roomId}>{item.description}</option>
                        );
                    })}
                </Form.Select>
            </div>
        </section>
    );
}

export default Rules;