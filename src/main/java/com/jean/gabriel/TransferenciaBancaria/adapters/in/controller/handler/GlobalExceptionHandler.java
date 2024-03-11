package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller.handler;

import com.google.gson.Gson;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.exception.ErroTransferenciaBancariaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(ErroTransferenciaBancariaException.class)
    public ResponseEntity<String> handleErroTransferencia(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                gson.toJson(new ExceptionResponse("Erro ao realizar transferencia bancaria", e.getMessage())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleErroParse(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                gson.toJson(new ExceptionResponse("Corpo da requisição inválida")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleArgumentException(MethodArgumentNotValidException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                gson.toJson(new ExceptionResponse("Erro ao converter corpo da requisicao",
                        e.getBindingResult().getAllErrors().get(0).getDefaultMessage())));
    }

}
