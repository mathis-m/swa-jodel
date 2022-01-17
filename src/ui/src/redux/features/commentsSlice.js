import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {postApi} from "../../api/post/post-api";
import {mCommentAdded} from "./mostCommentsPostSlice";
import {lCommentAdded} from "./latestPostSlice";
import {hCommentAdded} from "./highestVoting";

const initialState = {
    comments: {},
    status: "idle",
    error: null,
}

export const fetchCommentsForPost = createAsyncThunk('comments/fetchCommentsForPost',
    async ({id}) => {
        return {
            comments: await postApi.getCommentsForPost(id),
            id
        }
    });


export const createCommentForPost = createAsyncThunk('comments/createCommentForPost',
    async ({id, text}, thunkAPI) => {
        await postApi.createCommentForPost(id, text)
        thunkAPI.dispatch(fetchCommentsForPost({id}));
        thunkAPI.dispatch(mCommentAdded({id}));
        thunkAPI.dispatch(lCommentAdded({id}));
        thunkAPI.dispatch(hCommentAdded({id}));
    });

export const commentSlice = createSlice({
    name: 'comments',
    initialState,
    reducers: {
    },
    extraReducers(builder) {
        builder
            .addCase(fetchCommentsForPost.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchCommentsForPost.fulfilled, (state, action) => {
                state.status = "succeeded";
                const comments = action.payload.comments;
                const postId = action.payload.id;
                state.comments[postId] = comments;
            })
            .addCase(fetchCommentsForPost.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
})


export const {
} = commentSlice.actions

const state = (state) => state.comments;

export const selectCommentsById = (id) => createSelector(
    state,
    (state) => state.comments[id] || []
);

export const useComments = (id) => useSelector(selectCommentsById(id));

export const selectStatus = createSelector(
    state,
    state => state.status
)
export const useCommentsFetchingStatus = () => useSelector(selectStatus);


export const selectError = createSelector(
    state,
    state => state.error
)
export const useCommentsError = () => useSelector(selectError);

export default commentSlice.reducer;