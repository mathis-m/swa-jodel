import {configureStore} from '@reduxjs/toolkit'
import userSliceReducer from "./features/userSlice";
import commentsSliceReducer from "./features/commentsSlice";
import newestPostSliceReducer from "./features/latestPostSlice";
import mostCommentsPostSliceReducer from "./features/mostCommentsPostSlice";
import votingSliceReducer from "./features/votingSlice";
import highestVotingSliceReducer from "./features/highestVoting";
import addPostSliceReducers from "./features/addPostSlice";

export const store = configureStore({
    reducer: {
        user: userSliceReducer,
        addPost: addPostSliceReducers,
        votes: votingSliceReducer,
        newestPosts: newestPostSliceReducer,
        mostCommentsPosts: mostCommentsPostSliceReducer,
        highestVotesPosts: highestVotingSliceReducer,
        comments: commentsSliceReducer
    },
})
export default store;