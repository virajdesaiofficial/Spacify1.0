import { useState } from 'react';
import { Link } from 'react-router-dom';
import './rules.css';

function Rules() {
  const [minOccupants, setMinOccupants] = useState(1);
  const [maxOccupants, setMaxOccupants] = useState(1);
  const [duration, setDuration] = useState(1);
  const [incentive, setIncentive] = useState(0);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleMinOccupantsChange = (event) => {
    setMinOccupants(event.target.value);
  };

  const handleMaxOccupantsChange = (event) => {
    setMaxOccupants(event.target.value);
  };

  const handleDurationChange = (event) => {
    setDuration(event.target.value);
  };

  const handleIncentiveChange = (event) => {
    setIncentive(event.target.value);
  };

  const handleSubmit = () => {
    // Save rules to database using appropriate API call
    // Display success message
    setShowSuccessMessage(true);
  };

  return (
    <div className="rules-container">
      <h1 className="rules-header">Edit Rules:</h1>
      <div className="rules-input-container">
        <label className="rules-label" htmlFor="minOccupants">Minimum Occupants:</label>
        <input className="rules-input" id="minOccupants" type="number" value={minOccupants} onChange={handleMinOccupantsChange} />
      </div>
      <div className="rules-input-container">
        <label className="rules-label" htmlFor="maxOccupants">Maximum Occupants:</label>
        <input className="rules-input" id="maxOccupants" type="number" value={maxOccupants} onChange={handleMaxOccupantsChange} />
      </div>
      <div className="rules-input-container">
        <label className="rules-label" htmlFor="duration">Duration of Occupation:</label>
        <input className="rules-input" id="duration" type="number" value={duration} onChange={handleDurationChange} />
      </div>
      <div className="rules-input-container">
        <label className="rules-label" htmlFor="incentive">Incentives:</label>
        <input className="rules-input" id="incentive" type="number" value={incentive} onChange={handleIncentiveChange} />
      </div>
      <button className="rules-button" onClick={handleSubmit}>Save</button>
      {showSuccessMessage && (
        <div className="rules-success-container">
          <p className="rules-success-message">Rules successfully created!</p>
          <Link to="/user" className="rules-link">
            <button className="rules-submit-button" type="submit">Submit</button>
          </Link>
        </div>
      )}
    </div>
  );
}

export default Rules;
