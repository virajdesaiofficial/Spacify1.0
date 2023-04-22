import { useState } from "react";
import './reservation.css';
import { Link } from 'react-router-dom';
import axios from "axios";
import {
  useQuery,
  useMutation,
  useQueryClient,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'

import { format } from 'date-fns';

const getSlots = async (roomType, date) => {
  if (!roomType || !date) return [];
  //TODO add date here
  let data = await fetch(`http://localhost:8083/api/v1/availableSlots/${roomType.toUpperCase()}`);
  return await data.json();
}


const formatDate = (date) => {
  console.log(date);
  return new Date(date).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit', hour12: false});
}

const Room = ({ roomId, timeBound }) => {
  return (
    <div>
      <h1>{roomId}</h1>
      <div className="flex-row">
        {
          timeBound.map((bound) => {
            return (
              <button type="button" className="btn" style={{ "background-color": "gainsboro" }}>
                {formatDate(bound.timeFrom)} - {formatDate(bound.timeTo)}
              </button>
            )
          })}
      </div>
    </div>
  )
}


const ReservationPage = () => {
  const [roomType, setRoomType] = useState("");
  const [date, setDate] = useState("");
  const isButtonDisabled = !roomType || !date;

  // Queries
  const query = useQuery({ queryKey: ['slots', roomType, date], queryFn: () => getSlots(roomType, date) });

  const handleRoomTypeChange = (event) => {
    setRoomType(event.target.value);
    // Reset date and time when room type changes
    setDate("");
  };

  const handleDateChange = (event) => {
    setDate(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // TODO: Handle form submission and reservation
    // save selected information to the local storage or API
    // example code for saving data to local storage
    //const reservation = { roomType, date, time, availableRooms };
    //localStorage.setItem("reservation", JSON.stringify(reservation));
  };
  if (query.isLoading) return <span>Loading</span>;

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
              <input id="date" name="date" type="date" value={date} onChange={handleDateChange} min={new Date().toISOString().split("T")[0]} />
            </div>
            {date && (
              <div className="form-group">
              </div>
            )}
          </>
        )}
        {query.data.map(data => {
          return data.timeBound.length > 0 ?
          <Room roomId={data.roomId} timeBound={data.timeBound}></Room>: <></>;
        })}
        <Link to="/user" className="user-link">
          <button type="submit" onSubmit={handleSubmit} disabled={isButtonDisabled}>Reserve</button>
        </Link>
      </form>
    </div>
  );
};

export default ReservationPage;
