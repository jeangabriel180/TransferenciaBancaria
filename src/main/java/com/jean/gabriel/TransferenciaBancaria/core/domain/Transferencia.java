package com.jean.gabriel.TransferenciaBancaria.core.domain;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.TransferenciaEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transferencia {
    private UUID codigoTransferencia;
    private BigDecimal valorTransferencia;
    private String numeroContaRemetente;
    private String numeroContaDestinatario;
    private LocalDateTime dataHoraTransferencia;

    public Transferencia(UUID codigoTransferencia, BigDecimal valorTransferencia, String numeroContaRemetente,
                         String numeroContaDestinatario, LocalDateTime dataHoraTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
        this.valorTransferencia = valorTransferencia;
        this.numeroContaRemetente = numeroContaRemetente;
        this.numeroContaDestinatario = numeroContaDestinatario;
        this.dataHoraTransferencia = dataHoraTransferencia;
    }

    public BigDecimal getValorTransferencia() {
        return valorTransferencia;
    }

    public String getNumeroContaRemetente() {
        return numeroContaRemetente;
    }

    public TransferenciaEntity toEntity() {
        return new TransferenciaEntity(
                this.codigoTransferencia,
                this.valorTransferencia,
                new ContaEntity(this.numeroContaRemetente, null, null, null, null),
                new ContaEntity(this.numeroContaDestinatario, null, null, null, null),
                this.dataHoraTransferencia
        );
    }
}
