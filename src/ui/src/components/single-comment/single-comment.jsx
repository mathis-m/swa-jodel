import './single-comment.scss'
import moment from "moment";

const SingleComment = ({createdAt, id, postId, text, user, color}) => {
    const dateText = moment(createdAt).fromNow();

    return (
        <div className="single-comment" style={{backgroundColor: color}}>
            <div className="comment-info">
                <div>von {user}</div>
                <div>{dateText}</div>
            </div>
            <div className="comment-text">{text}</div>
        </div>
    );
}

export default SingleComment;