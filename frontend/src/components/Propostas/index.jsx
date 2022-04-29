import { Fragment, useEffect, useState } from "react";
import './styles.css';

function Propostas(props) {

    const id = props.id;
    const API = "http://localhost:8080/propostas/veiculos";

    const [carregado, setCarregado] = useState(false);
    const [listaPropostas, setlistaPropostas] = useState('undefined');

    useEffect(() => {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var propostas = JSON.parse(this.responseText);
                setlistaPropostas(propostas);
                setCarregado(true);
            }
        }
        xhr.send();
    }, [id]);

    return (
        <Fragment>
            <thead className="thead-dark">
                <tr>
                    <th>Id</th>
                    <th>Data</th>
                    <th>Estado</th>
                    <th>Parcelamento</th>
                    <th>Valor</th>
                </tr>
            </thead>
            <tbody id="tabela-propostas">
                {carregado ? listaPropostas.map(proposta =>
                    <tr key={proposta.id}>
                        <td data-label="Id" >{proposta.id}</td>
                        <td data-label="Data" >{proposta.data}</td>
                        <td data-label="Estado">{proposta.estado}</td>
                        <td data-label="Parcelamento">{proposta.parcelamento}</td>
                        <td data-label="Valor">{proposta.valor}</td>
                    </tr>
                ) : <tr><td>Não há propostas</td></tr>}
            </tbody>
        </Fragment>
    );
}

export default Propostas;