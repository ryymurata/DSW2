import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

function Topbar() {
    return (
        <div className="topbar-support">
            <div className="topbar">
                <ul id="links">
                    <li className="logo">
                        <Link to="/"><span id="titulo">LataVelhaInc.</span></Link>
                    </li>

                    <li className="linkTopoDireita linkTopo">
                        <Link to="/CRUD">admin</Link>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default Topbar;