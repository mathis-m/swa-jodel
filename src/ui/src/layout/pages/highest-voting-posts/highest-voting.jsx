import React, {useEffect} from "react";
import {
    fetchNextHighestVotedPosts,
    reFetchHighestVotedPosts,
    useHasMorePosts,
    usePostFetchingStatus,
    usePostReFetchStatus,
    usePosts
} from "../../../redux/features/highestVoting";
import BasePosts from "../base-posts/base-posts";
import {useDispatch} from "react-redux";

const HighestVoting = () => {
    const posts = usePosts();

    const dispatch = useDispatch()
    const hasMore = useHasMorePosts()
    const status = usePostFetchingStatus();
    const isReloading = usePostReFetchStatus() === "loading";
    const fetchMore = () => dispatch(fetchNextHighestVotedPosts());
    useEffect(() => {
        if (status === 'idle' && !isReloading) {
            dispatch(fetchNextHighestVotedPosts());
        } else if (!isReloading) {
            dispatch(reFetchHighestVotedPosts());
        }
    }, [dispatch]);
    return (<BasePosts posts={posts} fetchMoreData={fetchMore} hasMore={hasMore} listKey={"voting"}
                       isReloading={isReloading}/>)
}
export default HighestVoting;