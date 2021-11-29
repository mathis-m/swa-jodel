import React from "react";
import UpVoteIcon from '@mui/icons-material/KeyboardArrowUpOutlined';
import DownVoteIcon from '@mui/icons-material/KeyboardArrowDownOutlined';

import "./post-vote.scss"
import {IconButton} from "@mui/material";
import {useDispatch} from "react-redux";
import {postDownVoted, postUpVoted, useVoting} from "../../redux/features/votingSlice";

const PostVote = ({postId}) => {
    const dispatch = useDispatch();
    const {hasUpVoted, hasDownVoted, voteCount} = useVoting(postId);
    return (
        <div className="post-vote">
            <IconButton sx={{color: hasUpVoted ? "#7A9F35" : "white"}} onClick={() => dispatch(postUpVoted(postId))}>
                <UpVoteIcon sx={{fontSize: "48px"}}/>
            </IconButton>
            <div className="vote-count">
                {voteCount}
            </div>
            <IconButton sx={{color: hasDownVoted ? "#E7410FFF" : "white"}} onClick={() => dispatch(postDownVoted(postId))}>
                <DownVoteIcon sx={{fontSize: "48px" }}/>
            </IconButton>
        </div>
    )
}

export default PostVote;