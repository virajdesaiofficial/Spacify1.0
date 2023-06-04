import "./incentives.css";
import moment from "moment";

const Incentives = (props) => {
    return (
        <div className="incentives-container">
            <h1 className="incentives-header">My Incentives</h1>
            <h3>Total Incentives: <strong>{props.totalIncentive} points</strong></h3>
            <ul className="incentives-list">
                {props.incentives.map(incentive => (
                    <li key={incentive.incentiveId} className="incentives-list-item">
                        <h4>{incentive.incentivePoints} Points</h4>
                        <p>{moment(incentive.timestamp).format("MMMM Do YYYY")}</p>
                        <p>Status: {incentive.added ? <span>Awarded</span> : <span>Pending</span>}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Incentives;
