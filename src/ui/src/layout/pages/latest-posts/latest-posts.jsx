import React, {useEffect} from "react";
import BasePosts from "../base-posts/base-posts";
import {
    fetchNextLatestPosts,
    reFetchNextLatestPosts,
    useHasMorePosts,
    usePostFetchingStatus, usePostReFetchStatus,
    usePosts
} from "../../../redux/features/latestPostSlice";
import {useDispatch} from "react-redux";

const LatestPosts = () => {
    const posts = usePosts();
    const dispatch = useDispatch()
    const hasMore = useHasMorePosts()
    const status = usePostFetchingStatus();
    const isReloading = usePostReFetchStatus() === "loading";
    const fetchMore = () => dispatch(fetchNextLatestPosts());
    useEffect(() => {
        if (status === 'idle' && !isReloading) {
            dispatch(fetchNextLatestPosts());
        } else if (!isReloading) {
            dispatch(reFetchNextLatestPosts());
        }
    }, [dispatch]);
    return (<BasePosts posts={posts} fetchMoreData={fetchMore} hasMore={hasMore} listKey={"latest"}
                       isReloading={isReloading}/>)
}
export default LatestPosts;