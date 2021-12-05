import React, {useRef, useState} from "react";
import AddIcon from '@mui/icons-material/AddCircleOutline';
import "./add-post.scss"
import {IconButton} from "@mui/material";
import {useOutsideClick} from "../../hooks/useOutsideClick";
import {Form, FormLabel} from "react-bootstrap";
import {colorArray} from "../../utils/colorArray";

const AddPost = () => {
    const [showDropin, setShowDropin] = useState(false);
    const [color, setColor] = useState(colorArray[0]);
    const dropinRef = useRef();
    const [createdPost] = useState("");

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
                    <Form.Label className="createPost-font">Create your Post</Form.Label>
                    <Form.Control
                        style={{
                            backgroundColor: color,
                            color: "white"
                        }}
                        className="textInsert"
                        rows={6}
                        as="textarea"
                        placeholder="Your post"
                        autoFocus
                        type="text"
                    />
                    <Form.Label> Pick the color of your post </Form.Label>
                    {
                        colorArray.map((color, i) => (
                            <Form.Check
                                inline
                                defaultChecked={i === 0}
                                onChange={() => setColor(color)}
                                name="colorPicker"
                                type="radio"
                                label={<div className="color-box" style={{backgroundColor: color}}></div>}
                            />
                        ))
                    }
                </div>
            }
        </div>
    )
}

export default AddPost;
