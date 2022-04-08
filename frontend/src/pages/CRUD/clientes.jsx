import './styles.css';

function Clientes() {
    return (
        <>
            <div class="topbar-support">
                <div class="topbar">
                    <ul id="links">
                        <li class="logo">
                            <a href="./index.html"><span id="titulo">LataVelhaInc.</span></a>
                        </li>

                        <li class="linkTopoDireita linkTopo">
                            <a href="./crudLinks.html">admin</a>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="list-container">
                <h1>Lista de Clientes</h1>

                <button class="adiciona" onclick="gerar_criacao()">Adicionar novo cliente</button>

                <div class="container" id="cadastro" style="display: none;">
                    <h3 id="editando"></h3>
                    <form method="POST">
                        <div class="form-row">
                            <div class="form-group">
                                <label>Nome</label>
                                <input type="text" id="nome" />
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input type="text" id="username" placeholder="algumacoisa@email.com" />
                            </div>
                            <div class="form-group">
                                <label>Senha</label>
                                <input type="text" id="password" />
                            </div>
                            <div class="form-group">
                                <label>CPF</label>
                                <input type="text" id="CPF" placeholder="___.___.___-__" />
                            </div>
                            <div class="form-group">
                                <label>Telefone</label>
                                <input type="text" id="telefone" placeholder="____-____" />
                            </div>
                            <div class="form-group">
                                <label>Sexo</label>
                                <input type="text" id="sexo" />
                            </div>
                            <div class="form-group">
                                <label>Nascimento</label>
                                <input type="text" id="nascimento" placeholder="__/__/____" />
                            </div>
                            <div class="form-group">
                                <label>Função</label>
                                <input type="text" id="role" value="ROLE_USER" readonly />
                            </div>
                            <input type="text" name="id" id="indice" hidden />
                        </div>
                    </form>
                    <div class="divSalvar">
                        <button id="salvar" onclick="processar()">Salvar</button>
                    </div>
                    <div>
                        <a href="./clientes.html">Fechar</a>
                    </div>
                </div>

                <table class="table">
                    <thead class="thead-dark">
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

                    <tbody id="tabela-clientes"></tbody>
                </table>
            </div>
        </>
    );
}
export default Clientes;