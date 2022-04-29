import Loja from '@components/Loja'
import { useEffect, useState } from 'react';
import './styles.css';

function ListarLojas() {
    const API = 'http://localhost:8080/lojas';
    const [lojas, setLojas] = useState('');
    const [carregado, setCarregado] = useState(false);

    /* hook de efeito colateral, executará sempre que houver mudança nos dados */
    useEffect(() => {
        setCarregado(false);
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var lojas = JSON.parse(this.responseText);
                setLojas(lojas);
                setCarregado(true);
            }
        }
        xhr.send();
    }, []);/* esse colchete fica vazio pq effect não depende de nenhum valor para fazer requisição */

    function gerar_criacao() {
        let editaveis = document.getElementsByClassName('editavel');
        for (let i = 0; i < editaveis.length; i++)
            editaveis[i].value = '';

        document.getElementById("cadastro").style = 'display:block;';
        document.getElementById("editando").innerHTML = 'Criando nova Loja';
    }

    function processar() {
        let info_loja = {};
        info_loja.username = document.getElementById("username").value;
        info_loja.password = document.getElementById("password").value;
        info_loja.role = document.getElementById("role").value;
        info_loja.enabled = true;
        info_loja.nome = document.getElementById("nome").value;
        info_loja.cnpj = document.getElementById("CNPJ").value;
        info_loja.descricao = document.getElementById("descricao").value;

        const json = JSON.stringify(info_loja);

        let xhr = new XMLHttpRequest();
        let id = document.getElementById("indice").value;

        if (id !== '') {
            xhr.open('PUT', API + '/' + id, true);
        } else {
            xhr.open('POST', API, true);
        }

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
        <div className="list-container">
            <h1>Lista de Lojas</h1>

            <button className="adiciona" onClick={() => gerar_criacao()}>Adicionar nova Loja</button>

            <div className="container" id="cadastro" style={{ display: 'none', }}>
                <h3 id="editando"> </h3>
                <form method="POST">
                    <div className="form-row">
                        <div className="form-group">
                            <label>Nome</label>
                            <input type="text" id="nome" className='editavel' />
                        </div>
                        <div className="form-group">
                            <label>Email</label>
                            <input type="text" id="username" className='editavel' />
                        </div>
                        <div className="form-group">
                            <label>Senha</label>
                            <input type="text" id="password" className='editavel'/>
                        </div>
                        <div className="form-group">
                            <label>CNPJ</label>
                            <input type="text" id="CNPJ" className='editavel' placeholder="__.___.___/____-__" />
                        </div>
                        <div className="form-group">
                            <label>Descriçao</label>
                            <input type="text" id="descricao" className='editavel'/>
                        </div>

                        <div className="form-group">
                            <label>Função</label>
                            <input type="text" id="role" value="ROLE_USER" readOnly />
                        </div>
                        <input type="text" name="id" id="indice" className='editavel' hidden />
                    </div>
                </form>
                <div className="divSalvar">
                    <button id="salvar" onClick={() => processar()}>Salvar</button>
                </div>
                <div>
                    <a href="./lojas">Fechar</a>
                </div>
            </div>

            <table className="table">
                <thead className="thead-dark">
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CNPJ</th>
                        <th>Descriçao</th>
                        <th>Açao</th>
                    </tr>
                </thead>

                <tbody id="tabela-lojas">
                    {carregado && lojas.map(loja => (
                        <Loja key={loja.id} loja={loja} />
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ListarLojas;