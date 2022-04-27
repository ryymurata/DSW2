import { useEffect, useState } from "react";
import Propostas from "@components/Propostas";
import { useParams } from 'react-router-dom';
import './styles.css';

function Comprar() {

    const { id } = useParams();
    const API = "http://localhost:8080/veiculos";

    const [veiculo, setVeiculo] = useState('undefined');
    const [carregado, setCarregado] = useState(false);

    useEffect(() => {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var veiculo = JSON.parse(this.responseText);
                setVeiculo(veiculo);
                setCarregado(true);
            }
        }
        xhr.send();
    }, [id]);

    return (
        <div className="carro">
            {carregado &&
                <div>
                    <div className="grid">
                        <img src={'/img/' + veiculo.id + '/1.jpg'} alt="Carro" height="440px" />
                        <div>
                            <ul>
                                <li>{veiculo.modelo} {veiculo.ano}</li>
                                <li>{veiculo.quilometragem}km</li>
                                <li>{veiculo.loja.nome}</li>
                                <li>Placa: {veiculo.placa} </li>
                                <li>Chassi: {veiculo.chassi} </li>
                                <li>R${veiculo.preco}</li>
                            </ul>
                            <form id="form_proposta" method="post">
                                <div>
                                    <input type="number" name="valor" placeholder="Valor" />
                                </div>
                                <div>
                                    <input type="number" name="parcelamento" placeholder="Parcelas" />
                                </div>
                                {/* <input type="text" name="id" id="indice" value="${veiculo.id}" hidden /> */}
                                <input type="submit" name="Proposta" />
                            </form>
                        </div>
                    </div>
                    <div className="descricao">
                        <strong>Sobre</strong>
                        <p>{veiculo.descricao}</p>
                    </div>

                    <Propostas id={veiculo.id} />
                    
                    <a href="/">Voltar</a>
                </div>
            }
        </div>
    );
}

export default Comprar;