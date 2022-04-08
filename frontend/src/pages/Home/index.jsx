import './styles.css';

function Inicio() {
    return (
        <>
            <section id="carros">
                <div id="divFiltro">
                    <input type="text" name="Filtro" placeholder="filtrar carros..." id="filtro" onfocus="comecar()" onblur="parar()" />
                    <span id="legendaN"></span>
                    <span id="numero">-</span>
                </div>
                <div id="catalogo"></div>
                <section id="carro"></section>
            </section>
        </>
    );
}

export default Inicio;