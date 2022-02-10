function msg_email() {
    
    var msg = document.getElementById('mensagem')
    
    document.getElementById('aceitar').href += '?mensagem=' + encodeURI(msg.value)
    document.getElementById('negar').href += '?mensagem=' + encodeURI(msg.value)
    document.getElementById('salvar_mensagem').disabled = true;
}