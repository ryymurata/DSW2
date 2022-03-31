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
					<a th:href="@{/loja/editar/{id} (id=${loja.id})}" role="button">
						<span th:title="#{object.update}">&#x1F58C;</span>
					</a>
								
					<a th:href="@{/loja/remover/{id} (id=${loja.id})}" role="button">
						<span th:title="#{object.delete}">&#x1F5D1;</span>
					</a>
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