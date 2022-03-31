const API = "http://localhost:8080/lojas"

function linhas(lojas){
    var catalogo = document.getElementById("dados-tabela");
    console.log(lojas);
    var lista_carros = lojas.map(loja => linha(loja)).join('');
    catalogo.innerHTML = lista_carros;
}

const linha = (loja) => {
	return `<tr>
				<td data-label="Id" >${loja.id}</td>
				<td data-label="Nome" >${loja.nome}</td>
				<td data-label="Email">${loja.username}</td>
				<td data-label="CNPJ">${loja.CNPJ}</td>
				<td data-label="Telefone">${loja.descricao}</td>
				<td data-label="Ações" colspan="2">
					<button onclick="editar(${loja.id})"><span>&#x1F58C;</span></button>
					<button onclick="apagar(${loja.id})"><span>&#x1F5D1;</span></button>
				</td>
		</tr>`
}

function lista(){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var lojas = JSON.parse(this.responseText);
            linhas(lojas);
        }
    }
    xhr.send();
}

function apagar(id){
	let xhr = new XMLHttpRequest();
    xhr.open('DELETE', API + '/' + id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 204) {
            alert('Loja removida!');
			document.location.reload(true);
        }
		else{
			alert('Ocorreu algum problema');
		}
    }
    xhr.send();
}

function criar(){
	let nova_loja = new Object();
	nova_loja.username  = document.getElementById("username").value;
    nova_loja.password = document.getElementById("password").value;
    nova_loja.role = document.getElementById("role").value;
    nova_loja.enabled = true;
    nova_loja.nome = document.getElementById("nome").value;
    nova_loja.descricao = document.getElementById("descricao").value;
    nova_loja.cnpj = document.getElementById("CNPJ").value;

	const json = JSON.stringify(nova_loja);

	let xhr = new XMLHttpRequest();
    xhr.open('POST', API, true);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.onload = function () {
        if (this.status == 200) {
            alert('Loja criada!');
        }
		else{
			alert('Ocorreu algum problema');
		} 
    }
    xhr.send(json);
	document.location.href = './lojas.html';
}

function editar(id){
	let xhr = new XMLHttpRequest();
    xhr.open('PUT', API + '/' + id, true);
    xhr.onload = function() {
        
    }
    xhr.send();
}