import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {userApi} from "../../api/user/user-api";

const initialState = {
    user: undefined,
    status: "idle",
    header: undefined,
    error: null,
    registerStatus: "idle",
    registerError: null,
}

const logout =
    (state) => {
        state.status = "idle";
        state.user = undefined;
    }

export const fetchCurrentUser = createAsyncThunk('user/fetchCurrentUser',
    async ({idToken, userName, password}) => {
        const headerPrefix = idToken ? "Bearer" : "Basic";
        const headerValue = idToken ? idToken : btoa(userName + ":" + password)
        const header = `${headerPrefix} ${headerValue}`
        return {user: await userApi.getCurrentUser(header), header};
    });

export const registerUserGoogle = createAsyncThunk('user/registerUserGoogle',
    async ({idToken, userName}, {rejectWithValue}) => {
        try {
            return await userApi.createGoogleUser(idToken, userName);
        } catch (err) {
            if (!err.response) {
                throw err
            }

            return rejectWithValue(err.response.data)
        }
    });

export const registerUserLocal = createAsyncThunk('user/registerUserLocal',
    async ({userName, password}) => {
        return await userApi.createLocalUser(userName, password);
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
                state.user = action.payload.user;
                state.header = action.payload.header;
            })
            .addCase(fetchCurrentUser.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });

        builder
            .addCase(registerUserGoogle.pending, (state, action) => {
                state.registerStatus = 'loading';
            })
            .addCase(registerUserGoogle.fulfilled, (state, action) => {
                state.registerStatus = "succeeded";
            })
            .addCase(registerUserGoogle.rejected, (state, action) => {
                state.registerStatus = 'failed';
                state.registerError = action.error.message;
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

export const selectRegisterError = createSelector(
    state,
    state => state.registerError
)
export const useUserRegisterError = () => useSelector(selectRegisterError);

export const selectRegisterStatus = createSelector(
    state,
    state => state.registerStatus
)
export const useUserRegisterStatus = () => useSelector(selectRegisterStatus);

export const selectHeader = createSelector(
    state,
    state => state.header
)

export default userSlice.reducer;