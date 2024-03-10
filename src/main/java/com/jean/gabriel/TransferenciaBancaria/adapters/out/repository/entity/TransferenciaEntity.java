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
    @Column(name = "codigotransferencia")
    UUID codigoTransferencia;

    @Column(name = "valortransferido")
    BigDecimal valorTransferencia;

    @ManyToOne
    @JoinColumn(name = "numerocontaremetente")
    ContaEntity numeroContaRemetente;

    @ManyToOne
    @JoinColumn(name = "numerocontadestinataria")
    ContaEntity numeroContaDestinataria;

    @Column(name = "datahoratransferencia")
    LocalDateTime dataHoraTransferencia;

    @Column(name = "codigobacen")
    UUID codigoBacen;


    public Transferencia toDomain() {
        return new Transferencia(codigoTransferencia, valorTransferencia, numeroContaRemetente.numeroConta,
                numeroContaDestinataria.numeroConta, dataHoraTransferencia, codigoBacen);
    }


}
