import React, { useEffect, useState } from 'react';
import Cards from '@components/Cards';
import { comecar, parar } from '@utils/filtro';
import Botao from '@utils/botao';
import './styles.css';
import { Link } from 'react-router-dom';

/* utilizando função ao invés de classe por causa do uso dos hooks */
function Inicio() {

    /* usando react hooks de estado (semelhante ao setState) */
    const [listaCarros, setListaCarros] = useState('undefined');
    const [carregado, setCarregado] = useState(false);

    const API = "http://localhost:8080/veiculos";

    /* hook de efeito colateral, executará sempre que houver mudança nos dados */
    useEffect(() => {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var veiculo = JSON.parse(this.responseText);
                setListaCarros(veiculo);
                setCarregado(true);
            }
        }
        xhr.send();
    }, []);/* esse colchete fica vazio pq effect não depende de nenhum valor para fazer requisição */

    function gerar_catalogo_por_loja(id) {

        setCarregado(false)
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API + '/lojas/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var veiculo = JSON.parse(this.responseText);
                setListaCarros(veiculo);
                setCarregado(true);
            }
        }
        xhr.send();
    }

    var vermelho = 'vermelho';

    return (
        <section id="carros">
            
            <div id="menu">
                <Botao cor={vermelho}/>
                <button className="botao-loja" onClick={() => gerar_catalogo_por_loja(5)}>Loja 1</button>
                <button className="botao-loja" onClick={() => gerar_catalogo_por_loja(6)}>Loja 2</button>
                <button className="botao-loja" onClick={() => gerar_catalogo_por_loja(7)}>Loja 3</button>
            </div>

            <div id="divFiltro">
                <input type="text" name="Filtro" placeholder="filtrar carros..." id="filtro" onFocus={comecar} onBlur={parar} />
                <span id="legendaN">veículos</span>
                <span id="numero">-</span>
            </div>
            {/* lista os carros só depois deles terem sido totalmente recebidos */}
            {carregado && listaCarros.map(carro =>
                /* invoca componente filho para cada carro salvo passando como props o carro em questão */
                <Link to={"/comprar/" + carro.id} key={carro.id}>
                    <Cards carro={carro} />
                </Link>
            )}
        </section>
    );
}

export default Inicio;