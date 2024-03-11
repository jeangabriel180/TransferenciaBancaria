package com.jean.gabriel.TransferenciaBancaria.core.domain;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Conta implements Serializable {
    private String numeroConta;
    private UUID idCLiente;
    private BigDecimal saldo;
    private Boolean ativa;
    private LocalDate dataCriacaoConta;


    public Conta(String numeroConta, UUID idCLiente, BigDecimal saldo, Boolean ativa, LocalDate dataCriacaoConta) {
        this.numeroConta = numeroConta;
        this.idCLiente = idCLiente;
        this.saldo = saldo;
        this.ativa = ativa;
        this.dataCriacaoConta = dataCriacaoConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public UUID getIdCLiente() {
        return idCLiente;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public LocalDate getDataCriacaoConta() {
        return dataCriacaoConta;
    }

    public void deposito(BigDecimal valor) {
        this.saldo = saldo.add(valor);
    }

    public void retirada(BigDecimal valor) throws Exception {
        if (valor.compareTo(this.saldo) > 0) {
            throw new Exception("Saldo Insuficiente");
        }
        this.saldo = saldo.subtract(valor);
    }

    public ContaEntity toEntity() {
        return new ContaEntity(
                this.numeroConta,
                this.idCLiente,
                this.saldo,
                this.ativa,
                this.dataCriacaoConta
        );
    }
}
