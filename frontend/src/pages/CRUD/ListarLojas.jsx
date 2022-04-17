import './styles.css';

function ListarLojas(props) {
    return (
        <div class="list-container">
            <h1>Lista de Lojas</h1>

            <button class="adiciona" onclick="gerar_criacao()">Adicionar nova Loja</button>

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
                            <input type="text" id="username" />
                        </div>
                        <div class="form-group">
                            <label>Senha</label>
                            <input type="text" id="password" />
                        </div>
                        <div class="form-group">
                            <label>CNPJ</label>
                            <input type="text" id="CNPJ" placeholder="__.___.___/____-__" />
                        </div>
                        <div class="form-group">
                            <label>Descriçao</label>
                            <input type="text" id="descricao" />
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
                    <a href="./lojas.html">Fechar</a>
                </div>
            </div>

            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CNPJ</th>
                        <th>Descriçao</th>
                        <th>Açao</th>
                    </tr>
                </thead>

                <tbody id="tabela-lojas"></tbody>
            </table>
        </div>
    );
}

export default ListarLojas;