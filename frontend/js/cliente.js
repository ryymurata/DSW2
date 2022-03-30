const API = "http://localhost:8080/clientes"

function linhas(clientes){
    var catalogo = document.getElementById("dados-tabela");
    console.log(clientes);
    var lista_carros = clientes.map(cliente => linha(cliente)).join('');
    catalogo.innerHTML = lista_carros;
}

const linha = (cliente) => {
    return `aqui vai a div q tava dentro de tbody q equivale a cada cliente
            (acho q so precisa mexer nisso)`
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