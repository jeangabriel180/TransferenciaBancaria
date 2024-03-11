package com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception;

public class ErroAoBuscarNomeClienteException extends RuntimeException {
    public ErroAoBuscarNomeClienteException(String msg) {
        super(msg);
    }

    public ErroAoBuscarNomeClienteException() {
        super();
    }

}
