import './App.scss';
import Header from "./layout/header/header";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import React, {lazy, Suspense} from "react";
import {CircularProgress} from "@mui/material";
import Footer from "./layout/footer/footer";
import AddPost from "./components/add-post/add-post";

const NewestPosts = lazy(() => import("./layout/pages/latest-posts/latest-posts"));
const MostCommentsPosts = lazy(() => import("./layout/pages/most-comments-posts/most-comments-posts"));
const HighestVoting = lazy(() => import("./layout/pages/highest-voting-posts/highest-voting"));

function App() {
    return (
        <Router>
            <Header/>
            <Suspense fallback={<div className="page-load-wrapper"><CircularProgress/></div>}>
                <Switch>
                    <Route exact path="/" component={NewestPosts}/>
                </Switch>
                <Switch>
                    <Route exact path="/most-comments" component={MostCommentsPosts}/>
                </Switch>
                <Switch>
                    <Route exact path="/highest-voting" component={HighestVoting}/>
                </Switch>
            </Suspense>
            <AddPost/>
            <Footer/>
        </Router>
    );
}

export default App;
