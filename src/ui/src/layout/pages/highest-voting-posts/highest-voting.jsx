import React from "react";
import {usePosts} from "../../../redux/features/highestVoting";
import BasePosts from "../base-posts/base-posts";
const HighestVoting = () => {
    const posts = usePosts();

    return (<BasePosts posts={posts}/>)
}
export default HighestVoting;