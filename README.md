# LataVelhaInc. (Site de compra e venda de veiculos usando Spring/Thymeleaf)

## Integrantes ##

- Lucas Vinícius Domingues 769699
- Rafael Yoshio Yamawaki Murata 769681
- Victor Luís Aguilar Antunes 769734

Arquivo com a separação dos requisitos:
[Checklist](doc/checklist.md)

Documento de requisitos:
[Requisitos](doc/Requisitos-A2.pdf)

Configuração Spring:
[Initializr](doc/spring-initializr.png)

Para rodar os testes de operações REST via Postman:
- No aplicativo Postman, vá em File > Import > File e faça o upload do arquivo localizado em:
```
doc/T3-DSW.postman_collection
```
- É recomendado, a fim de evitar possíveis problemas, seguir os testes de acordo com a seguinte ordem dos diretórios: 
```
Clientes > Lojas > Propostas > Veículos
```
- Também é recomendado executar os testes dos diretórios de cima para baixo, pois algumas requisições utilizam os novos objetos criados
- Ex: Cliente -> executar na seguinte ordem:
```
Cria > Lista > Retorna Cliente por ID > Atualiza > Delete
```
- Fique à vontade para realizar quaisquer outras requisições utilizando outros objetos também.  


## Dependências ###
- MySQL (https://www.mysql.com/downloads/)
- Java 11 (https://www.oracle.com/java/technologies/downloads/#java11)
- Postman (https://www.postman.com/downloads/)

### Ubuntu ###
```
sudo apt install mysql-server openjdk-11-jdk
```

## Antes de executar ##
Clone ou baixe o repositório
```
git clone https://github.com/VLx2000/LataVelhaInc-Spring-Thymeleaf.git
```
Acesse o arquivo application.properties localizado em:
```
LataVelhaInc/src/main/resources/application.properties
```
- Nas linhas 3 e 4, coloque seu usuário e senha do MySQL;
- Nas linhas 22 e 23, adicione seu usuário e senha do https://mailtrap.io/ para uso da função de email.

Para correto funcionamento, o site foi previamente povoado com alguns exemplos de clientes e lojas, sendo eles:

Nome | Login | Senha | Tipo
|:---|---|:---:|---:|
Jorge | jorjao@gmail.com | 123 | Cliente
Airton Cena | irto@gmail.com | 123 | Cliente
Michael Wazowski | mw@gmail.com | 123 | Cliente
Carros e Ventiladores SA | carros@gmail.com | 123 | Loja
Ferro velho do Maicao | ferro@gmail.com | 123 | Loja
Tesla saocarlense | teslabr@gmail.com | 123 | Loja
Admin | admin | admin | Administrador

além também de alguns carros e propostas.
Para adicioná-los, dentro da raiz do projeto, acesse o mysql com o seguinte comando:
```
mysql -uroot -p
```
E então execute:
```
source LataVelhaInc/db/MySQL/create.sql;quit;
```
É recomendado copiar a pasta ```LataVelhaInc/src/main/resources/static/images``` para ```/tmp/ultima-pasta-criada-pelo-tomcat``` para o bom funcionamento do upload de arquivos e do carrossel de fotos.

## Para executar ##
1) Acesse a pasta ***LataVelhaInc*** 
```
cd LataVelhaInc/
```
2) Execute
```
./mvnw spring-boot:run
```
3) Copie a pasta ***/images*** para o último diretório criado pelo tomcat dentro de ***/tmp***, ou, em outro terminal, na raiz do projeto execute:
```
chmod +x ./copiar_fotos.sh && ./copiar_fotos.sh
```
4) Em um navegador acesse http://localhost:8080/
5) E, por fim, compre seu Corsa turbinado ou Uno com escada!
