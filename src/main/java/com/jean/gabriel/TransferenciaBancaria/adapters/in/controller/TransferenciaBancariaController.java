package com.jean.gabriel.TransferenciaBancaria.adapters.in.controller;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.ConsultarSaldoPorIdContaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
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
    public ResponseEntity<TransferenciaBancariaResponse> realizarTransferencia(@RequestBody TransferenciaBancariaRequest request, @RequestHeader String idConta) throws Exception {
        return ResponseEntity.ok(transferenciaBancariaAdapterIn.executar(idConta, request));
    }

}
