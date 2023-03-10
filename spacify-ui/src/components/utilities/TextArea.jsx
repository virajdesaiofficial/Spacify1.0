import FloatingLabel from 'react-bootstrap/FloatingLabel';
import Form from 'react-bootstrap/Form';

function TextArea(props) {
    return (
        <FloatingLabel
            controlId="floatingTextarea"
            label={props.label}
            className="mb-3"
        >
            <Form.Control as="textarea" placeholder={props.placeholder} />
        </FloatingLabel>
    );
}

export default TextArea;