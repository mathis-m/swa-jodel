import React from "react";
import "./post.scss";
import PostVote from "../post-vote/post-vote";
import PostLocation from "../post-location/post-location";
import PostDate from "../post-date/post-date";
import PostCommentCount from "../post-comment-count/post-comment-count";

/**
 import {IconButton, TextField} from "@mui/material";
 import SendIcon from '@mui/icons-material/SendOutlined';


 <div className="comment-container">
 <TextField type="text" variant="standard" className="comment-text-field"/>
 <IconButton>
 <SendIcon />
 </IconButton>
 </div>
 **/

const Post = ({id, text, user, color, date, commentCount, locationText}) => {
    return <div className="post" style={{backgroundColor: color}}>
        <div className="post-content">
            <div className="location-user">
                <PostLocation location={locationText}/>
                <div>von {user}</div>
            </div>
            <div className="text-vote-container">
                <div className="text-container">
                    {text}
                </div>
                <div className="vote-container">
                    <PostVote postId={id}/>
                </div>
            </div>
            <div className="bottom-row">
                <PostDate date={date}/>
                <PostCommentCount commentCount={commentCount}/>
            </div>
        </div>
    </div>
}

export default Post;