import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {postApi} from "../../api/post/post-api";
import {setCommentCount} from "./highestVoting";

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
export const fetchNextLatestPosts = createAsyncThunk('user/fetchNextLatestPosts',
    async (_, thunkAPI) => {
        const state = thunkAPI.getState();
        const currentPage = selectCurrentPage(state);
        return postApi.getNewestPosts(currentPage);
    });

export const reFetchNextLatestPosts = createAsyncThunk('user/reFetchNextLatestPosts',
    async () => {
        return postApi.getNewestPosts(0);
    });

export const latestPostSlice = createSlice({
    name: 'latest-posts',
    initialState,
    reducers: {
        postAdded: addPost,
        lCommentAdded: setCommentCount
    },
    extraReducers(builder) {
        builder
            .addCase(fetchNextLatestPosts.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchNextLatestPosts.fulfilled, (state, action) => {
                const posts = action.payload;
                state.status = 'succeeded';

                if (posts.length === 0) {
                    state.hasMore = false;
                    return;
                }
                state.currentPage++;
                state.posts.push(...posts);
            })
            .addCase(fetchNextLatestPosts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
        builder
            .addCase(reFetchNextLatestPosts.pending, (state) => {
                state.reFetchStatus = "loading";
            })
            .addCase(reFetchNextLatestPosts.rejected, (state) => {
                state.reFetchStatus = "failed";
            })
            .addCase(reFetchNextLatestPosts.fulfilled, (state, action) => {
                const posts = action.payload;

                if (posts.length === 0) {
                    state.hasMore = false;
                    state.currentPage = 1;
                    state.reFetchStatus = "succeeded";
                    state.status = 'succeeded';
                    return;
                }
                state.posts = posts;
                state.hasMore = true;
                state.reFetchStatus = "succeeded";
                state.status = 'succeeded';
                state.currentPage = 1;
            })
    }
})

export const {
    postAdded,
    lCommentAdded
} = latestPostSlice.actions

const state = (state) => state.newestPosts;

export const selectCurrentPage = createSelector(
    state,
    state => state.currentPage
);

export const selectPosts = createSelector(
    state,
    state => state.posts
);
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

export default latestPostSlice.reducer;