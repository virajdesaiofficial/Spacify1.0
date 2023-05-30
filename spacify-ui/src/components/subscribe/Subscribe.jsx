import React, {useEffect, useState} from 'react';
import './subscribe.css';
import {Button} from "react-bootstrap";
import Form from 'react-bootstrap/Form';
import ListGroup from 'react-bootstrap/ListGroup';
import Toast from 'react-bootstrap/Toast';
import ToastContainer from 'react-bootstrap/ToastContainer';
import Chart from "../chart/Chart";
import {ALL_BUILDINGS_API, ROOMS_FOR_SUBS_API, ROOM_RULES_API, SUBSCRIBE_ROOM_API, USER_NAME_KEY, ROOM_TREND_API} from "../../endpoints";

function Subscribe(props) {
    const [rooms, setRooms] = useState([]);
    const [buildings, setBuildings] = useState([]);
    const [spacifyRoomId, setSpacifyRoomId] = useState('');
    const [rules, setRules] = useState([]);
    const [response, setResponse] = useState({header: "", show: false,  responseMessage: ""});
    const [trendData, setTrendData] = useState([]);

    function getRoomsFromBuildingSpaceId(buildingSpaceId) {
        const url = ROOMS_FOR_SUBS_API + buildingSpaceId;
        fetch(url).then((res) => res.json())
                    .then((data) => {
                          setRooms(data);
                    });

    }

    function setSpacifyRoomIdAndGetRulesandTrends(selectedRoomId){
        setSpacifyRoomId(selectedRoomId);
        const url = ROOM_RULES_API + selectedRoomId;
        fetch(url).then((result) => result.json())
                    .then((data) => {
                        setRules(data);
                    })
        fetch(ROOM_TREND_API + selectedRoomId)
                 .then((res) => res.json())
                 .then((data) => {
                       setTrendData(data);
                  });
    }

    function closeToast(){
          setResponse({header: "", show: false,  responseMessage: ""});
        }

    useEffect(() => {
         fetch(ALL_BUILDINGS_API)
              .then((res) => res.json())
              .then((data) => {
                    setBuildings(data);
                    });
    }, []);

    function subscribeToRoom() {

        let userId = global.sessionStorage.getItem(USER_NAME_KEY);
        const requestHeader = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ string1: userId, string2: spacifyRoomId})
        };
        fetch(SUBSCRIBE_ROOM_API, requestHeader)
            .then((res) => res.json())
            .then((data) => {
                setResponse({header: data.success ? "Subscribed!" : "Uh-Oh error", show: true, responseMessage: data.message});
            });
    }

    return (
        <section className="subscribeRoom">
            {response.show &&
              <ToastContainer className="p-3" position="top-end">
                <Toast id="toast" onClose={closeToast}>
                   <Toast.Header>
                      <img src="holder.js/20x20?text=%20" className="rounded me-2" alt="" />
                         <strong className="me-auto">{response.header}</strong>
                   </Toast.Header>
                   <Toast.Body>{response.responseMessage}</Toast.Body>
                 </Toast>
               </ToastContainer>
            }

            <div id="subscribeRoomTitle">
                <h4>Choose a room below to subscribe </h4>
            </div>
            <div id="selection">
                <Form.Select aria-label="Default select example" onChange={(e) => getRoomsFromBuildingSpaceId(e.target.value)}>
                    <option value=''>Select a building</option>
                    {buildings.map((item, index) => {
                        return (
                            <option key={index} value={item.roomId} >{item.roomDescription}</option>
                        );
                    })}
                </Form.Select>
                <Form.Select aria-label="Default select example" onChange={(e) => setSpacifyRoomIdAndGetRulesandTrends(e.target.value)}>
                    <option value=''>Select a room</option>
                    {rooms.map((item, index) => {
                        return (
                            <option key={index} value={item.roomId}>{item.roomDescription}</option>
                        );
                    })}
                </Form.Select>
            </div>
            {rules.length > 0 && (<div>
               <div id="rulesTitle"><h6> Below are the rules of the room and its occupancy trend graph: </h6></div>
               <div id="rules">
               <ListGroup as="ol" numbered>
                 {rules.map((item, index) => {
                   return (
                       <ListGroup.Item as="li" className="d-flex justify-content-between align-items-start">
                         <div className="ms-2 me-auto">
                         	  {item}
                          </div>
                       </ListGroup.Item>
                  );
                 })}
               </ListGroup></div>x
               <div id="trendGraph">
                 {trendData.length > 0 && <Chart trend={trendData}  />}
               </div>
               </div>
               )}
            <div id="subscribeButtons">
                <Button variant="primary" size="lg" onClick={subscribeToRoom} disabled={spacifyRoomId === ''}>Subscribe!</Button>
            </div>

        </section>
    );
}

export default Subscribe;