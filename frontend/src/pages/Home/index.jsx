import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cards from '@components/Cards';
import { comecar, parar } from '@utils/filtro';
import './styles.css';

function Inicio() {

    const [listaCarros, setListaCarros] = useState('undefined');
    const [carregado, setCarregado] = useState(false);

    useEffect(() => {
      axios
        .get('http://localhost:8080/veiculos')
        .then(response => {
            const data = response.data;
            if (data !== 'undefined') {
                //console.log(data)
                setListaCarros(data);
                setCarregado(true);
            }
        });
    }, []);
    
    return (
        <>
            <section id="carros">
                <div id="divFiltro">
                    <input type="text" name="Filtro" placeholder="filtrar carros..." id="filtro" onFocus={comecar} onBlur={parar} />
                    <span id="legendaN">veículos</span>
                    <span id="numero">-</span>
                </div>
                {carregado && listaCarros.map(carro => 
                    <div key={carro.id}>
                        <Cards carro={carro} />
                    </div>
                )}
            </section>
        </>
    );
}

export default Inicio;