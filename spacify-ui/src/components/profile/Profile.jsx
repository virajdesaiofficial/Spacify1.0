import React, { useState } from 'react';
import './profile.css';
import { Button, Form } from 'react-bootstrap';

function Profile(props) {
  const [firstName, setFirstName] = useState('John');
  const [lastName, setLastName] = useState('Doe');
  const [email, setEmail] = useState('johndoe@example.com');
  const [macAddress, setMacAddress] = useState([
    { id: 1, macAddress: '00:11:22:33:44:55', deviceName: 'Laptop' },
    { id: 2, macAddress: '66:77:88:99:aa:bb', deviceName: 'Phone' },
  ]);

  const handleSave = () => {
    // TODO: save the updated user information to the backend API
    console.log('Saving updated user information...');
  };

  const handleCancel = () => {
    // TODO: cancel any changes made to the user information
    console.log('Cancelling changes...');
  };

  const handleEdit = (index, newMacAddress) => {
    const updatedMacAddress = [...macAddress];
    updatedMacAddress[index].macAddress = newMacAddress;
    setMacAddress(updatedMacAddress);
  };

return (
<div className="profile">
  <h2>User Profile</h2>
  <Form>
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
      <Form.Control type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
    </Form.Group>
    <Form.Group className="mb-3" controlId="macAddress">
      <Form.Label className="form-label mac-address-label">Mac Address</Form.Label>
      <Form.Control as="table" className="table">
        <head>
          <tr>
            <th>Mac Address</th>
            <th>Device Name</th>
            <th></th>
          </tr>
        </head>
        <body>
          {macAddress.map((device, index) => (
            <tr key={device.id}>
              <td>
                <Form.Control
                  type="text"
                  value={device.macAddress}
                  onChange={(e) => handleEdit(index, e.target.value)}
                />
              </td>
              <td>
                <Form.Control
                  type="text"
                  value={device.deviceName}
                  onChange={(e) => {
                    const updatedMacAddress = [...macAddress];
                    updatedMacAddress[index].deviceName = e.target.value;
                    setMacAddress(updatedMacAddress);
                  }}
                />
              </td>
            </tr>
          ))}
        </body>
      </Form.Control>
    </Form.Group>
    <div className="btn">
      <Button variant="primary" onClick={handleSave}>
        Save Changes
      </Button>{" "}
      <Button variant="secondary" onClick={handleCancel}>
        Cancel
      </Button>
    </div>
  </Form>
</div>

);

}

export default Profile;
