import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./login.scss";
import {Container} from "react-bootstrap";
import {GoogleLogin} from 'react-google-login';
import {useDispatch} from "react-redux";
import {fetchCurrentUser, useUser, useUserFetchingStatus} from "../../../redux/features/userSlice";
import {Link} from "react-router-dom";
//import { FacebookLogin } from 'react-facebook-login'; npm install react-facebook-login
//import FacebookLogin from 'react-facebook-login/dist/facebook-login-render-props'

const Login = () => {
    const dispatch = useDispatch();
    const loginStatus = useUserFetchingStatus();
    const user = useUser();
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");

    const validateForm = () => userName.length > 0 && password.length > 0;

    const handleLocalLogin = () => {
        dispatch(fetchCurrentUser({userName, password}))
    };

    const responseGoogle = (googleRes) => {
        if (googleRes.tokenId)
            dispatch(fetchCurrentUser({idToken: googleRes.tokenId}))
    }

    return (
        <Container className="login-page">
            <div className="login">
                {
                    loginStatus !== "succeeded" && <Form>
                        <Form.Label className="login-font">Login</Form.Label>
                        <Form.Group size="lg" controlId="email">
                            <Form.Label>Nutzername</Form.Label>
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
                            <Button size="lg" onClick={() => validateForm() && handleLocalLogin()} disabled={!validateForm()}>
                                Login
                            </Button>
                            <GoogleLogin
                                clientId="978825777932-3dgb9vtadeno8mbvs763i6tb138tuu0f.apps.googleusercontent.com"
                                buttonText="Mit Google anmelden"
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
                            {loginStatus !== "failed" && <Link to="/register">Jetzt registrieren!</Link>}
                            {loginStatus === "failed" && <div>Login hat nicht geklappt! Bist du bereits registriert? <Link to="/register">Jetzt registrieren!</Link></div>}
                        </div>
                    </Form>
                }
                {
                    loginStatus === "succeeded" && user && <div>Willkommen jetzt {user.userName}, <Link to="/">jetzt aktuelle Yodels ansehen!</Link></div>
                }
            </div>
        </Container>
    );
}

export default Login;
