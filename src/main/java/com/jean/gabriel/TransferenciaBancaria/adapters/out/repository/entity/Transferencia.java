package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transferencia")
public class Transferencia {

    @Id
    @Column(name = "codigotransferencia")
    UUID codigoTransferencia;

    @Column(name = "valortransferido")
    BigDecimal valorTransferido;

    @ManyToOne
    @JoinColumn(name = "numerocontaremetente")
    Conta numeroContaRemetente;

    @ManyToOne
    @JoinColumn(name = "numerocontadestinataria")
    Conta numeroContaDestinataria;

    @Column(name = "datahoratransferencia")
    LocalDateTime dataHoraTransferencia;

}
