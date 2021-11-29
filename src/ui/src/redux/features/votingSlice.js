import {createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import moment from "moment";

const initialState = {
    votes: [
        {
            postId: 0,
            voteCount: 65,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 1,
            voteCount: 65,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 2,
            voteCount: 65,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 3,
            voteCount: 10,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 4,
            voteCount: 54,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 5,
            voteCount: 99,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 6,
            voteCount: 50,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 7,
            voteCount: 33,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 8,
            voteCount: 22,
            hasDownVoted: false,
            hasUpVoted: false
        },
        {
            postId: 9,
            voteCount: 0,
            hasDownVoted: false,
            hasUpVoted: false
        },
    ],
    status: "idle",
    error: null,
    lastDate: moment().format()
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


export const votingSlice = createSlice({
    name: 'voting',
    initialState,
    reducers: {
        postUpVoted: votePostUp,
        postDownVoted: votePostDown,
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
    (voteState) => id => voteState.votes.find(v => v.postId === id)
);
export const useVoting = (postId) => useSelector(selectVoting)(postId);

export default votingSlice.reducer;