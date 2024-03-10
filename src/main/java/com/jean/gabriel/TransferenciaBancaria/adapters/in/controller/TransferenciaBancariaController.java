package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller;

import com.jean.gabriel.TransferenciaBancaria.core.ports.in.ConsultarSaldoPorIdContaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.ConsultarSaldoPorIdContaUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/operacoes")
public class TransferenciaBancariaController {

    private final ConsultarSaldoPorIdContaAdapterIn consultarSaldoPorIdContaAdapterIn;

    public TransferenciaBancariaController(ConsultarSaldoPorIdContaUseCase consultarSaldoUseCase) {
        this.consultarSaldoPorIdContaAdapterIn = consultarSaldoUseCase;
    }

    @GetMapping("/saldo/{idConta}")
    public ResponseEntity<BigDecimal> getSaldoByIdConta(@PathVariable String idConta) {
        //TODO criar teste unitario pra esse cara
        return ResponseEntity.ok(consultarSaldoPorIdContaAdapterIn.executar(idConta));
    }

}
