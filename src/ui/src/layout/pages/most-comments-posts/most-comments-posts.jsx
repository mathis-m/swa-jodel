import React from "react";
import {usePosts} from "../../../redux/features/mostCommentsPostSlice";
import BasePosts from "../base-posts/base-posts";
const MostCommentsPosts = () => {
    const posts = usePosts();

    return (<BasePosts posts={posts}/>)
}
export default MostCommentsPosts;