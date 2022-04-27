function Cliente(props) {

    const API = 'http://localhost:8080/clientes';
    const cliente = props.cliente;

    function gerar_edicao(id) {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 200) {
                var cliente = JSON.parse(this.responseText);
                
                document.getElementById("username").value = cliente.username;
                document.getElementById("password").value = cliente.password;
                document.getElementById("role").value = cliente.role;
                document.getElementById("nome").value = cliente.nome;
                document.getElementById("telefone").value = cliente.telefone;
                document.getElementById("sexo").value = cliente.sexo;
                document.getElementById("nascimento").value = cliente.nascimento;
                document.getElementById("CPF").value = cliente.cpf;
                document.getElementById("indice").value = cliente.id;

                document.getElementById("cadastro").style = 'display:block;';
                document.getElementById("editando").innerHTML = 'Editando ' + cliente.nome;
            }
        }
        xhr.send();
    }

    function apagar(id) {
        let xhr = new XMLHttpRequest();
        xhr.open('DELETE', API + '/' + id, true);
        xhr.onload = function () {
            if (this.readyState === 4 && this.status === 204) {
                alert('Cliente removido!');
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
            <td data-label="Id" >{cliente.id}</td>
            <td data-label="Nome" >{cliente.nome}</td>
            <td data-label="Email">{cliente.username}</td>
            <td data-label="CPF">{cliente.cpf}</td>
            <td data-label="Telefone">{cliente.telefone}</td>
            <td data-label="Sexo">{cliente.sexo}</td>
            <td data-label="Nascimento">{cliente.nascimento}</td>
            <td data-label="Ações">
                <button onClick={() => gerar_edicao(cliente.id)}><span>&#x1F58C;</span></button>
                <button onClick={() => apagar(cliente.id)}><span>&#x1F5D1;</span></button>
            </td>
        </tr>
    )
}

export default Cliente;