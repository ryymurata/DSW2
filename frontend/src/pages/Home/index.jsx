import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cards from '@components/Cards';
import { comecar, parar } from '@utils/filtro';
import './styles.css';

/* utilizando função ao invés de classe por causa do uso dos hooks */
function Inicio() {

    /* usando react hooks de estado (semelhante ao setState) */
    const [listaCarros, setListaCarros] = useState('undefined');
    const [carregado, setCarregado] = useState(false);

    /* hook de efeito colateral, executará sempre que houver mudança nos dados */
    useEffect(() => {
        /* utilizando biblioteca externa axios para requisições */
        axios
            .get('http://localhost:8080/veiculos')
            .then(response => {
                /* converte a resposta recebida com .data */
                const data = response.data;
                /* se a resposta estiver ok, realiza set */
                if (data !== 'undefined') {
                    //console.log(data) // eh pra mostrar json de todos os carros
                    setListaCarros(data);
                    setCarregado(true);
                }
            });
    }, []);/* esse colchete fica vazio pq effect não depende de nenhum valor para fazer requisição */

    return (
        <section id="carros">
            <div id="divFiltro">
                <input type="text" name="Filtro" placeholder="filtrar carros..." id="filtro" onFocus={comecar} onBlur={parar} />
                <span id="legendaN">veículos</span>
                <span id="numero">-</span>
            </div>
            {/* lista os carros só depois deles terem sido totalmente recebidos */}
            {carregado && listaCarros.map(carro =>
                /* invoca componente filho para cada carro salvo passando como props o carro em questão */
                <Cards key={carro.id} carro={carro} />
            )}
        </section>
    );
}

export default Inicio;