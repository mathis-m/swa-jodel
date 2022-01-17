import React, {useEffect, useRef, useState} from "react";
import "./post-preview.scss";
import Post from "../post/post";
import Button from "react-bootstrap/Button"
import {submitPost} from "../../redux/features/addPostSlice";
import {colorArray} from "../../utils/colorArray";
import {useDispatch} from "react-redux";
import {createCommentForPost, fetchCommentsForPost, useComments} from "../../redux/features/commentsSlice";
import {Form} from "react-bootstrap";
import SingleComment from "../single-comment/single-comment";

const PostPreview = (props) => {
    const post = props.post;
    const onClose = props.onClose;
    const dispatch = useDispatch();
    const [commentText, setCommentText] = useState("");
    const handleCommentSubmit = (e) => {
        if(e)
            e.preventDefault();
        dispatch(createCommentForPost({
            id: post.id,
            text: commentText
        }));
        setCommentText("");
    };

    const comments = useComments(post.id);
    const hasComments = comments.length > 0;
    useEffect(() => {
        dispatch(fetchCommentsForPost({id: post.id}));
    }, [post.id]);

    return (
        <div className="post-preview">
            <Post {...post} allowModal={false}/>
            {
                hasComments && <div className="post-comments" style={{border: `1px solid ${post.color}`}}>
                    {
                        comments.map(c => (
                            <SingleComment key={c.id} {...c} color={post.color}/>
                        ))
                    }
                </div>
            }
            <div className="create-post-comment">
                <Form onSubmit={handleCommentSubmit}>
                    <Form.Group className="mb-3">
                        <Form.Label className="createComment-font">Create your Comment</Form.Label>
                        <Form.Control
                            className="textInsert"
                            as="input"
                            placeholder="Your comment"
                            autoFocus
                            type="text"
                            value={commentText}
                            onChange={(e) => setCommentText(e.target.value)}
                        />
                    </Form.Group>
                </Form>
                <div className="actions">
                    <Button onClick={handleCommentSubmit}>Comment</Button>
                    <Button onClick={onClose}>Close</Button>
                </div>

            </div>
        </div>
    );
}

export default PostPreview;