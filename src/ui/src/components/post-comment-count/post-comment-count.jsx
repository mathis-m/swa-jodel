import React from "react";
import CommentsCount from '@mui/icons-material/MessageOutlined';
import "./post-comment-count.scss"

const PostCommentCount = ({commentCount}) => {
    let commentCountText = commentCount + (commentCount === 1 ? " Kommentar" : " Kommentare");
    return (
        <div className="comment-count">
            <CommentsCount sx={{color: "white", fontSize: "16px"}}/>
            <span className="date">
                {commentCountText}
            </span>
        </div>
    )
}

export default PostCommentCount;