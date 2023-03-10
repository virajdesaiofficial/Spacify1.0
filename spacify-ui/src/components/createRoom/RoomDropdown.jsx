import React from 'react';
import Dropdown from "../utilities/Dropdown";

function RoomDropdown(props) {
    return (
        <Dropdown title={"Select Room"} items={props.list} />
    );
}

export default RoomDropdown;