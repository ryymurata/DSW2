import './estilo.css';

function Botao(props) {
    var cor = props.cor;
    return <button className={cor}><a href="/" style={{ color: 'inherit', }}>Tudo</a></button>
}

export default Botao;