import React from "react";
import "./ownedRooms.css";

const OwnedRooms = (props) => {
    return (
        <div className="rooms-container">
            <h2 className="rooms-header">Owned Rooms</h2>
            <ul className="rooms-list">
                {
                    props.ownedRooms.map(room => (
                        <li key={room.roomId} className="rooms-list-item">
                            <h3>{room.roomDescription}</h3>
                            <p>Type: {room.roomType}</p>
                        </li>
                    ))
                }
            </ul>
            {/*<h2 className="rooms-header">Subscribed Rooms</h2>*/}
            {/*<ul className="rooms-list">*/}
            {/*    {*/}
            {/*        props.subscribedRooms.map(room => (*/}
            {/*            <li key={room.roomId} className="rooms-list-item">*/}
            {/*                <h3>{room.roomDescription}</h3>*/}
            {/*                <p>Type: {room.roomType}</p>*/}
            {/*            </li>*/}
            {/*        ))*/}
            {/*    }*/}
            {/*</ul>*/}
        </div>
    );
};

export default OwnedRooms;
