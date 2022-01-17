import React, {useEffect} from "react";
import {useDispatch} from "react-redux";
import {logout} from "../../../redux/features/userSlice";
const Logout = () => {
    const dispatch = useDispatch()
    useEffect(() => {
        dispatch(logout())
    }, []);

    return <>
    Logging out...
    </>
}

export default Logout;