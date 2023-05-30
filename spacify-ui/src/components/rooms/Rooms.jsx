import React, { useEffect, useState } from "react";
import "./rooms.css";
import { ALL_SUBSCRIBED_ROOMS_API, USER_NAME_KEY, UPDATE_SUBSCRIBED_ROOMS_API, UPDATE_SUBSCRIBED_STATUS_API, GET_ALL_MONITORING_ROOMS_WITH_ZERO_OCCUPANCY_API, GET_ALL_WHATSAPP_SUBSCRIBED_ROOMS } from "../../endpoints";

const Rooms = () => {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null); // Selected room for the pop-up form
  const [phoneNo, setPhoneNo] = useState(""); // Phone number for WhatsApp subscription
  const [showPhoneNumberInput, setShowPhoneNumberInput] = useState(false); // Control visibility of phone number input
  const [firstEffectCompleted, setFirstEffectCompleted] = useState(false);
  const [vacantRooms, setVacantRooms] = useState([])
  const [subToWhatsapp, setSubToWhatsapp] = useState([])
  const [response, setResponse] = useState({
    header: "",
    show: false,
    responseMessage: ""
  });

  useEffect(() => {

    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    let url = ALL_SUBSCRIBED_ROOMS_API + userId;

    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        data.forEach(element => {
          element.available = false
          element.subscribed = false
        });
        setRooms(data)
        setFirstEffectCompleted(true);
      })

  }, []);

  useEffect(() => {
    if (firstEffectCompleted) {
      handleSubscribedRooms();
      handleRoomVacancy();
    }

  }, [firstEffectCompleted])

  const handleSubscribedRooms = () => {
    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    let url = GET_ALL_WHATSAPP_SUBSCRIBED_ROOMS + userId;
    var requestOptions = {
      method: 'GET',
      redirect: 'follow'
    };

    fetch(url, requestOptions)
      .then(response => response.json())
      .then(result =>
        setSubToWhatsapp(result)
      )
      .catch(error => console.log('error', error));

  }

  const handleRoomVacancy = () => {
    var new_room_ids = [];
    rooms.forEach((room) => {
      new_room_ids.push(room.roomId)
    })

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: JSON.stringify(new_room_ids),
      redirect: 'follow'
    };

    fetch(GET_ALL_MONITORING_ROOMS_WITH_ZERO_OCCUPANCY_API, requestOptions)
      .then(response => response.json())
      .then(result =>
        setVacantRooms(result)
      )
      .catch(error => console.log('error', error));
  }

  const handleRoomSelect = (room) => {
    setSelectedRoom(room);
    setResponse({ header: "", show: false, responseMessage: "" }); // Reset the response state when a new room is selected
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
    
    setShowPhoneNumberInput(false);

    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    const requestHeader = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: userId, roomId: selectedRoom.roomId, phoneNumber: phoneNo })
    };
    fetch(UPDATE_SUBSCRIBED_ROOMS_API, requestHeader)
      .then((res) => res.json())
      .then((data) => {
        setResponse({ header: data.success ? "Subscribed!" : "Uh-Oh error", show: true, responseMessage: data.message });
      
        setSubToWhatsapp([...subToWhatsapp, selectedRoom.roomId])
        // Store the subscribed room ID in local storage
        const subscribedRooms = JSON.parse(localStorage.getItem("subscribedRooms")) || [];
        localStorage.setItem("subscribedRooms", JSON.stringify([...subscribedRooms, selectedRoom.roomId]));

        // Update the rooms state to mark the room as subscribed
        const updatedRooms = rooms.map((room) => {
          if (room.roomId === selectedRoom.roomId) {
            return { ...room, subscribed: true };
          }
          return room;
        });
        setRooms(updatedRooms);
      });
  };

  const handleUnsubscription = () => {
    let userId = global.sessionStorage.getItem(USER_NAME_KEY);

    const requestHeader = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: userId, roomId: selectedRoom.roomId })
    };
    fetch(UPDATE_SUBSCRIBED_STATUS_API, requestHeader)
      .then((res) => res.json())
      .then((data) => {
        setResponse({ header: data.success ? "Unsubscribed!" : "Uh-Oh error", show: true, responseMessage: data.message });
        var new_array = subToWhatsapp
        new_array.splice(new_array.indexOf(selectedRoom.roomId), 1)
        setSubToWhatsapp(new_array)
        // Update the rooms state to mark the room as unsubscribed
        const updatedRooms = rooms.map((room) => {
          if (room.roomId === selectedRoom.roomId) {
            return { ...room, subscribed: false };
          }
          return room;
        });
        setRooms(updatedRooms);
      });
  }

  return (
    <div className="rooms-container">
      <h1 className="rooms-header">My Rooms</h1>
      <ul className="rooms-list">
        {rooms.map((room) => (
          <li key={room.roomId} className="rooms-list-item" style={{ backgroundColor: vacantRooms.includes(room.roomId) ? '#0f0' : '#fff' }}>
            <h3 onClick={() => handleRoomSelect(room)}>{room.roomDescription}</h3>
            {subToWhatsapp.includes(room.roomId) && (
              <div className="subscription-status-container">
                <p className="subscription-status" style={{ color: "green" }}>Subscribed to Whatsapp</p>
              </div>
            )}
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

            {!showPhoneNumberInput && !response.show && !subToWhatsapp.includes(selectedRoom.roomId) && (
              <button id="formButton" variant="primary" size="lg" onClick={handleSubscribe}>
                Subscribe to WhatsApp
              </button>
            )}

            {showPhoneNumberInput && (
              <div>
                <label htmlFor="phoneNo">Enter Phone Number:</label>
                <input type="text" id="phoneNo" value={phoneNo} onChange={handlePhoneNoChange} />
                <button className="confirm-button" id="formButton" onClick={handleConfirmSubscription}>
                  Confirm Subscription
                </button>
              </div>
            )}

            {response.show && (
              <div className="form-buttons">
                {response.header === "Subscribed!" && (
                  <p className="congratulations-message">{response.responseMessage}</p>
                )}
              </div>
            )}

            {!showPhoneNumberInput && !response.show && subToWhatsapp.includes(selectedRoom.roomId) && (
              <div className="form-buttons">
                <button className="unsubscribe-button" id="formButton" variant="primary" size="lg" onClick={handleUnsubscription}>
                  Unsubscribe
                </button>
              </div>
            )}

            {response.show && (
              <div className="form-buttons">
                {response.header === "Unsubscribed!" && (
                  <p className="unsubscribed-message">{response.responseMessage}</p>
                )}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default Rooms;
