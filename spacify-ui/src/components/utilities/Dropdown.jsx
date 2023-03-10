import Dropdown from 'react-bootstrap/Dropdown';

function Dropdown(props) {
    return (
        <Dropdown>
            <Dropdown.Toggle variant="success" id="dropdown-basic">
                {props.title}
            </Dropdown.Toggle>

            <Dropdown.Menu>
                {
                    props.items.map(item => {
                        return (
                            <Dropdown.Item>{item.tippersSpaceId}</Dropdown.Item>
                        );
                    })
                }
            </Dropdown.Menu>
        </Dropdown>
    );
}

export default BasicExample;