package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transferencia")
public class TransferenciaEntity {

    @Id
    @GeneratedValue
    @Column(name = "codigotransferencia", columnDefinition = "BINARY(16)")
    private UUID codigoTransferencia;

    @Column(name = "valortransferido")
    private BigDecimal valorTransferencia;

    @ManyToOne
    @JoinColumn(name = "numerocontaremetente")
    private ContaEntity numeroContaRemetente;

    @ManyToOne
    @JoinColumn(name = "numerocontadestinataria")
    private ContaEntity numeroContaDestinataria;

    @Column(name = "datahoratransferencia")
    private LocalDateTime dataHoraTransferencia;


    public TransferenciaEntity() {
    }

    public TransferenciaEntity(UUID codigoTransferencia, BigDecimal valorTransferencia,
                               ContaEntity numeroContaRemetente, ContaEntity numeroContaDestinataria,
                               LocalDateTime dataHoraTransferencia) {
        this.codigoTransferencia = codigoTransferencia;
        this.valorTransferencia = valorTransferencia;
        this.numeroContaRemetente = numeroContaRemetente;
        this.numeroContaDestinataria = numeroContaDestinataria;
        this.dataHoraTransferencia = dataHoraTransferencia;
    }

    public Transferencia toDomain() {
        return new Transferencia(codigoTransferencia, valorTransferencia, numeroContaRemetente.getNumeroConta(),
                numeroContaDestinataria.getNumeroConta(), dataHoraTransferencia);
    }

}
