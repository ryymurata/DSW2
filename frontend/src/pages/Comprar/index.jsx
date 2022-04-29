import { useEffect, useState } from "react";
import Propostas from "@components/Propostas";
import { useParams } from 'react-router-dom';
import './styles.css';

function Comprar() {

    const { id } = useParams();
    const API = "http://localhost:8080/veiculos";
    const API_PROPOSTA = "http://localhost:8080/propostas";

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

    function efetuarCompra() {
        let nova_proposta = {};
        
        nova_proposta.data = new Date().toLocaleDateString();
        nova_proposta.estado = "ABERTO";
        nova_proposta.parcelamento = document.getElementById("parcelamento").value;
        nova_proposta.valor = document.getElementById("valor").value;
        
        const json = JSON.stringify(nova_proposta);

        let xhr = new XMLHttpRequest();
        xhr.open('POST', API_PROPOSTA + '/' + veiculo.loja.id + '/' + veiculo.id, true);
        
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                alert('Deu certo!');
                document.location.reload(true);
            }
            else {
                alert('Ocorreu algum problema');
            }
        }
        
        xhr.send(json);
    }

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
                                    <input type="number" name="valor" id="valor" placeholder="Valor" />
                                </div>
                                <div>
                                    <input type="number" name="parcelamento" id="parcelamento" placeholder="Parcelas" />
                                </div>
                               
                            </form>
                            <button name="Proposta" id="fazerProposta" onClick={() => efetuarCompra()}>Comprar</button>
                        </div>
                    </div>
                    <div className="descricao">
                        <strong>Sobre</strong>
                        <p>{veiculo.descricao}</p>
                    </div>
                    <section id="lista-propostas">
                        <table className="propostas">
                            <Propostas id={veiculo.id} />
                        </table>
                    </section>
                    <a href="/">Voltar</a>
                </div>
            }
        </div>
    );
}

export default Comprar;