import './App.scss';
import Header from "./layout/header/header";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import React, {lazy, Suspense} from "react";
import {CircularProgress} from "@mui/material";
import Footer from "./layout/footer/footer";

const NewestPosts = lazy(() => import("./layout/pages/newest-posts/newest-posts"));

function App() {
    return (
        <Router>
            <Header/>
            <Suspense fallback={<div className="page-load-wrapper"><CircularProgress/></div>}>
                <Switch>
                    <Route exact path="/" component={NewestPosts}/>
                </Switch>
            </Suspense>
            <Footer/>
        </Router>
    );
}

export default App;
