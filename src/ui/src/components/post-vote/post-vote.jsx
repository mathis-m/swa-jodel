import React, {useEffect} from "react";
import UpVoteIcon from '@mui/icons-material/KeyboardArrowUpOutlined';
import DownVoteIcon from '@mui/icons-material/KeyboardArrowDownOutlined';

import "./post-vote.scss"
import {IconButton} from "@mui/material";
import {useDispatch} from "react-redux";
import {
    fetchVotingForPost,
    postDownVoted,
    postUpVoted,
    sendPostDownVoted,
    sendPostUpVoted,
    useVoting
} from "../../redux/features/votingSlice";
import {useIsLoggedIn} from "../../redux/features/userSlice";

const PostVote = ({postId}) => {
    const isLoggedIn = useIsLoggedIn();
    const dispatch = useDispatch();
    const {hasUpVoted, hasDownVoted, voteCount} = useVoting(postId);
    useEffect(() => {
        if (isLoggedIn)
            dispatch(fetchVotingForPost(postId));
    }, [dispatch]);
    let sxUp = {color: hasUpVoted ? "#7A9F35" : "white"};
    let sxDown = {color: hasDownVoted ? "#E7410FFF" : "white"};
    const withStroke = {
        stroke: "white",
        strokeLocation: "inside",
        strokeLinecap: "round",
        strokeLinejoin: "bevel"
    }
    if (!isLoggedIn) {
        sxUp.color = "#dcdcdc"
        sxDown.color = "#dcdcdc"
    }
    if (hasUpVoted) {
        sxUp = {
            ...sxUp,
            ...withStroke
        }
    }
    if (hasDownVoted) {
        sxDown = {
            ...sxDown,
            ...withStroke
        }
    }
    const onUpVote = (e) => {
        if (!isLoggedIn)
            return;
        dispatch(postUpVoted(postId));
        dispatch(sendPostUpVoted(postId));
        e.preventDefault();
        e.stopPropagation();
    };
    const onDownVote = (e) => {
        if (!isLoggedIn)
            return;
        dispatch(postDownVoted(postId));
        dispatch(sendPostDownVoted(postId));
        e.preventDefault();
        e.stopPropagation();
    }
    return (
        <div className="post-vote">
            <IconButton sx={sxUp} onClick={onUpVote} disabled={!isLoggedIn}>
                <UpVoteIcon sx={{fontSize: "48px"}}/>
            </IconButton>
            <div className="vote-count">
                {voteCount}
            </div>
            <IconButton sx={sxDown} onClick={onDownVote} disabled={!isLoggedIn}>
                <DownVoteIcon sx={{fontSize: "48px"}}/>
            </IconButton>
        </div>
    )
}

export default PostVote;