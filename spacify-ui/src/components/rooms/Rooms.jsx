import React, { useState, useEffect } from "react";
import axios from "axios";
import "./rooms.css";

const Rooms = () => {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null); // Selected room for the pop-up form
  const [phoneNo, setPhoneNo] = useState(""); // Phone number for WhatsApp subscription
  const [showPhoneNumberInput, setShowPhoneNumberInput] = useState(false); // Control visibility of phone number input

  useEffect(() => {
    setRooms([
      { id: 1, name: "VP-C-120", type: "study" },
      { id: 2, name: "DBH-1200", type: "office" },
      { id: 3, name: "VP-D-130", type: "study" }
    ]);
  }, []);

  const handleRoomSelect = (room) => {
    setSelectedRoom(room);
  };

  const handleCloseForm = () => {
    setSelectedRoom(null);
    setShowPhoneNumberInput(false);
    setPhoneNo("");
  };

  const handleSubscribe = () => {
    setShowPhoneNumberInput(true);
  };

  const handlePhoneNoChange = (e) => {
    setPhoneNo(e.target.value);
  };

  const handleConfirmSubscription = () => {
    console.log(`Subscribing to WhatsApp for room: ${selectedRoom.name}`);
    console.log(`Phone number: ${phoneNo}`);
    setShowPhoneNumberInput(false);
    const savedPhoneNo = phoneNo;
    console.log("Saved phone number:", savedPhoneNo);
  };

  return (
    <div className="rooms-container">
      <h1 className="rooms-header">My Rooms</h1>
      <ul className="rooms-list">
        {rooms.map((room) => (
          <li key={room.id} className="rooms-list-item">
            <h3 onClick={() => handleRoomSelect(room)}>{room.name}</h3>
            <p>Type: {room.type}</p>
          </li>
        ))}
      </ul>

      {selectedRoom && (
        <div className="room-details-overlay">
          <div className="room-details-form">
            <div className="form-header">
              <h2>Room Details</h2>
              <button className="close-button" onClick={handleCloseForm}>
                Close
              </button>
            </div>
            <p className="room-name">Name: {selectedRoom.name}</p>
            <p className="room-type">Type: {selectedRoom.type}</p>

            {!showPhoneNumberInput ? (
              <button className="subscribe-button" onClick={handleSubscribe}>
                Subscribe to Whatsapp
              </button>
            ) : (
              <div>
                <label htmlFor="phoneNo">Enter Phone Number:</label>
                <input
                  type="text"
                  id="phoneNo"
                  value={phoneNo}
                  onChange={handlePhoneNoChange}
                />
                <button className="confirm-button" onClick={handleConfirmSubscription}>
                  Confirm Subscription
                </button>
              </div>
            )}

            {!showPhoneNumberInput && (
              <div className="form-buttons">
                <button className="unsubscribe-button">Unsubscribe</button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default Rooms;
