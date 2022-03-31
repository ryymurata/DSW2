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
				<td data-label="CPF">${cliente.CPF}</td>
				<td data-label="Telefone">${cliente.telefone}</td>
				<td data-label="Sexo">${cliente.sexo}</td>
				<td data-label="Nascimento">${cliente.nascimento}</td>
				<td data-label="Ações" colspan="2">
					<a class="actions" th:href="@{/cliente/editar/{id} (id=${cliente.id})}" role="button">
							<span th:title="#{object.update}" aria-hidden="true">&#x1F58C;</span>
					</a>
	
					<a class="actions" th:href="@{/cliente/remover/{id} (id=${cliente.id})}" role="button">
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
            var clientes = JSON.parse(this.responseText);
            linhas(clientes);
        }
    }
    xhr.send();
}