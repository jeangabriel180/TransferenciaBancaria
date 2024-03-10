package com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception;

public class ErroAoBuscarNomeClienteException extends RuntimeException {
    public ErroAoBuscarNomeClienteException(String msg, Exception e) {
        super(msg);
    }

    public ErroAoBuscarNomeClienteException() {
        super();
    }

}
