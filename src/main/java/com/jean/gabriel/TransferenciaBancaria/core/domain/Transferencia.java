package com.jean.gabriel.TransferenciaBancaria.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transferencia {
    private UUID codigoTransferencia;
    private BigDecimal valorTransferencia;
    private String numeroContaRemetente;
    private String numeroContaDestinaratio;
    private LocalDateTime dataHoraTransferencia;
    private UUID codigoBacen;

    public Transferencia(UUID codigoTransferencia, BigDecimal valorTransferencia, String numeroContaRemetente,
                         String numeroContaDestinaratio, LocalDateTime dataHoraTransferencia, UUID codigoBacen) {
        this.codigoTransferencia = codigoTransferencia;
        this.valorTransferencia = valorTransferencia;
        this.numeroContaRemetente = numeroContaRemetente;
        this.numeroContaDestinaratio = numeroContaDestinaratio;
        this.dataHoraTransferencia = dataHoraTransferencia;
        this.codigoBacen = codigoBacen;
    }

    public BigDecimal getValorTransferencia() {
        return valorTransferencia;
    }
}
