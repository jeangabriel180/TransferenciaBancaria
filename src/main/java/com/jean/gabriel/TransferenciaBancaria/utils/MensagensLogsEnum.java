package com.jean.gabriel.TransferenciaBancaria.utils;

public enum MensagensLogsEnum {

    INICIO_CONSULTA_SALDO("Iniciando requisicao de consulta de saldo por id conta: {}"),
    FIM_CONSULTA_SALDO("Encerrada requisicao de consulta de saldo por id conta: {} - {} ms"),
    ERRO_CONSULTA_SALDO("Falha ao consultar saldo por id conta: {}"),

    INICIO_CONSULTA_CONTA("Iniciando requisicao de consulta de conta por id conta: {}"),
    FIM_CONSULTA_CONTA("Encerrada requisicao de consulta de conta por id conta: {} - {} ms"),
    ERRO_CONSULTA_CONTA("Falha ao consultar conta por id conta: {}"),

    INICIO_BUSCAR_NOME_CLIENTE("Iniciando busca do nome do cliente com ID: {}"),
    FIM_BUSCAR_NOME_CLIENTE("Encerrada a operação ao consultar nome por IdCliente: {}"),
    ERRO_BUSCAR_NOME_CLIENTE("Erro ao buscar o nome do cliente com ID: {}"),

    INICIO_NOTIFICAR_BACEN("Iniciando notificacao de transferencia com o id Conta: {}"),
    FIM_NOTIFICAR_BACEN("Encerrada operação ao notificar transferencia com o id Conta: {}"),
    ERRO_NOTIFICAR_BACEN("Erro ao notificar bacen com id conta: {}");




    private String msg;

    MensagensLogsEnum(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
