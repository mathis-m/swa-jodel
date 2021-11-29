import React from "react";
import DateIcon from '@mui/icons-material/AccessTimeOutlined';
import "./post-date.scss"
import moment from "moment";

const PostDate = ({date}) => {
    console.log(date)
    const dateText = moment(date).fromNow();
    return (
        <div className="post-date">
            <DateIcon sx={{color: "white", fontSize: "16px"}}/>
            <span className="date">
                {dateText}
            </span>
        </div>
    )
}

export default PostDate;