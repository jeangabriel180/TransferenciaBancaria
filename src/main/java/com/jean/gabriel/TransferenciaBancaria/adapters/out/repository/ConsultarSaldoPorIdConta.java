package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarSaldoPorIdContaAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class ConsultarSaldoPorIdConta implements ConsultarSaldoPorIdContaAdapterOut {
    private final ContaRepository contaRepository;
    private final static Logger log = LoggerFactory.getLogger(ConsultarSaldoPorIdConta.class);

    public ConsultarSaldoPorIdConta(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public BigDecimal consultarSaldoPorIdConta(String idConta) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_CONSULTA_SALDO.getMsg(), idConta);
            var conta= contaRepository.findById(idConta).map(ContaEntity::toDomain).orElseThrow(() ->
                    new ErroContaNaoEncontradaException("Conta nao encontrada"));
            return conta.getSaldo();
        } catch (DataAccessException e) {
            log.error(ERRO_CONSULTA_SALDO.getMsg(), idConta);
            throw new ErroAoConsultarSaldoException("Erro ao consultar saldo de conta");
        } finally {
            log.info(FIM_CONSULTA_SALDO.getMsg(), idConta, System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
