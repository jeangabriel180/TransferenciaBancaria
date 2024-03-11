package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.ConsultarSaldoPorIdContaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Validated
@RequestMapping("/api/operacoes")
public class TransferenciaBancariaController {

    private final ConsultarSaldoPorIdContaAdapterIn consultarSaldoPorIdContaAdapterIn;
    private final TransferenciaBancariaAdapterIn transferenciaBancariaAdapterIn;

    public TransferenciaBancariaController(ConsultarSaldoPorIdContaAdapterIn consultarSaldoPorIdContaAdapterIn, TransferenciaBancariaAdapterIn transferenciaBancariaAdapterIn) {
        this.consultarSaldoPorIdContaAdapterIn = consultarSaldoPorIdContaAdapterIn;
        this.transferenciaBancariaAdapterIn = transferenciaBancariaAdapterIn;
    }

    @GetMapping("/saldo/{idConta}")
    public ResponseEntity<BigDecimal> getSaldoByIdConta(@PathVariable String idConta) {
        return ResponseEntity.ok(consultarSaldoPorIdContaAdapterIn.executar(idConta));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransferenciaBancariaResponse> realizarTransferencia(
            @Valid @RequestBody TransferenciaBancariaRequest request,
            @RequestHeader String idConta)  {
        return ResponseEntity.ok(transferenciaBancariaAdapterIn.executar(idConta, request));
    }

}
