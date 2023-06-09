import React, {useEffect, useState} from 'react';
import './reservation.css';
import ReactDatetimeClass from "react-datetime";
import "react-datetime/css/react-datetime.css";
import moment from "moment";
import {Button} from "react-bootstrap";
import {CREATE_RESERVATION_API, GET_ALL_ROOMS_API, USER_NAME_KEY} from "../../endpoints";
import LoadingSpinner from "../utilities/LoadingSpinner";
import Alert from "react-bootstrap/Alert";
import Form from "react-bootstrap/Form";
import SignInDisclaimer from "../utilities/SignInDisclaimer";

function Reservation(props) {
    const initialState = {
        rooms: [],
        fromTime: undefined,
        toTime: undefined,
        selectedRoom: '',
        attendees: '',
        wasSuccess: false,
        show: false,
        responseMessage: '',
        loading: false
  };

  const [state, setState] = useState(initialState);

  const yesterday = moment().subtract( 1, 'day' );
  const valid = function( current ){
    return current.isAfter( yesterday );
  };
  const inputProps = {
    placeholder: "Select date and time",
  };

  useEffect(() => {
    setState({...state, loading: true});
    fetch(GET_ALL_ROOMS_API)
        .then((res) => res.json())
        .then((data) => {
          setState({...state, rooms: data, loading: false})
        });
  }, []);

  const userName = global.sessionStorage.getItem(USER_NAME_KEY);

  const createReservation = () => {
      setState({...state, loading: true});
      const payload = {
          reservedRoomId: state.selectedRoom,
          timeFrom: state.fromTime,
          timeTo: state.toTime,
          numberOfGuests: state.attendees,
          reservedBy: userName
      };

      const requestHeader = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload)
      };
      fetch(CREATE_RESERVATION_API, requestHeader)
          .then((res) => res.json())
          .then((data) => {
              setState({...state, wasSuccess: data.success, show: true, responseMessage: data.message, loading: false});
          });
  };

  const DATE_FORMAT = "MM/DD/YYYY HH:mm:ss";
  const displayRoomDropDown = () => {
    if (state.rooms !== undefined) {
      return (
          <Form.Select aria-label="Default select example" onChange={(e) => setState({...state, selectedRoom: e.target.value})} >
            <option value=''>Select Room</option>
            {state.rooms.map((item, index) => {
              return (
                  <option key={index} value={item.roomId}>{item.roomName}</option>
              );
            })}
          </Form.Select>
      );
    }
  };

  const displayAttendeeDropdown = () => {
      let options = [];
      options.push(<option value=''>Select attendee(s)</option>)
      for (let i=1; i<15; i++) {
          if (i === 1) {
              options.push(<option key={i} value={i}>{i} attendee</option>);
          } else {
              options.push(<option key={i} value={i}>{i} attendees</option>);
          }
      }
      return (
          <Form.Select aria-label="Default select example" onChange={(e) => setState({...state, attendees: e.target.value})} >
              {options}
          </Form.Select>
      );
  }

  const display = () => {
      if (userName) {
          return (
              <section id="reservation">
                  <LoadingSpinner show={state.loading} />
                  <Alert show={state.show} variant={state.wasSuccess ? 'success' : 'danger'}
                         onClose={() => setState({...state, show: false})}
                         dismissible>
                      {state.responseMessage}
                  </Alert>
                  <h2 style={{margin: '1rem'}}>Create your room!</h2>
                  <Form>
                      <Form.Group className="mb-3">
                          <Form.Label className="form-label">Select room</Form.Label>
                          {displayRoomDropDown()}
                      </Form.Group>
                      <Form.Label className="form-label">Select Reservation Time</Form.Label>
                      <Form.Group className="mb-3" controlId="room">
                          <div style={{display: "inline-flex", gap: "2rem"}}>
                              <div>
                                  <label>From</label>
                                  <ReactDatetimeClass onChange={(e) => setState({...state, fromTime: e.format(DATE_FORMAT)})} className="datePicker" isValidDate={valid} inputProps={inputProps} />
                              </div>
                              <div>
                                  <label>To</label>
                                  <ReactDatetimeClass onChange={(e) => setState({...state, toTime: e.format(DATE_FORMAT)})} className="datePicker" isValidDate={valid} inputProps={inputProps} />
                              </div>
                          </div>
                      </Form.Group>
                      <Form.Group className="mb-3">
                          <Form.Label className="form-label">Select expected number of attendees</Form.Label>
                          {displayAttendeeDropdown()}
                      </Form.Group>
                  </Form>
                  <div id="reserveButton">
                      <Button variant="primary" size="lg" onClick={() => createReservation()} disabled={state.selectedRoom === '' || state.attendees === '' || state.fromTime === undefined || state.toTime === undefined} >Create!</Button>
                  </div>
              </section>
          );
      } else {
          return (
              <div style={{textAlign: 'center'}}>
                  <SignInDisclaimer
                      header="Please sign in to reserve a room!"
                  />
              </div>
          );
      }
  };

  return display();
}

export default Reservation;
