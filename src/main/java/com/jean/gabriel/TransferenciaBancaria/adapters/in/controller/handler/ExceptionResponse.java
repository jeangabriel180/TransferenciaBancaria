package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller.handler;

public class ExceptionResponse {
    private String mensagem;
    private String causa;

    public ExceptionResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public ExceptionResponse(String mensagem, String causa) {
        this.mensagem = mensagem;
        this.causa = causa;
    }
}
