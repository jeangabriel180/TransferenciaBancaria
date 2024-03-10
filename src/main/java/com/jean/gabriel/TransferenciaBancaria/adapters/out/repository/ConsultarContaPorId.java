package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroContaNaoEncontradaException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.ConsultarContaPorIdAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class ConsultarContaPorId implements ConsultarContaPorIdAdapterOut {
    private final ContaRepository contaRepository;
    private final static Logger log = LoggerFactory.getLogger(ConsultarContaPorId.class);

    public ConsultarContaPorId(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Conta consultarContaPorId(String idConta) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_CONSULTA_CONTA.getMsg(), idConta);
            return contaRepository.findById(idConta).orElseThrow(() ->
                    new ErroContaNaoEncontradaException("Conta nao encontrada")).toDomain();
        } catch (DataAccessException e) {
            log.error(ERRO_CONSULTA_CONTA.getMsg(), idConta);
            throw new ErroContaNaoEncontradaException("Erro conta nao encontrada");
        } finally {
            log.info(FIM_CONSULTA_CONTA.getMsg(), idConta,
                    System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
