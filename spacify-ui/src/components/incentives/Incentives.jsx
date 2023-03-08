import React from 'react';
import './incentives.css';

function IncentivePoints(props) {
  const { points } = props;

  return (
    <div className="incentive-points-container">
      <div className="incentive-points-heading">
        <h3>Your Rewards:</h3>
      </div>
      <div className="incentive-points-number">
        <h2>{points}</h2>
      </div>
    </div>
  );
}

export default IncentivePoints;
