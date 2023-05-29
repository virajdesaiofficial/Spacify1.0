import React, {useEffect, useState} from "react";
import "./rooms.css";
import {ALL_SUBSCRIBED_ROOMS_API, USER_NAME_KEY, UPDATE_SUBSCRIBED_ROOMS_API} from "../../endpoints";

const Rooms = () => {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null); // Selected room for the pop-up form
  const [phoneNo, setPhoneNo] = useState("");               // Phone number for WhatsApp subscription
  const [showPhoneNumberInput, setShowPhoneNumberInput] = useState(false); // Control visibility of phone number input
  const [roomId, setRoomID] = useState("");
  const savedRoomId = null;
  const savedPhoneNo = null;
  const [response, setResponse] = useState({header: "", show: false,  responseMessage: ""});
  const axios = require('axios');



  useEffect(() => {

//     let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    let url = ALL_SUBSCRIBED_ROOMS_API + "kshatris";        //HARDCODED change later
    console.log(url);
     fetch(url)
              .then((res) => res.json())
              .then((data) => {
              console.log(data);
                    setRooms(data);
                    });


//     setRooms([
//       { id: 1, name: "VP-C-120", type: "study" },
//       { id: 2, name: "DBH-1200", type: "office" },
//       { id: 3, name: "VP-D-130", type: "study" }
//     ]);
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
    console.log(`Subscribing to WhatsApp for room: ${selectedRoom.roomDescription}`);
    console.log(`Phone number: ${phoneNo}`);
    setShowPhoneNumberInput(false);

    console.log("Saved phone number:", savedPhoneNo);
    console.log("Saved room ID:", savedRoomId);
//    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
          let userId = "kshatris";              //hardcoded change later
          const requestHeader = {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ userId: userId, roomId: selectedRoom.roomId, phoneNumber: phoneNo})
          };
          fetch(UPDATE_SUBSCRIBED_ROOMS_API, requestHeader)
              .then((res) => res.json())
              .then((data) => {
                  setResponse({header: data.success ? "Subscribed!" : "Uh-Oh error", show: true, responseMessage: data.message});
              });
  };

  return (
    <div className="rooms-container">
      <h1 className="rooms-header">My Rooms</h1>
      <ul className="rooms-list">
        {rooms.map((room) => (
          <li key={room.roomId} className="rooms-list-item">
            <h3 onClick={() => handleRoomSelect(room)}>{room.roomDescription}</h3>
          </li>
        ))}
      </ul>

      {selectedRoom && (
        <div className="room-details-overlay">
          <div className="room-details-form">
            <div className="form-header">
              <h2>Room Details</h2>
              <button className="close-button" onClick={handleCloseForm}>
                X
              </button>
            </div>
            <p className="room-name">Name: {selectedRoom.roomDescription}</p>
            <p className="room-type">Type: {selectedRoom.type}</p>

            {!showPhoneNumberInput ? (
              <button id="formButton" variant="primary" size="lg" onClick={handleSubscribe}>
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
                <button className="confirm-button" id="formButton" onClick={handleConfirmSubscription}>
                  Confirm Subscription
                </button>
              </div>
            )}

            {!showPhoneNumberInput && (
              <div className="form-buttons">
                <button className="unsubscribe-button" id="formButton" variant="primary" size="lg">Unsubscribe</button>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default Rooms;
