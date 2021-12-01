import React, {useEffect} from "react";
import {
    fetchNextHighestVotedPosts, reFetchHighestVotedPosts,
    useHasMorePosts,
    usePostFetchingStatus,
    usePosts
} from "../../../redux/features/highestVoting";
import BasePosts from "../base-posts/base-posts";
import {useDispatch} from "react-redux";

const HighestVoting = () => {
    const posts = usePosts();

    const dispatch = useDispatch()
    const hasMore = useHasMorePosts()
    const status = usePostFetchingStatus();
    const fetchMore = () => dispatch(fetchNextHighestVotedPosts());
    useEffect(() => {
        if (status === 'idle') {
            dispatch(fetchNextHighestVotedPosts());
        } else {
            dispatch(reFetchHighestVotedPosts());
        }
    }, [dispatch]);
    return (<BasePosts posts={posts} fetchMoreData={fetchMore} hasMore={hasMore} listKey={"voting"}/>)
}
export default HighestVoting;