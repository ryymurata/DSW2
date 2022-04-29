import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

export default class Topbar extends Component {
    render() {
        return <div className="topbar-support">
            <div className="topbar">
                <ul id="links">
                    <li className="logo">
                        <Link to="/"><span id="titulo">LataVelhaInc.</span></Link>
                    </li>
                    <li className="linkTopoDireita linkTopo">
                        <Link to="/CRUD">{this.props.msg}</Link>
                    </li>
                </ul>
            </div>
        </div>
    }
}