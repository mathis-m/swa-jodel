import React from "react";
import AddIcon from '@mui/icons-material/AddCircleOutline';
import {Nav} from "react-bootstrap";
import "./add-post.scss"
import {Link} from "react-router-dom";

const AddPost = () => {
    return (
        <div className="add-col">
            <div className="dropin">
                <Nav.Link eventKey="add-post" as={Link} to="/add-post"
                          id="add-icon">
                    <AddIcon sx={{color: "white", fontSize: "64px"}}/>
                </Nav.Link>
            </div>
        </div>
    )
}

export default AddPost;

