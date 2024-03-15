Transferência Bancária

* Nossa aplicação tem como objetivo realizar a consulta de saldo do cliente por conta e e realizar chamada de solicitação
de Transferência Bancária

Tecnologias Utilizadas:
-> Spring Boot
-> Maven
-> Java 17
-> AuroraDB / MySQL
-> Mockoon para mock de APIs
-> Docker para utilização de banco de dados local
-> Para essa aplicação foi utilizado o Design Pattern de arquitetura hexagonal
-> Cucumber para utilização de testes funcionais ou TaaC

Desenho de arquitetura:
* Dentro da Pasta "diagramaProjeto" estão os desenhos da arquitetura , a primeira proposta de solução é referente ao
projeto designado com a chamada ao Bacen de forma sincrona, a segunda proposta é uma sugestão de solução com a chamada
ao Bacen de forma assíncrona.

Rotas da aplicação :

Post : /api/operacoes/transferencia (Realizar Transferência bancária de uma conta destino para uma remetente)
Get: /saldo/{idConta} (Trazer informação de saldo do cliente por id Conta)

Execução (Local) :
1) Para executar a aplicação localmente primeiro é necessário baixar o Mockoon para simular as rotas externas
2) Importar as requisições da pasta "mockoon" e dar startar as rotas clicando no icone de play
3) È Preciso ter o docker instalado local , então rodar o comando docker-compose up dentro do arquivo "docker"
4) Rodar os scripts dentro da pasta "scripts" para criar as tabelas e inserir registros de exemplo (username: root e senha:password)
5) Iniciar a aplicação


Exemplo chamadas :

GET - http://localhost:8080/api/operacoes/saldo/56984359874

POST - http://localhost:8080/api/operacoes/transferencia

body :

{
    "idContaDestinatario": "56984359874",
    "valorTransferencia" : 10.0
}

header:
idConta : 00360021003


Testes Funcionais (TaaC):
1) Para executar os testes funcionais é preciso que as APis estejam rodando via Mockoon para simular requisições externas
2) Rodar os Testes funcionais atráves da classe "RunTransferenciaBancariaTest"

Obs. Nesse Caso como as APIs são mockadas e não possuimos um ambiente de DEV os testes funcionais
vão retornar as respostas esperadas das APIs externas