import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";

const initialState = {
    user: undefined,
    status: "idle",
    error: null,
}

const logout =
    (state) => {
        state.status = "idle";
        state.user = undefined;
    }

export const fetchCurrentUser = createAsyncThunk('user/fetchCurrentUser',
    async () => {
        return null;
    });

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        userLoggedOut: logout
    },
    extraReducers(builder) {
        builder
            .addCase(fetchCurrentUser.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchCurrentUser.fulfilled, (state, action) => {
                state.status = "succeeded";
                state.user = action.payload
            })
            .addCase(fetchCurrentUser.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
})


export const {
    userLoggedOut
} = userSlice.actions

const state = (state) => state.user;

export const selectUser = createSelector(
    state,
    state => state.user
);
export const useUser = () => useSelector(selectUser);

export const selectIsLoggedIn = createSelector(
    selectUser,
    user => !!user
);
export const useIsLoggedIn = () => useSelector(selectIsLoggedIn);

export const selectStatus = createSelector(
    state,
    state => state.status
)
export const useUserFetchingStatus = () => useSelector(selectStatus);


export const selectError = createSelector(
    state,
    state => state.error
)
export const useUserError = () => useSelector(selectError);


export default userSlice.reducer;