import React, {useEffect, useState} from "react";
import "./rooms.css";

const Rooms = () => {
  const [rooms, setRooms] = useState([]);

  // Use this useEffect hook to fetch the list of rooms from the API endpoint
  // useEffect(() => {
  //   axios.get("/api/my-rooms")
  //     .then(res => setRooms(res.data))
  //     .catch(err => console.log(err));
  // }, []);

  // For now, we'll use a mocked list of rooms
  useEffect(() => {
    setRooms([
      { id: 1, name: "VP-C-120", type: "study" },
      { id: 2, name: "DBH-1200", type: "office" },
      { id: 3, name: "VP-D-130", type: "study" }
    ]);
  }, []);

  return (
    <div className="rooms-container">
      <h1 className="rooms-header">My Rooms</h1>
      <ul className="rooms-list">
        {rooms.map(room => (
          <li key={room.id} className="rooms-list-item">
            <h3>{room.name}</h3>
            <p>Type: {room.type}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Rooms;
