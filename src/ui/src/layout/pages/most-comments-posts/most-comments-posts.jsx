import React, {useEffect} from "react";
import {
    fetchNextMostCommentsPosts,
    reFetchMostCommentsPosts,
    useHasMorePosts,
    usePostFetchingStatus, usePostReFetchStatus,
    usePosts
} from "../../../redux/features/mostCommentsPostSlice";
import BasePosts from "../base-posts/base-posts";
import {useDispatch} from "react-redux";

const MostCommentsPosts = () => {
    const posts = usePosts();
    const dispatch = useDispatch()
    const hasMore = useHasMorePosts()
    const status = usePostFetchingStatus();
    const isReloading = usePostReFetchStatus() === "loading";
    const fetchMore = () => dispatch(fetchNextMostCommentsPosts());
    useEffect(() => {
        if (status === 'idle' && !isReloading) {
            dispatch(fetchNextMostCommentsPosts());
        } else if (!isReloading) {
            dispatch(reFetchMostCommentsPosts());
        }
    }, [dispatch]);
    return (<BasePosts posts={posts} fetchMoreData={fetchMore} hasMore={hasMore} listKey={"comments"}
                       isReloading={isReloading}/>)
}
export default MostCommentsPosts;