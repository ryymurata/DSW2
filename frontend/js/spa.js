const url = "http://localhost:8080/proposta/listarPropostasCliente"

let xhr = new XMLHttpRequest()
xhr.withCredentials = true;

 xhr.onreadystatechange = function(){
        console.log(xhr.responseText)
    }
xhr.open("GET",url,true)

xhr.send()