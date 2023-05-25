import React, { useState, useEffect } from 'react';
import './profile.css';
import { Button, Form } from 'react-bootstrap';

function Profile(props) {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [userName, setUserName] = useState('');
  const [macAddress, setMacAddress] = useState([]);

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
    const userId = getUserId(userName);
    console.log('userId:', userId); // Log the fetched userId

    if (userName) {
      fetch(`http://127.0.0.1:8083/api/v1/user/${userName}`)
        .then(response => response.json())
        .then(data => {
          console.log('Fetched user data:', data); // Log the fetched user data
          setFirstName(data.user.firstName);
          setLastName(data.user.lastName);
          setEmail(data.user.email);
          setMacAddress(data.user.macAddress);
        })
        .catch(error => {
          console.log('Error fetching user data:', error);
        });
    }
  }, []);

const handleSave = async () => {
    console.log('Saving updated user information...');

    const updatedUserData = {
      firstName,
      lastName,
      macAddress,
    };

    try {
      const response = await fetch('http://127.0.0.1:8083/api/v1/user/updateUser', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedUserData),
      });

      if (response.ok) {
        console.log('User information saved successfully!');
      } else {
        console.log('Failed to save user information.');
      }
    } catch (error) {
      console.log('An error occurred while saving user information:', error);
    }
  };

  const handleCancel = () => {
    // TODO: cancel any changes made to the user information
    console.log('Cancelling changes...');
  };

  const handleEditMacAddress = (index, newMacAddress) => {
    const updatedMacAddress = [...macAddress];
    updatedMacAddress[index] = newMacAddress;
    setMacAddress(updatedMacAddress);
  };

return (
<div className="profile">
  <h2>User Profile</h2>
  <Form>
    <Form.Group className="mb-3" controlId="userName">
      <Form.Label className="form-label">Username</Form.Label>
      <Form.Control type="text" value={userName} readOnly />
    </Form.Group>
    <Form.Group className="mb-3" controlId="firstName">
      <Form.Label className="form-label">First Name</Form.Label>
      <Form.Control type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
    </Form.Group>
    <Form.Group className="mb-3" controlId="lastName">
      <Form.Label className="form-label">Last Name</Form.Label>
      <Form.Control type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} />
    </Form.Group>
    <Form.Group className="mb-3" controlId="email">
      <Form.Label className="form-label">Email Address</Form.Label>
      <Form.Control type="text" value={email} readOnly />
    </Form.Group>
    <Form.Group className="mb-3" controlId="macAddress">
      <Form.Label className="form-label mac-address-label">Mac Address</Form.Label>
      <Form.Control as="table" className="table">
        <head>
          <tr>
            <th>Mac Address</th>
            <th></th>
          </tr>
        </head>
        <body>
          {macAddress.map((mac, index) => (
            <tr key={index}>
              <td>
                <Form.Control
                  type="text"
                  value={mac}
                  onChange={(e) => handleEditMacAddress(index, e.target.value)}
                />
                </td>
                </tr>
              ))}
            </body>
          </Form.Control>
        </Form.Group>
        <div className="button-container">
          <Button variant="primary" onClick={handleSave}>Save</Button>
          <Button variant="secondary" onClick={handleCancel}>Cancel</Button>
        </div>
      </Form>
    </div>
  );
}

export default Profile;
