import { Link } from 'react-router-dom';
import './styles.css';

function Crud() {
    return (
        <section id="crudLinks">
            <div className="crud">
                <Link to="./clientes">Listar Clientes</Link>
            </div>
            <div className="crud">
                <Link to="./lojas">Listar Lojas</Link>
            </div>
        </section>
    );
}

export default Crud;
