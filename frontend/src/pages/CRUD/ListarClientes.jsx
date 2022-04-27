import Cliente from '@components/Cliente'
import { useEffect, useState } from 'react';
import './styles.css';

function ListarClientes() {
    const API = 'http://localhost:8080/clientes';
    const [clientes, setClientes] = useState('');
    const [carregado, setCarregado] = useState(false);

    /* hook de efeito colateral, executará sempre que houver mudança nos dados */
    useEffect(() => {
        setCarregado(false);
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var veiculo = JSON.parse(this.responseText);
                setClientes(veiculo);
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
        document.getElementById("editando").innerHTML = 'Criando novo cliente';
    }

    function processar() {
        let info_cliente = {};
        info_cliente.username = document.getElementById("username").value;
        info_cliente.password = document.getElementById("password").value;
        info_cliente.role = document.getElementById("role").value;
        info_cliente.enabled = true;
        info_cliente.nome = document.getElementById("nome").value;
        info_cliente.telefone = document.getElementById("telefone").value;
        info_cliente.sexo = document.getElementById("sexo").value;
        info_cliente.nascimento = document.getElementById("nascimento").value;
        info_cliente.cpf = document.getElementById("CPF").value;

        const json = JSON.stringify(info_cliente);

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
            <h1>Lista de Clientes</h1>

            <button className="adiciona" onClick={() => gerar_criacao()}>Adicionar novo cliente</button>

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
                            <input type="text" id="username" placeholder="algumacoisa@email.com" />
                        </div>
                        <div className="form-group">
                            <label>Senha</label>
                            <input type="text" id="password" className='editavel' />
                        </div>
                        <div className="form-group">
                            <label>CPF</label>
                            <input type="text" id="CPF" className='editavel' placeholder="___.___.___-__" />
                        </div>
                        <div className="form-group">
                            <label>Telefone</label>
                            <input type="text" id="telefone" className='editavel' placeholder="____-____" />
                        </div>
                        <div className="form-group">
                            <label>Sexo</label>
                            <input type="text" id="sexo" className='editavel' />
                        </div>
                        <div className="form-group">
                            <label>Nascimento</label>
                            <input type="text" id="nascimento" className='editavel' placeholder="__/__/____" />
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
                    <a href="./clientes">Fechar</a>
                </div>
            </div>

            <table className="table">
                <thead className="thead-dark">
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Cpf</th>
                        <th>Telefone</th>
                        <th>Sexo</th>
                        <th>Nascimento</th>
                        <th>Ação</th>
                    </tr>
                </thead>

                <tbody id="tabela-clientes">
                    {carregado && clientes.map(cliente => (
                        <Cliente key={cliente.id} cliente={cliente} />
                    ))}
                </tbody>
            </table>
        </div>
    );
}
export default ListarClientes;