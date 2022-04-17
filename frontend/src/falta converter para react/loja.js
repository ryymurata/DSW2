const API = "http://localhost:8080/lojas"

function linhas(lojas){
    var tabela_lojas = document.getElementById("tabela-lojas");
    console.log(lojas);
    var lista_lojas = lojas.map(loja => linha(loja)).join('');
    tabela_lojas.innerHTML = lista_lojas;
}

const linha = (loja) => {
	return `<tr>
				<td data-label="Id" >${loja.id}</td>
				<td data-label="Nome" >${loja.nome}</td>
				<td data-label="Email">${loja.username}</td>
				<td data-label="CNPJ">${loja.cnpj}</td>
				<td data-label="Telefone">${loja.descricao}</td>
				<td data-label="Ações" colspan="2">
					<button onclick="gerar_edicao(${loja.id})"><span>&#x1F58C;</span></button>
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

function gerar_criacao(){
    document.getElementById("username").value = '';
    document.getElementById("password").value = '';
    document.getElementById("nome").value = '';
    document.getElementById("CNPJ").value = '';
    document.getElementById("indice").value = '';
	document.getElementById("descricao").value = '';

    document.getElementById("cadastro").style = 'display:block;';
    document.getElementById("editando").innerHTML = 'Criando novo loja';
}

function tabela(loja){
    document.getElementById("username").value = loja.username;
    document.getElementById("password").value = loja.password;
    document.getElementById("role").value = loja.role;
    document.getElementById("nome").value = loja.nome;
    document.getElementById("CNPJ").value = loja.cnpj;
    document.getElementById("indice").value = loja.id;
	document.getElementById("descricao").value = loja.descricao;
	
    document.getElementById("cadastro").style = 'display:block;';
    document.getElementById("editando").innerHTML = 'Editando ' + loja.nome;
}

function gerar_edicao(id){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API + '/' + id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var loja = JSON.parse(this.responseText);
            tabela(loja);
        }
    }
    xhr.send();
}

function processar(){
    let info_loja = new Object();
	info_loja.username  = document.getElementById("username").value;
    info_loja.password = document.getElementById("password").value;
    info_loja.role = document.getElementById("role").value;
    info_loja.enabled = true;
    info_loja.nome = document.getElementById("nome").value;
    info_loja.descricao = document.getElementById("descricao").value;

    info_loja.cnpj = document.getElementById("CNPJ").value;

    const json = JSON.stringify(info_loja);

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