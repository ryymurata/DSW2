const API = "http://localhost:8080/veiculos/modelos/"

function cards(veiculos){
    var catalogo = document.getElementById("catalogo");
    console.log(veiculos);
    var lista_carros = veiculos.map(veiculo => card(veiculo)).join('');
    catalogo.innerHTML = lista_carros;
}

const card = (veiculo) => {
    return `<section class="box">
                <a href=/proposta/comprar/${veiculo.id} class="box-link">
                    <div class="card">
                        <div class="container">
                            <img src=/images/${veiculo.id}/1.jpg alt="Carro">
                        </div>
                        <ul class="dadosCarro">
                            <li class="modelo">${veiculo.modelo}</li>
                            <li>${veiculo.loja}</li>
                            <li>${veiculo.quilometragem + 'km'}</li>
                            <li class="oferta">${'R$' + veiculo.preco}</li>
                        </ul>
                    </div>
                </a>
            </section>`
}

function catalogo(){
    let xhr = new XMLHttpRequest();
    xhr.open('GET', API, true);
    xhr.onload = function() {
        if (this.readyState == 4 && this.status == 200) {
            var veiculos = JSON.parse(this.responseText);
            cards(veiculos);
        }
    }
    xhr.send();
}