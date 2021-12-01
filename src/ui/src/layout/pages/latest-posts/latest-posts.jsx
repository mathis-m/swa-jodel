import React, {useEffect} from "react";
import BasePosts from "../base-posts/base-posts";
import {
    fetchNextLatestPosts,
    reFetchNextLatestPosts,
    useHasMorePosts,
    usePostFetchingStatus,
    usePosts
} from "../../../redux/features/latestPostSlice";
import {useDispatch} from "react-redux";

const LatestPosts = () => {
    const posts = usePosts();
    const dispatch = useDispatch()
    const hasMore = useHasMorePosts()
    const status = usePostFetchingStatus();
    const fetchMore = () => dispatch(fetchNextLatestPosts());
    useEffect(() => {
        if (status === 'idle') {
            dispatch(fetchNextLatestPosts());
        } else {
            dispatch(reFetchNextLatestPosts());
        }
    }, [dispatch]);
    return (<BasePosts posts={posts} fetchMoreData={fetchMore} hasMore={hasMore} listKey={"latest"}/>)
}
export default LatestPosts;