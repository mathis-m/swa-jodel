import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {postApi} from "../../api/post/post-api";

const initialState = {
    posts: [],
    status: "idle",
    reFetchStatus: "idle",
    error: null,
    currentPage: 0,
    hasMore: true
}

const addPost =
    (state, post) => {
        state.posts.push(post)
    }
export const fetchNextHighestVotedPosts = createAsyncThunk('user/fetchNextHighestVotedPosts',
    async (_, thunkAPI) => {
        const state = thunkAPI.getState();
        const currentPage = selectCurrentPage(state);
        return postApi.getHighestVotedPosts(currentPage);
    });

export const reFetchHighestVotedPosts = createAsyncThunk('user/reFetchHighestVotedPosts',
    async () => {
        return postApi.getHighestVotedPosts(0);
    });

export const highestVotingSlice = createSlice({
    name: 'highest-voting',
    initialState,
    reducers: {
        postAdded: addPost,
    },
    extraReducers(builder) {
        builder
            .addCase(fetchNextHighestVotedPosts.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchNextHighestVotedPosts.fulfilled, (state, action) => {
                const posts = action.payload;
                state.status = 'succeeded';

                if (posts.length === 0) {
                    state.hasMore = false;
                    return;
                }
                state.currentPage++;
                state.posts.push(...posts);
            })
            .addCase(fetchNextHighestVotedPosts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
        builder
            .addCase(reFetchHighestVotedPosts.pending, (state) => {
                state.reFetchStatus = "loading";
            })
            .addCase(reFetchHighestVotedPosts.rejected, (state) => {
                state.reFetchStatus = "failed";
            })
            .addCase(reFetchHighestVotedPosts.fulfilled, (state, action) => {
                const posts = action.payload;

                if (posts.length === 0) {
                    state.hasMore = false;
                    state.currentPage = 1;
                    state.status = 'succeeded';
                    state.reFetchStatus = "succeeded";
                    return;
                }
                state.posts = posts;
                state.hasMore = true;
                state.status = 'succeeded';
                state.reFetchStatus = "succeeded";
                state.currentPage = 1;
            })
    }
})

export const {
    postAdded,
} = highestVotingSlice.actions

const state = (state) => state.highestVotesPosts;
const selectCurrentPage = createSelector(
    state,
    state => state.currentPage
)
export const selectPosts = createSelector(
    state,
    state => state.posts
)

export const usePosts = () => useSelector(selectPosts);

export const selectHasMorePosts = createSelector(
    state,
    state => state.hasMore
);
export const useHasMorePosts = () => useSelector(selectHasMorePosts);

export const selectStatusPosts = createSelector(
    state,
    state => state.status
);
export const usePostFetchingStatus = () => useSelector(selectStatusPosts);

export const selectReFetchStatusPosts = createSelector(
    state,
    state => state.reFetchStatus
);
export const usePostReFetchStatus = () => useSelector(selectReFetchStatusPosts);

export default highestVotingSlice.reducer;