package com.jean.gabriel.TransferenciaBancaria.core.usecase.exception;

public class ErroTransferenciaBancariaException extends RuntimeException{

    public ErroTransferenciaBancariaException(String msg) {
        super(msg);
    }

    public ErroTransferenciaBancariaException(String msg, String erroMessage) {
        super(msg);
    }

}
