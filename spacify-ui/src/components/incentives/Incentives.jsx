import React, {useEffect, useState} from "react";
import "./incentives.css";

const Incentives = () => {
  const [incentives, setIncentives] = useState([]);

  function getUserId(userName) {
    // Fetch user data using the API endpoint /api/v1/user/{userName}
    return fetch(`http://127.0.0.1:8083/api/v1/user/${userName}`)
      .then(response => response.json())
      .then(data => data.user.userId)
      .catch(error => {
        console.log(error);
        console.log(error.response);
      });
  }

  useEffect(() => {
    const userName = sessionStorage.getItem('userName');

    if (userName) {
      const userIdPromise = getUserId(userName);

      userIdPromise.then(userId => {
        // Fetch user data using the API endpoint /api/v1/user/{userId}
        fetch(`http://127.0.0.1:8083/api/v1/user/${userId}`)
          .then(response => response.json())
          .then(data => {
            const userIncentives = data.incentives;
            setIncentives(userIncentives);
          })
          .catch(error => {
            console.log(error);
            console.log(error.response);
          });
      });
    }
  }, []);

  function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  return (
    <div className="incentives-container">
      <h1 className="incentives-header">My Incentives</h1>
      <ul className="incentives-list">
        {incentives.map(incentive => (
          <li key={incentive.incentiveId} className="incentives-list-item">
            <p>Current Incentives</p>
            <p>Points: {incentive.incentivePoints}</p>
            <p>Date: {formatDate(incentive.timestamp)}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Incentives;
