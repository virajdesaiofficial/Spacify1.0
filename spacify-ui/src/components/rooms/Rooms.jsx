import React, { useEffect, useState } from "react";
import "./rooms.css";
import { ALL_SUBSCRIBED_ROOMS_API, USER_NAME_KEY, UPDATE_SUBSCRIBED_ROOMS_API, UPDATE_SUBSCRIBED_STATUS_API, GET_ALL_MONITORING_ROOMS_WITH_ZERO_OCCUPANCY_API, GET_ALL_WHATSAPP_SUBSCRIBED_ROOMS } from "../../endpoints";

const Rooms = () => {
  const [rooms, setRooms] = useState([]);
  const [selectedRoom, setSelectedRoom] = useState(null); // Selected room for the pop-up form
  const [phoneNo, setPhoneNo] = useState(""); // Phone number for WhatsApp subscription
  const [showPhoneNumberInput, setShowPhoneNumberInput] = useState(false); // Control visibility of phone number input
  const [roomId, setRoomID] = useState("");
  const savedRoomId = null;
  const savedPhoneNo = null;
  const [roomIds, setRoomIds] = useState([]);
  const [roomColor, setRoomColor] = useState(false)
  const [firstEffectCompleted, setFirstEffectCompleted] = useState(false);
  const [response, setResponse] = useState({
    header: "",
    show: false,
    responseMessage: ""
  });

  useEffect(() => {
    
    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    let url = ALL_SUBSCRIBED_ROOMS_API + userId;
    console.log("Getting all Subscribed rooms");
    fetch(url)
      .then((res) => res.json())
      .then((data) => {
        data.forEach(element => {
          element.available = false
          element.subscribed = false  
        });
        console.log("data is ", data);
        setRooms(data)
         setFirstEffectCompleted(true);
      })
      
  }, []);

  useEffect(() => {
    if (firstEffectCompleted) {
      console.log("if condition triggered");
      
      handleSubscribedRooms();
    }
    
  },[firstEffectCompleted])

  // useEffect(() => {

  //   // Retrieve subscribed room IDs from local storage
  //   const subscribedRooms = JSON.parse(localStorage.getItem("subscribedRooms")) || [];
  //   console.log("subscribed rooms ", subscribedRooms);
  //   const ids = rooms.map(room => room.roomId);
  //   setRoomIds(ids);
  //   console.log("room IDS are ", roomIds);
  //   const updatedRooms = rooms.map((room) => {
  //     if (subscribedRooms.includes(room.roomId)) {
  //       return { ...room, subscribed: true };
  //     }
  //     return room;
  //   });
  // }, [rooms]);

  const handleSubscribedRooms = () => {
    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
    let url = GET_ALL_WHATSAPP_SUBSCRIBED_ROOMS + userId;
    var requestOptions = {
      method: 'GET',
      redirect: 'follow'
    };
    
    fetch(url, requestOptions)
      .then(response => response.json())
      .then(result => {
        rooms.forEach((element) => {
          console.log(element);
          if(result.includes(element.roomId)) {
          console.log(`${element.roomId} is wa subscribed`)
            element.subscribed = true
          }
        });
        setRooms(rooms)
        handleRoomVacancy();    
      })
      .catch(error => console.log('error', error));
      
  }

  const handleRoomVacancy = () => {
    var new_room_ids = [];
    rooms.forEach((room) => {
      new_room_ids.push(room.roomId)
    })
  console.log("handle vacancy called ", new_room_ids);
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
      .then(result => {
      
        rooms.forEach((element) => {
          console.log(element);
          if(result.includes(element.roomId)) {
            console.log(`${element.roomId} is vacant`)
            element.available = true
          }
        });
        setRooms(rooms);
      })
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
    console.log(`Subscribing to WhatsApp for room: ${selectedRoom.roomDescription}`);
    console.log(`Phone number: ${phoneNo}`);
    setShowPhoneNumberInput(false);

    console.log("Saved phone number:", savedPhoneNo);
    console.log("Saved room ID:", savedRoomId);
    let userId = global.sessionStorage.getItem(USER_NAME_KEY);
//     let userId = "kshatris"; //hardcoded change later
    const requestHeader = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: userId, roomId: selectedRoom.roomId, phoneNumber: phoneNo })
    };
    fetch(UPDATE_SUBSCRIBED_ROOMS_API, requestHeader)
      .then((res) => res.json())
      .then((data) => {
        setResponse({ header: data.success ? "Subscribed!" : "Uh-Oh error", show: true, responseMessage: data.message });

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
        setResponse({header: data.success ? "Unsubscribed!" : "Uh-Oh error", show: true, responseMessage: data.message });

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
          <li key={room.roomId} className="rooms-list-item" style={{backgroundColor:room.available?'#0f0':'#fff'}}>
            <h3 onClick={() => handleRoomSelect(room)}>{room.roomDescription}</h3>
            {room.subscribed && (
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

            {!showPhoneNumberInput && !response.show && !selectedRoom.subscribed && (
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

            {!showPhoneNumberInput && !response.show && selectedRoom.subscribed && (
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
