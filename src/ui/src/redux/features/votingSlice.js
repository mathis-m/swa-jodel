import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {fetchNextLatestPosts, reFetchNextLatestPosts} from "./latestPostSlice";
import {fetchNextHighestVotedPosts, reFetchHighestVotedPosts} from "./highestVoting";
import {fetchNextMostCommentsPosts, reFetchMostCommentsPosts} from "./mostCommentsPostSlice";
import {postApi} from "../../api/post/post-api";

const initialState = {
    votes: [],
}

const votePostUp =
    (state, action) => {
        const index = state.votes.findIndex(p => p.postId === action.payload);
        if(index !== -1) {
            if(state.votes[index].hasUpVoted) {
                state.votes[index].hasUpVoted = false;
                state.votes[index].voteCount--;
                return;
            }
            state.votes[index].hasUpVoted = true;
            let diff = 1;
            if(state.votes[index].hasDownVoted) {
                state.votes[index].hasDownVoted = false;
                diff++;
            }
            state.votes[index].voteCount += diff;
        }
    }

const votePostDown =
    (state, action) => {
        const index = state.votes.findIndex(p => p.postId === action.payload);
        if(index !== -1) {
            if(state.votes[index].hasDownVoted) {
                state.votes[index].hasDownVoted = false;
                state.votes[index].voteCount++;
                return;
            }
            let diff = 1;
            if(state.votes[index].hasUpVoted) {
                state.votes[index].hasUpVoted = false;
                diff++;
            }
            state.votes[index].hasDownVoted = true;
            state.votes[index].voteCount -= diff;
        }
    }
export const fetchVotingForPost = createAsyncThunk('user/fetchVotingForPost',
    async (postId) => {
        return postApi.getVotingForPost(postId);
    });

export const sendPostUpVoted = createAsyncThunk('user/sendPostUpVoted',
    async (postId) => {
        return postApi.votePostUp(postId);
    });

export const sendPostDownVoted = createAsyncThunk('user/sendPostDownVoted',
    async (postId) => {
        return postApi.votePostDown(postId);
    });


const newPostsFetched = (state, action) => {
    for (let post of action.payload) {
        const index = state.votes.findIndex(v => v.postId === post.id)
        if(index === -1) {
            state.votes.push({
                postId: post.id,
                hasUpVoted: false,
                hasDownVoted: false,
                voteCount: post.voteCount
            })
            continue;
        }

        if(state.votes[index].voteCount !== post.voteCount)
            state.votes[index].voteCount = post.voteCount
    }
}

const newVotingFetched = (state, action) => {
    const vote = action.payload;
    const index = state.votes.findIndex(v => v.postId === vote.postId);
    if(index === -1) {
        state.votes.push(vote);
        return;
    }

    state.votes[index].hasUpVoted = vote.hasUpVoted;
    state.votes[index].hasDownVoted = vote.hasDownVoted;
};

const newPostVotingFetched = (state, action) => {
    const index = state.votes.findIndex(v => v.postId === action.payload.post.id);
    if(index === -1) {
        state.votes.push({
            postId: action.payload.post.id,
            hasUpVoted: action.payload.voting.hasUpVoted,
            hasDownVoted: action.payload.voting.hasDownVoted,
            voteCount: action.payload.post.voteCount
        })
        return;
    }

    if(state.votes[index].voteCount !== action.payload.post.voteCount)
        state.votes[index].voteCount = action.payload.post.voteCount;

    if(state.votes[index].hasUpVoted !== action.payload.voting.hasUpVoted)
        state.votes[index].hasUpVoted = action.payload.voting.hasUpVoted;

    if(state.votes[index].hasDownVoted !== action.payload.voting.hasDownVoted)
        state.votes[index].hasDownVoted = action.payload.voting.hasDownVoted;
}

export const votingSlice = createSlice({
    name: 'voting',
    initialState,
    reducers: {
        postUpVoted: votePostUp,
        postDownVoted: votePostDown,
    },
    extraReducers(builder) {
        builder
            .addCase(fetchNextLatestPosts.fulfilled, newPostsFetched)
            .addCase(reFetchNextLatestPosts.fulfilled, newPostsFetched)
            .addCase(fetchNextHighestVotedPosts.fulfilled, newPostsFetched)
            .addCase(reFetchHighestVotedPosts.fulfilled, newPostsFetched)
            .addCase(fetchNextMostCommentsPosts.fulfilled, newPostsFetched)
            .addCase(reFetchMostCommentsPosts.fulfilled, newPostsFetched)
            .addCase(fetchVotingForPost.fulfilled, newVotingFetched)
            .addCase(sendPostUpVoted.fulfilled, newPostVotingFetched)
            .addCase(sendPostDownVoted.fulfilled, newPostVotingFetched)
    }
})

export const {
    postDownVoted,
    postUpVoted
} = votingSlice.actions

const state = (state) => state.votes;
export const selectVoting = createSelector(
    [
        (state) => state.votes,
        (state, id) => id
    ],
    (voteState) => id => voteState.votes.find(v => v.postId === id) || {}
);
export const useVoting = (postId) => useSelector(selectVoting)(postId);

export default votingSlice.reducer;