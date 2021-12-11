import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {postApi} from "../../api/post/post-api";
import {reFetchNextLatestPosts} from "./latestPostSlice";

const initialState = {
    status: "idle",
    error: null,
}

export const submitPost = createAsyncThunk('addPost/submitPost',
    async (post, thunkAPI) => {
        await postApi.createPost(post);
        thunkAPI.dispatch(reFetchNextLatestPosts())
    });

export const addPostSlice = createSlice({
    name: 'addPost',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addCase(submitPost.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(submitPost.fulfilled, (state, action) => {
                state.status = "succeeded";
            })
            .addCase(submitPost.rejected, (state, action) => {
                state.status = 'failed';
            });
    }
})


const state = (state) => state.addPost;

export const selectStatus = createSelector(
    state,
    state => state.status
)
export const useAddPostFetchingStatus = () => useSelector(selectStatus);


export const selectError = createSelector(
    state,
    state => state.error
)
export const useAddPostError = () => useSelector(selectError);

export default addPostSlice.reducer;