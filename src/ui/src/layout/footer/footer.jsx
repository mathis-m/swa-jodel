import React from "react";
import {Container} from "react-bootstrap";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import {useIsLoggedIn} from "../../redux/features/userSlice";
import "./footer.scss"

const Footer = () => {
    return (
        <div className="footer-container">
            <Container >
                Footer
            </Container>
        </div>
    );
}

export default Footer;