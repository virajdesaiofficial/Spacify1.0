import React, { useState, useEffect } from "react";
import axios from "axios";
import "./incentives.css";

const Incentives = () => {
  const [incentives, setIncentives] = useState([]);

  // Use this useEffect hook to fetch the list of incentives from the API endpoint
  useEffect(() => {
    //axios.get("/api/incentives-history")
      //.then(res => setIncentives(res.data))
      //.catch(err => console.log(err));
     setIncentives([
       { id: 1, name: "Current Incentives", credit: "150", date: "2023-03-14" }
     ]);
   }, []);

  return (
    <div className="incentives-container">
      <h1 className="incentives-header">My Incentives</h1>
      <ul className="incentives-list">
        {incentives.map(incentive => (
          <li key={incentive.id} className="incentives-list-item">
            <h3>{incentive.name}</h3>
            <p>Amount: {incentive.credit}</p>
            <p>Date: {incentive.date}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Incentives;
