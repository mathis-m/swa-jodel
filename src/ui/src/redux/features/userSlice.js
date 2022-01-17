import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import {userApi} from "../../api/user/user-api";
import {loginApi} from "../../api/user/login-api";

const initialState = {
    user: undefined,
    status: "idle",
    error: null,
    registerStatus: "idle",
    registerError: null,
    lat: null,
    lon: null
}

export const fetchCurrentUser = createAsyncThunk('user/fetchCurrentUser',
    async (_, thunkAPI) => {
        const user = await userApi.getCurrentUser();
        const state = thunkAPI.getState();

        if(state.user.lat && state.user.lon) {
            thunkAPI.dispatch(updateLocation({lat: state.user.lat, lon: state.user.lon}))
        }
        return user;
    });

export const updateLocation = createAsyncThunk('user/updateLocation',
    async ({lat, lon}) => {
        try {
            return await userApi.updateLocation(lat, lon);
        } catch (e) {
            return {lat, lon}
        }
    });

export const logout = createAsyncThunk('user/logout',
    async () => {
        return await loginApi.logout();
    });

export const loginUserLocal = createAsyncThunk('user/loginUserLocal',
    async ({userName, password}, thunkAPI) => {
        const headerPrefix = "Basic";
        const headerValue = btoa(userName + ":" + password)
        const header = `${headerPrefix} ${headerValue}`
        await loginApi.loginLocal(header)
        const state = thunkAPI.getState();
        if(state.user.lat && state.user.lon) {
            thunkAPI.dispatch(updateLocation({lat: state.user.lat, lon: state.user.lon}))
        }
        thunkAPI.dispatch(fetchCurrentUser())
    });

export const loginUserGoogle = createAsyncThunk('user/loginUserGoogle',
    async (idToken, thunkAPI) => {
        const header = `Bearer ${idToken}`
        await loginApi.loginGoogle(header)
        const state = thunkAPI.getState();
        if(state.user.lat && state.user.lon) {
            thunkAPI.dispatch(updateLocation({lat: state.user.lat, lon: state.user.lon}))
        }
        thunkAPI.dispatch(fetchCurrentUser())
    });
export const loginUserFacebook = createAsyncThunk('user/loginUserFacebook',
    async (accessToken, thunkAPI) => {
        const header = `Bearer ${accessToken}`
        await loginApi.loginFacebook(header)
        const state = thunkAPI.getState();
        if(state.user.lat && state.user.lon) {
            thunkAPI.dispatch(updateLocation({lat: state.user.lat, lon: state.user.lon}))
        }
        thunkAPI.dispatch(fetchCurrentUser())
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

export const registerUserFacebook = createAsyncThunk('user/registerUserFacebook',
    async ({accessToken, userName}, {rejectWithValue}) => {
        try {
            return await userApi.createFacebookUser(accessToken, userName);
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
    },
    extraReducers(builder) {
        builder
            .addCase(fetchCurrentUser.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchCurrentUser.fulfilled, (state, action) => {
                state.status = "succeeded";
                state.user = action.payload;
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
        builder
            .addCase(registerUserLocal.pending, (state, action) => {
                state.registerStatus = 'loading';
            })
            .addCase(registerUserLocal.fulfilled, (state, action) => {
                state.registerStatus = "succeeded";
            })
            .addCase(registerUserLocal.rejected, (state, action) => {
                state.registerStatus = 'failed';
                state.registerError = action.error.message;
            });
        builder
            .addCase(logout.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(logout.fulfilled, (state, action) => {
                state.status = "idle";
                state.user = undefined;
            })
            .addCase(logout.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
        builder
            .addCase(updateLocation.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(updateLocation.fulfilled, (state, action) => {
                state.status = "idle";
                const {lat, lon} = action.payload;
                state.lat = lat;
                state.lon = lon;
            })
            .addCase(updateLocation.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
})


export const {
    locationSet
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