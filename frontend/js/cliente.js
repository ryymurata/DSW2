const API = "http://localhost:8080/clientes"

function linhas(clientes){
    var catalogo = document.getElementById("dados-tabela");
    console.log(clientes);
    var lista_carros = clientes.map(cliente => linha(cliente)).join('');
    catalogo.innerHTML = lista_carros;
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
					<button onclick="editar(${cliente.id})"><span>&#x1F58C;</span></button>
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

function criar(){
	let novo_cliente = new Object();
	novo_cliente.username  = document.getElementById("username").value;
    novo_cliente.password = document.getElementById("password").value;
    novo_cliente.role = document.getElementById("role").value;
    novo_cliente.enabled = true;
    novo_cliente.nome = document.getElementById("nome").value;
    novo_cliente.telefone = document.getElementById("telefone").value;
    novo_cliente.sexo = document.getElementById("sexo").value;
    novo_cliente.nascimento = document.getElementById("nascimento").value;
    novo_cliente.cpf = document.getElementById("CPF").value;

	const json = JSON.stringify(novo_cliente);

	let xhr = new XMLHttpRequest();
    xhr.open('POST', API, true);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.onload = function () {
        if (this.status == 200) {
            alert('Cliente criado!');
        }
		else{
			alert('Ocorreu algum problema');
		} 
    }
    xhr.send(json);
	document.location.href = './clientes.html';
}

function editar(id){
	let xhr = new XMLHttpRequest();
    xhr.open('PUT', API + '/' + id, true);
    xhr.onload = function() {
        
    }
    xhr.send();
}