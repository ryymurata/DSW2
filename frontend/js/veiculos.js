const API = "http://localhost:8080/veiculos"
const API_PROPOSTAS = "http://localhost:8080/propostas/veiculos"

function cards(veiculos){
    var catalogo = document.getElementById("catalogo");
    console.log(veiculos);
    var lista_carros = veiculos.map(veiculo => card(veiculo)).join('');
    catalogo.innerHTML = lista_carros;
}

const card = (veiculo) => {
    return `<section class="box">
                <div class="card" onclick="comprar(${veiculo.id})">
                    <div class="container">
                        <img src=./images/${veiculo.id}/1.jpg alt="Carro">
                    </div>
                    <ul class="dadosCarro">
                        <li class="modelo">${veiculo.modelo}</li>
                        <li>${veiculo.loja.nome}</li>
                        <li>${veiculo.quilometragem + 'km'}</li>
                        <li class="oferta">${'R$' + veiculo.preco}</li>
                    </ul>
                </div>
            </section>`;
}

function catalogo(){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API + '/modelos/', true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var veiculos = JSON.parse(this.responseText);
            cards(veiculos);
        }
    }
    xhr.send();
}

function info_carro(veiculo){
    var compra = document.getElementById("carro");
    console.log(veiculo);
    compra.style = 'display:block;';
    compra.innerHTML

    = `<div class="grid">
            <img src="./images/${veiculo.id}/1.jpg" alt="Carro" height="440px">
            <div>
                <ul>
                    <li>${veiculo.modelo} ${veiculo.ano}</li>
                    <li>${veiculo.quilometragem}km</li>
                    <li>${veiculo.loja.nome}</li>
                    <li>Placa: ${veiculo.placa} </li>
                    <li>Chassi: ${veiculo.chassi} </li>
                    <li>R$${veiculo.preco}</li>
                </ul>
                <form id="form_proposta" method="post">
                    <div>
                        <input type="number" name="valor" placeholder="Valor">
                    </div>
                    <div>
                        <input type="number" name="parcelamento" placeholder="Parcelas">
                    </div>
                    <input type="text" name="id" id="indice" hidden />
                    <input type="submit" name="Proposta">
                    
			    </form>
            </div>
        </div>
        <div class="descricao">
            <strong>Sobre</strong>
            <p>${veiculo.descricao}</p>
        </div>
        <button class="adiciona" onclick="listar_propostas()" style="display:block;"}>Listar Propostas do Veículo</button>
        <button onclick="voltar()">Voltar</button>`;
        
    var catalogo = document.getElementById("carros");
    catalogo.style = 'display:none;'
}

function comprar(id){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API + '/' + id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var veiculo = JSON.parse(this.responseText);
            info_carro(veiculo);
        }
    }
    xhr.send();
}

function voltar(){
    document.getElementById("carro").style = 'display:none;';
    document.getElementById("carros").style = 'display:block;';
}


function linhas(propostas){
    var tabela_propostas = document.getElementById("tabela-propostas");
    //console.log(clientes);
    var lista_propostas = propostas.map(proposta => linha(proposta)).join('');
    tabela_propostas.innerHTML = lista_propostas;
}

const linha = (proposta) => {
	return `<tr>
				<td data-label="Id" >${proposta.id}</td>
				<td data-label="Data" >${proposta.data}</td>
				<td data-label="Estado">${proposta.estado}</td>
				<td data-label="Parcelamento">${proposta.parcelamento}</td>
				<td data-label="Valor">${proposta.valor}</td>
			</tr>`
}

function listar_propostas(){
    document.getElementById("lista-propostas").style = 'display:block;';
    var veiculo_id = document.getElementById("indice").value
    console.log(veiculo_id)
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API_PROPOSTAS + '/' + veiculo_id, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var propostas = JSON.parse(this.responseText);
            linhas(propostas);
        }
    }
    xhr.send();
}