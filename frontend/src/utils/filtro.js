function filtro() {
    var filtrar = document.getElementById('filtro');
    var modelos = document.querySelectorAll('.modelo');
    var cards = document.querySelectorAll('.box');
    let cont = 0;
    for (let i = 0; i < cards.length; i++) {
        if (filtrar.value === '') {
            cards[i].style = 'display: block;';
            cont = cards.length;
        }
        else {
            if (modelos[i].textContent.toUpperCase().includes(filtrar.value.toUpperCase())) {
                cards[i].style = 'display: block;';
                cont += 1;
            }
            else {
                cards[i].style = 'display: none;';
            }
        }
    }
    document.getElementById('numero').textContent = cont;
    //console.log('to rodando!')
}

var timer

export function comecar(){
    document.getElementById('numero').style = 'display:block;';
    document.getElementById('legendaN').style = 'display:block;';
    timer = setInterval(filtro,100);
}

export function parar(){
    document.getElementById('numero').style = 'display:none;';
    document.getElementById('legendaN').style = 'display:none;';
    clearInterval(timer);
}