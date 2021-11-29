import React from "react";
import BasePosts from "../base-posts/base-posts";
import {usePosts} from "../../../redux/features/latestPostSlice";

const LatestPosts = () => {
    const posts = usePosts();
    return (<BasePosts posts={posts} />)
}
export default LatestPosts;