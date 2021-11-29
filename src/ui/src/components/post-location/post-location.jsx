import React from "react";
import LocationIcon from '@mui/icons-material/LocationOnOutlined';
import "./post-location.scss"

const PostLocation = ({location}) => {
    return (
        <div className="post-location">
            <LocationIcon sx={{color: "white", fontSize: "32px"}}/>
            <span className="location">
                {location}
            </span>
        </div>
    )
}

export default PostLocation;

