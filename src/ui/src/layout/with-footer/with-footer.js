import AddPost from "../../components/add-post/add-post";
import Footer from "../footer/footer";

const WithFooter = ({children}) => {
    return (
        <>
            {children}
            <AddPost/>
            <Footer/>
        </>
    )
}

export default WithFooter;