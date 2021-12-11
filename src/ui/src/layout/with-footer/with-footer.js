import AddPost from "../../components/add-post/add-post";
import Footer from "../footer/footer";
import {useIsLoggedIn} from "../../redux/features/userSlice";

const WithFooter = ({children}) => {
    const isLoggedIn = useIsLoggedIn();

    return (
        <>
            {children}
            {isLoggedIn && <AddPost/>}
            <Footer/>
        </>
    )
}

export default WithFooter;