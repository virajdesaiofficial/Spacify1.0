import React from "react";
import "./loadingSpinner.css"
import Modal from "react-bootstrap/Modal";

export default function LoadingSpinner(props) {
    return (
        <Modal show={props.show}
               className="spinner-container"
               centered
               size="sm"
        >
            <div className="loading-spinner"></div>
        </Modal>
    );
}