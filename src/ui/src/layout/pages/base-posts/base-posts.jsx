import React from "react";
import "./base-posts.scss"
import {Col} from "react-bootstrap";
import Post from "../../../components/post/post";
import {Button, CircularProgress} from "@mui/material";
import InfiniteScroll from "react-infinite-scroll-component";
import {colorArray} from "../../../utils/colorArray";

const BasePosts = ({posts, hasMore, fetchMoreData, listKey, isReloading}) => {
    return (
        <div className="page container">
            <div style={{height: "100%"}}>
                {
                    isReloading
                        ? <div className="loader-wrapper" style={{height: "100%"}}><CircularProgress/></div>
                        : <InfiniteScroll
                            className="row m-0"
                            height="100%"
                            dataLength={posts.length}
                            next={fetchMoreData}
                            hasMore={hasMore}
                            loader={<div className="loader-wrapper"><CircularProgress/></div>}
                            endMessage={"Ende der Liste erreicht!"}>
                            {
                                posts.map((p) => (
                                    <Col key={listKey + "-post-id-" + p.id} sm={12} md={4} className="post-container">
                                        <Post id={p.id} color={colorArray[p.id % colorArray.length]} text={p.text}
                                              user={p.user}
                                              date={p.createdAt} commentCount={p.commentCount}
                                              locationText={p.locationText}/>
                                    </Col>
                                ))
                            }
                            {
                                hasMore &&
                                <div className="load-more-wrapper"><Button onClick={fetchMoreData}>Mehr anzeigen</Button>
                                </div>
                            }
                        </InfiniteScroll>
                }
            </div>
        </div>
    )
}
export default BasePosts;