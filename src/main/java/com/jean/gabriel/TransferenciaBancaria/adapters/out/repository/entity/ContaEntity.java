package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity;

import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_conta")
public class ContaEntity {

    @Id
    @Column(name = "numeroconta")
    private String numeroConta;

    @Column(name = "idcliente",columnDefinition = "BINARY(16)")
    private UUID idCliente;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "datacriacaoconta")
    private LocalDate DataCriacaoConta;

    public ContaEntity() {
    }

    public ContaEntity(String numeroConta, UUID idCliente, BigDecimal saldo, Boolean ativa, LocalDate dataCriacaoConta) {
        this.numeroConta = numeroConta;
        this.idCliente = idCliente;
        this.saldo = saldo;
        this.ativa = ativa;
        DataCriacaoConta = dataCriacaoConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public Conta toDomain() {
        return new Conta(numeroConta, idCliente, saldo, ativa, DataCriacaoConta);
    }
}
