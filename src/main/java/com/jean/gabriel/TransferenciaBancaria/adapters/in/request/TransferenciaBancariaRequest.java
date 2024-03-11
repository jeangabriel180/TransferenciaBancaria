package com.jean.gabriel.TransferenciaBancaria.adapters.in.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferenciaBancariaRequest {
        @NotBlank(message = "O campo idContaDestinatario não pode ser nulo")
        private String idContaDestinatario;

        @NotNull(message = "O campo valorTransferencia não pode ser nulo")
        @Positive(message = "O valor da transferência deve ser maior que zero")
        private BigDecimal valorTransferencia;

        public TransferenciaBancariaRequest(String idContaDestinatario, BigDecimal valorTransferencia) {
                this.idContaDestinatario = idContaDestinatario;
                this.valorTransferencia = valorTransferencia;
        }

        public String getIdContaDestinatario() {
                return idContaDestinatario;
        }

        public BigDecimal getValorTransferencia() {
                return valorTransferencia;
        }
}
