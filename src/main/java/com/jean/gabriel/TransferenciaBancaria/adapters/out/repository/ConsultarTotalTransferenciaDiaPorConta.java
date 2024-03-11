package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.TransferenciaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarTotalTransferenciaDiaPorContaAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class ConsultarTotalTransferenciaDiaPorConta implements ConsultarTotalTransferenciaDiaPorContaAdapterOut {
    private final TransferenciaRepository transferenciaRepository;
    private final static Logger log = LoggerFactory.getLogger(ConsultarTotalTransferenciaDiaPorConta.class);

    public ConsultarTotalTransferenciaDiaPorConta(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    @Override
    public BigDecimal consultarTotalTransferenciaDia(String idConta) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_CONSULTA_SALDO.getMsg(), idConta);
            var transferencias = transferenciaRepository.findByNumeroContaRemetente(idConta);
            return transferencias.stream().map(TransferenciaEntity::toDomain)
                    .map(Transferencia::getValorTransferencia)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (DataAccessException e) {
            log.error(ERRO_CONSULTA_SALDO.getMsg(), idConta);
            throw new ErroAoConsultarSaldoException("Erro ao consultar saldo de conta");
        } finally {
            log.info(FIM_CONSULTA_SALDO.getMsg(), idConta,
                    System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
