import React from "react";
import {Container} from "react-bootstrap";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import {useIsLoggedIn} from "../../redux/features/userSlice";

const Header = () => {
    const isLoggedIn = useIsLoggedIn();
    return (
        <div className="header-container">
            <Container >
                <div className="title">
                    Yodel
                </div>
                <div className="location">

                </div>
                <div className="actions">
                    <AccountCircleIcon/>
                    {
                        isLoggedIn
                            ? <LogoutOutlinedIcon/>
                            : <LoginOutlinedIcon/>
                    }
                </div>
            </Container>
        </div>
    );
}

export default Header;