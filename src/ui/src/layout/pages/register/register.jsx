import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./register.scss";
import {Container} from "react-bootstrap";
import {GoogleLogin} from 'react-google-login';
import {useDispatch} from "react-redux";
import {
    fetchCurrentUser,
    registerUserGoogle,
    useUserFetchingStatus, useUserRegisterError,
    useUserRegisterStatus
} from "../../../redux/features/userSlice";
import {Link} from "react-router-dom";
import {CircularProgress} from "@mui/material";
//import { FacebookLogin } from 'react-facebook-login'; npm install react-facebook-login
//import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props'

const Register = () => {
    const dispatch = useDispatch();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const registerStatus = useUserRegisterStatus();
    const registerError = useUserRegisterError();
    const [googleResponseSuccess, setGoogleResponseSuccess] = useState(false);
    const [idToken, setIdToken] = useState(null);

    const validateForm = () => userName.length > 0 && password.length > 0;
    const validateUsername = () => userName.length > 0;

    const handleSubmit = event => {
        event.preventDefault();
    };

    const handleExternalUserNameSet = event => {
        event.preventDefault();
        dispatch(registerUserGoogle({idToken, userName: userName}))
    };

    const responseGoogle = (googleRes) => {
        if (googleRes.tokenId) {
            setGoogleResponseSuccess(true);
            setIdToken(googleRes.tokenId)
        }
    }

    return (
        <Container className="login-page">
            <div className="login">
                {
                    (!googleResponseSuccess || registerStatus === "failed") && <Form onSubmit={handleSubmit}>
                        <Form.Label className="login-font">Registrierung</Form.Label>
                        <Form.Group size="lg" controlId="email">
                            <Form.Label>Email</Form.Label>
                            <Form.Control
                                autoFocus
                                type="text"
                                value={userName}
                                onChange={(e) => setUserName(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group size="lg" controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>
                        <div className="login-methods">
                            <Button size="lg" type="submit" disabled={!validateForm()}>
                                Registrieren
                            </Button>
                            <GoogleLogin
                                clientId="978825777932-3dgb9vtadeno8mbvs763i6tb138tuu0f.apps.googleusercontent.com"
                                buttonText="Mit Google registrieren"
                                onSuccess={responseGoogle}
                                onFailure={responseGoogle}
                                cookiePolicy={'single_host_origin'}
                                redirectUri="postmessage"
                            />
                            {/* <FacebookLogin
                            appId="1088597931155576"
                            autoLoad={true}
                            fields="name,userName,picture"
                            onClick={componentClicked}
                            callback={responseFacebook} />*/}
                            {registerStatus === "failed" && <div>Es ist ein Fehler während der Registrierung aufgetreten <br/> {registerError}</div>}
                        </div>
                    </Form>
                }
                {
                    googleResponseSuccess && registerStatus === "idle" && <Form onSubmit={handleExternalUserNameSet}>
                        <Form.Group size="lg" controlId="email">
                            <Form.Label>Nutzername</Form.Label>
                            <Form.Control
                                autoFocus
                                type="text"
                                value={userName}
                                onChange={(e) => setUserName(e.target.value)}
                            />
                            <Button size="lg" type="submit" disabled={!validateUsername()}>
                                Registrieren abschließen
                            </Button>
                        </Form.Group>
                    </Form>
                }
                {
                    registerStatus === "loading" && <div className="load-wrapper"><CircularProgress/></div>
                }
                {
                    registerStatus === "succeeded" && <div>Registrierung erfolgreich! <Link to="/login">Weiter zur Anmeldung!</Link></div>
                }
            </div>
        </Container>
    );
}

export default Register;
