function Lojas(props) {

    const API = 'http://localhost:8080/lojas';
    const loja = props.loja;

    function gerar_edicao(id) {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var loja = JSON.parse(this.responseText);
                
                document.getElementById("username").value = loja.username;
                document.getElementById("password").value = loja.password;
                document.getElementById("role").value = loja.role;
                document.getElementById("nome").value = loja.nome;

                document.getElementById("CNPJ").value = loja.cnpj;
                document.getElementById("indice").value = loja.id;
                document.getElementById("descricao").value = loja.id;

                document.getElementById("cadastro").style = 'display:block;';
                document.getElementById("editando").innerHTML = 'Editando ' + loja.nome;
            }
        }
        xhr.send();
    }

    function apagar(id) {
        let xhr = new XMLHttpRequest();
        xhr.open('DELETE', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 204) {
                alert('Loja Removida!');
                document.location.reload(true);
            }
            else {
                alert('Ocorreu algum problema');
            }
        }
        xhr.send();
    }

    return (
        <tr>
            <td data-label="Id" >{loja.id}</td>
            <td data-label="Nome" >{loja.nome}</td>
            <td data-label="Email">{loja.username}</td>
            <td data-label="CNPJ">{loja.cpf}</td>
            <td data-label="Descricao">{loja.cpf}</td>

            <td data-label="Ações">
                <button onClick={() => gerar_edicao(loja.id)}><span>&#x1F58C;</span></button>
                <button onClick={() => apagar(loja.id)}><span>&#x1F5D1;</span></button>
            </td>
        </tr>
    )
}

export default Lojas;