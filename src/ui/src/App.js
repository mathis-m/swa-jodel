import './App.scss';
import Header from "./layout/header/header";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import React, {lazy, Suspense, useEffect} from "react";
import {CircularProgress} from "@mui/material";
import WithFooter from "./layout/with-footer/with-footer";
import {useDispatch} from "react-redux";
import {fetchCurrentUser} from "./redux/features/userSlice";

const NewestPosts = lazy(() => import("./layout/pages/latest-posts/latest-posts"));
const MostCommentsPosts = lazy(() => import("./layout/pages/most-comments-posts/most-comments-posts"));
const HighestVoting = lazy(() => import("./layout/pages/highest-voting-posts/highest-voting"));
const Login = lazy(() => import("./layout/pages/login/login"));
const Register = lazy(() => import("./layout/pages/register/register"));

function App() {
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(fetchCurrentUser());
    }, [dispatch]);
    return (
        <Router>
            <Header/>
            <Suspense fallback={
                <WithFooter>
                    <div className="page-load-wrapper"><CircularProgress/></div>
                </WithFooter>
            }>
                <Switch>
                    <Route exact path="/">
                        <WithFooter>
                            <NewestPosts/>
                        </WithFooter>
                    </Route>
                </Switch>
                <Switch>
                    <Route exact path="/most-comments">
                        <WithFooter>
                            <MostCommentsPosts/>
                        </WithFooter>
                    </Route>
                </Switch>
                <Switch>
                    <Route exact path="/highest-voting" component={HighestVoting}>
                        <WithFooter>
                            <HighestVoting/>
                        </WithFooter>
                    </Route>
                </Switch>
                <Switch>
                    <Route exact path="/login" component={Login}/>
                </Switch>
                <Switch>
                    <Route exact path="/register" component={Register}/>
                </Switch>
            </Suspense>
        </Router>
    );
}

export default App;
