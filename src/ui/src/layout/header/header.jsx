import React from "react";
import {Container, Nav} from "react-bootstrap";
import AccountIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/LogoutOutlined';
import LoginIcon from '@mui/icons-material/LoginOutlined';
import {useIsLoggedIn} from "../../redux/features/userSlice";
import "./header.scss"
import {Link} from "react-router-dom";

const Header = () => {
    const isLoggedIn = useIsLoggedIn();
    return (
        <div className="header-container">
            <Container className="header-content">
                <Nav.Link eventKey="home" as={Link} to="/"
                          className="header-icon">
                    <div className="title">
                        Yodel
                    </div>
                </Nav.Link>
                <div className="header-icons">
                    <Nav.Link id="first-item" eventKey="account" as={Link} to="/account"
                              className="header-icon">
                        <AccountIcon sx={{color: "white"}}/>
                    </Nav.Link>
                    <Nav.Link eventKey="logout" as={Link} to={isLoggedIn ? "/logout" : "/login"}
                              className="header-icon">
                        {!isLoggedIn ? <LoginIcon sx={{color: "white"}}/> : <LogoutIcon sx={{color: "white"}}/>}
                    </Nav.Link>
                </div>
            </Container>
        </div>
    );
}

export default Header;