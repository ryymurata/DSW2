const API = "http://localhost:8080/clientes"

function linhas(clientes){
    var tabela_clientes = document.getElementById("tabela-clientes");
    //console.log(clientes);
    var lista_clientes = clientes.map(cliente => linha(cliente)).join('');
    tabela_clientes.innerHTML = lista_clientes;
}

const linha = (cliente) => {
	return `<tr>
				<td data-label="Id" >${cliente.id}</td>
				<td data-label="Nome" >${cliente.nome}</td>
				<td data-label="Email">${cliente.username}</td>
				<td data-label="CPF">${cliente.cpf}</td>
				<td data-label="Telefone">${cliente.telefone}</td>
				<td data-label="Sexo">${cliente.sexo}</td>
				<td data-label="Nascimento">${cliente.nascimento}</td>
				<td data-label="Ações">
					<button onclick="gerar_edicao(${cliente.id})"><span>&#x1F58C;</span></button>
					<button onclick="apagar(${cliente.id})"><span>&#x1F5D1;</span></button>
				</td>
			</tr>`
}

function lista(){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var clientes = JSON.parse(this.responseText);
            linhas(clientes);
        }
    }
    xhr.send();
}

function apagar(id){
	let xhr = new XMLHttpRequest();
    xhr.open('DELETE', API + '/' + id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 204) {
            alert('Cliente removido!');
			document.location.reload(true);
        }
		else{
			alert('Ocorreu algum problema');
		}
    }
    xhr.send();
}

function gerar_criacao(){
    document.getElementById("username").value = '';
    document.getElementById("password").value = '';
    document.getElementById("nome").value = '';
    document.getElementById("telefone").value = '';
    document.getElementById("sexo").value = '';
    document.getElementById("nascimento").value = '';
    document.getElementById("CPF").value = '';
    document.getElementById("indice").value = '';

    document.getElementById("cadastro").style = 'display:block;';
    document.getElementById("editando").innerHTML = 'Criando novo cliente';
}

function tabela(cliente){
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

function gerar_edicao(id){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API + '/' + id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var cliente = JSON.parse(this.responseText);
            tabela(cliente);
        }
    }
    xhr.send();
}

function processar(){
    let info_cliente = new Object();
	info_cliente.username  = document.getElementById("username").value;
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

    if (id != ''){
        xhr.open('PUT', API + '/' + id, true);
    }else{
        xhr.open('POST', API, true);
    }
    
    xhr.setRequestHeader("Content-type","application/json");
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert('Deu certo!');
            document.location.reload(true);
        }
        else{
            alert('Ocorreu algum problema');
        } 
    }
    xhr.send(json);
}