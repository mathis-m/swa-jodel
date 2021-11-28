import {configureStore} from '@reduxjs/toolkit'
import userSliceReducer from "./features/userSlice";

export const store = configureStore({
    reducer: {
        user: userSliceReducer
    },
})

export default store;