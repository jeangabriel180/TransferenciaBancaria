#language: pt

Funcionalidade:  Realizar Transferência Bancária

  Cenario: Consultar nome de cliente por id Conta
    Dado uma requisição para consultar nome do cliente por id "00360021003"
    Quando eu realizo uma solicitação GET para "http://localhost:3000/cadastro/"
    Entao o status da resposta deve ser 200 contendo no corpo da requisicao o nome cliente

  Cenario: Deve notificar bacen
    Dado uma requisição para transferir R$ 500.0 da conta com id "536980120360" para a conta com id "63015990663"
    Quando eu realizo uma solicitação POST para "/bacen" com o corpo da requisição
    Entao o status da resposta deve ser 200 o corpo da resposta deve conter o id de registro bacen

#    Neste exemplo não possuimos ambiente de DEV apenas mock API
