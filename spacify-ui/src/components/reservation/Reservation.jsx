import { useState } from "react";
import './reservation.css';
import { Link } from 'react-router-dom';

const ReservationPage = () => {
  const [roomType, setRoomType] = useState("");
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [availableRooms, setAvailableRooms] = useState([]);
  const [availableDatesAndTimes, setAvailableDatesAndTimes] = useState([]);
  const isButtonDisabled = !roomType || !date || !time || availableRooms.length === 0;

    const handleRoomTypeChange = (event) => {
      setRoomType(event.target.value);
      // Reset date and time when room type changes
      setDate("");
      setTime("");
      // Fetch available dates and times for the selected room type. Mock data.
      setAvailableDatesAndTimes([
        {
          date: "2023-03-10",
          times: ["morning", "afternoon"]
        },
        {
          date: "2023-03-11",
          times: ["morning", "evening"]
        },
        {
          date: "2023-03-12",
          times: ["afternoon", "evening"]
        }
      ]);
      setAvailableRooms([]);
    };

    const handleDateChange = (event) => {
        setDate(event.target.value);
        // Reset available rooms when date changes
        setAvailableRooms([]);
      };

      const handleTimeChange = (event) => {
        setTime(event.target.value);
        // TODO: Fetch available rooms for the selected date and time. Mock data
        setAvailableRooms([
          {
            location: "Verano Place Community Center:",
            rooms: ["Room 121", " Room 145", "Room 159"]
          },
          {
            location: "DBH:",
            rooms: ["Room 1100", "Room 1200", "Room 1300"]
          }
        ]);
      };

  const handleSubmit = (event) => {
    event.preventDefault();
    // TODO: Handle form submission and reservation
    // save selected information to the local storage or API
    // example code for saving data to local storage
    //const reservation = { roomType, date, time, availableRooms };
    //localStorage.setItem("reservation", JSON.stringify(reservation));
  };

  return (
      <div className="reservation-container">
        <h2>Reservation</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="roomType">Room Type:</label>
            <select id="roomType" name="roomType" value={roomType} onChange={handleRoomTypeChange}>
              <option value="">Select Room Type</option>
              <option value="study">Study</option>
              <option value="office">Office</option>
              <option value="common">Common Space</option>
            </select>
          </div>
          {roomType && (
            <>
            <div className="form-group">
              <label htmlFor="date">Date:</label>
              <input id="date" name="date" type="date" value={date} onChange={handleDateChange} min={new Date().toISOString().split("T")[0]}/>
            </div>
            {date && (
              <div className="form-group">
                <label htmlFor="time">Time:</label>
                <select id="time" name="time" value={time} onChange={handleTimeChange}>
                  <option value="">Select Time</option>
                  <option value="morning">Morning (8am-9am)</option>
                  <option value="morning">Morning (10am-12pm)</option>
                  <option value="afternoon">Afternoon (3pm-4pm)</option>
                  <option value="evening">Evening (6pm-8pm)</option>
                </select>
              </div>
            )}
          </>
        )}
            {availableRooms.length > 0 && (
              <div className="available-rooms">
                <h3>Available Rooms:</h3>
                {availableRooms.map((roomLocation) => (
                  <div className="room-location" key={roomLocation.location}>
                    <h4>{roomLocation.location}</h4>
                    <select name="room" onChange={(event) => console.log(event.target.value)}>
                      <option value="">Select Room</option>
                      {roomLocation.rooms.map((room) => (
                        <option value={room} key={room}>
                          {room}
                        </option>
                      ))}
                    </select>
                  </div>
                ))}
              </div>
        )}
        <Link to="/user" className="user-link">
        <button type="submit" onSubmit={handleSubmit} disabled={isButtonDisabled}>Reserve</button>
        </Link>
      </form>
    </div>
  );
};

export default ReservationPage;
