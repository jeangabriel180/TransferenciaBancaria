package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller.handler;

import com.google.gson.Gson;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Gson gson = new Gson();

    @ExceptionHandler(ErroAoConsultarSaldoException.class)
    public ResponseEntity<String> handleErrpConsultarSaldo(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                gson.toJson(new ExceptionResponse("Erro ao consultar saldo")));
    }

    @ExceptionHandler(ErroContaNaoEncontradaException.class)
    public ResponseEntity<String> handleErroContaNaoEncontrada(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                gson.toJson(new ExceptionResponse("Erro conta nao encontrada")));
    }

}
