import React from "react";
import {Col, Container, Nav, Navbar, Row} from "react-bootstrap";
import LatestPostsIcon from '@mui/icons-material/AccessTimeOutlined';
import MostCommentsIcon from '@mui/icons-material/MessageOutlined';
import MostVotedIcon from '@mui/icons-material/KeyboardArrowUpOutlined';

import "./footer.scss"
import {useMediumDisplay} from "../../hooks/useDisplay";
import {Link} from "react-router-dom";

const Footer = () => {
    return (
        <Navbar variant="dark" className="nav-container" expand="lg">
            <Container>
                <Row>
                    <Col xs={4} className="other-cols">
                        <Nav.Link eventKey="latest-posts" as={Link} to="/"
                                  className="nav-icon" id="nav-item-first">
                            <LatestPostsIcon sx={{color: "white"}}/>
                        </Nav.Link>
                    </Col>
                    <Col xs={4} className="other-cols">
                        <Nav.Link eventKey="most-comments" as={Link} to="/most-comments"
                                  className="nav-icon">
                            <MostCommentsIcon sx={{color: "white"}}/>
                        </Nav.Link>
                    </Col>
                    <Col xs={4} className="other-cols" id="nav-item-last">
                        <Nav.Link eventKey="most-voted" as={Link} to="/highest-voting"
                                  className="nav-icon">
                            <MostVotedIcon sx={{color: "white"}}/>
                        </Nav.Link>
                    </Col>
                </Row>
            </Container>
        </Navbar>
    );
}

export default Footer;