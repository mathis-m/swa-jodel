import React, {useRef, useState} from "react";
import AddIcon from '@mui/icons-material/AddCircleOutline';
import "./add-post.scss"
import {IconButton} from "@mui/material";
import {useOutsideClick} from "../../hooks/useOutsideClick";
import {Form, FormLabel} from "react-bootstrap";
import {colorArray} from "../../utils/colorArray";
import Button from "react-bootstrap/Button";
import {useDispatch} from "react-redux";
import {submitPost} from "../../redux/features/addPostSlice";
import { useHistory  } from "react-router-dom";

const AddPost = () => {
    const dispatch = useDispatch();
    const [showDropin, setShowDropin] = useState(false);
    const [color, setColor] = useState(colorArray[0]);
    const dropinRef = useRef();
    const [postText, setPostText] = useState("");
    const history = useHistory();

    const handlePostSubmit = () => {
        dispatch(submitPost({
            text: postText,
            color
        }));
        history.push('/')
        setShowDropin(false);
        setColor(colorArray[0]);
        setPostText("");
    };
    useOutsideClick(dropinRef, () => setShowDropin(false));
    return (
        <div className="add-col">
            {
                !showDropin && <div className="dropin-button">
                    <IconButton id="add-icon" onClick={() => setShowDropin(true)}>
                        <AddIcon sx={{color: "white", fontSize: "64px"}}/>
                    </IconButton>
                </div>
            }
            {
                showDropin && <div className="dropin" ref={dropinRef}>
                    <Form>
                        <Form.Group className="mb-3">
                            <Form.Label className="createPost-font">Create your Post</Form.Label>
                            <Form.Control
                                style={{
                                    backgroundColor: color,
                                    color: "white",
                                    resize: "none"
                                }}
                                className="textInsert"
                                rows={6}
                                as="textarea"
                                placeholder="Your post"
                                autoFocus
                                type="text"
                                value={postText}
                                onChange={(e) => setPostText(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label> Pick the color of your post </Form.Label>
                            <br/>
                            {
                                colorArray.map((color, i) => (
                                    <Form.Check
                                        key={color}
                                        inline
                                        defaultChecked={i === 0}
                                        onChange={() => setColor(color)}
                                        name="colorPicker"
                                        type="radio"
                                        label={<div className="color-box" style={{backgroundColor: color}}></div>}
                                    />
                                ))
                            }
                        </Form.Group>
                        <Button variant="primary" onClick={handlePostSubmit}>
                            Posten
                        </Button>
                    </Form>
                </div>
            }
        </div>
    )
}

export default AddPost;
