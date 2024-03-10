package com.jean.gabriel.TransferenciaBancaria.adapters.out.api.exception;

public class ErroAoNotificarBacenException extends RuntimeException {
    public ErroAoNotificarBacenException(String msg, Exception e) {
        super(msg);
    }

    public ErroAoNotificarBacenException() {
        super();
    }

}
