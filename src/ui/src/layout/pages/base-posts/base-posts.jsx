import React, {useRef} from "react";
import "./base-posts.scss"
import {Col, Container, Row} from "react-bootstrap";
import Post from "../../../components/post/post";
import {usePosts} from "../../../redux/features/latestPostSlice";
import {Button, CircularProgress} from "@mui/material";
import InfiniteScroll from "react-infinite-scroll-component";

const colorArray = [
    '#FF6633',
    '#00B3E6',
    '#E6B333',
    '#3366E6',
    '#999966',
    '#80B300',
    '#6680B3',
    '#66991A',
    '#FF1A66',
    '#E6331A',
    '#66994D',
    '#4D8000',
    '#B33300',
    '#66664D',
    '#991AFF',
    '#4DB3FF',
    '#33991A',
    '#4D8066',
    '#FF3380',
    '#CCCC00',
    '#4D80CC',
    '#9900B3',
    '#E64D66',
    '#4DB380',
    '#FF4D4D',
];

const BasePosts = ({posts, hasMore, fetchMoreData, listKey}) => {
    return (
        <div className="page container">
            <div style={{height: "100%"}}>
                <InfiniteScroll
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
                                <Post id={p.id} color={colorArray[p.id % colorArray.length]} text={p.text} user={p.user}
                                      date={p.createdAt} commentCount={p.commentCount}
                                      locationText={p.locationText}/>
                            </Col>
                        ))
                    }
                    {
                        hasMore && <div className="load-more-wrapper"><Button onClick={fetchMoreData}>Mehr anzeigen</Button>
                        </div>
                    }
                </InfiniteScroll>
            </div>
        </div>
    )
}
export default BasePosts;