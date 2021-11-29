import {configureStore} from '@reduxjs/toolkit'
import userSliceReducer from "./features/userSlice";
import newestPostSliceReducer from "./features/latestPostSlice";
import mostCommentsPostSliceReducer from "./features/mostCommentsPostSlice";
import votingSliceReducer from "./features/votingSlice";
import highestVotingSliceReducer from "./features/highestVoting";

export const store = configureStore({
    reducer: {
        user: userSliceReducer,
        votes: votingSliceReducer,
        newestPosts: newestPostSliceReducer,
        mostCommentsPosts: mostCommentsPostSliceReducer,
        highestVotesPosts: highestVotingSliceReducer
    },
})

export default store;