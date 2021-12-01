import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {postApi} from "../../api/post/post-api";

const initialState = {
    posts: [],
    status: "idle",
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
            .addCase(reFetchNextLatestPosts.fulfilled, (state, action) => {
                const posts = action.payload;

                if (posts.length === 0) {
                    state.hasMore = false;
                    state.currentPage = 0;
                    state.status = 'succeeded';
                    return;
                }
                state.posts = posts;
                state.hasMore = true;
                state.status = 'succeeded';
                state.currentPage = 0;
            })
    }
})

export const {
    postAdded,
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

export default latestPostSlice.reducer;