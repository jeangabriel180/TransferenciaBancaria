package com.jean.gabriel.TransferenciaBancaria.adapters.out.repository;

import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.entity.ContaEntity;
import com.jean.gabriel.TransferenciaBancaria.adapters.out.repository.exception.ErroAoConsultarSaldoException;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Conta;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.SalvarNovoSaldoContasAdapterOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class SalvarNovoSaldoContas implements SalvarNovoSaldoContasAdapterOut {

    private final ContaRepository repository;
    private final static Logger log = LoggerFactory.getLogger(SalvarNovoSaldoContas.class);

    public SalvarNovoSaldoContas(ContaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvarNovoSaldoContas(List<Conta> contas) {
        var tempoRequisicao = System.currentTimeMillis();
        try {
            log.info(INICIO_SALVAR_SALDO_CONTA.getMsg(), contas.get(0).getIdCLiente());
            List<ContaEntity> contasEntity = contas.stream().map(Conta::toEntity).collect(Collectors.toList());
            repository.saveAll(contasEntity);
        } catch (DataAccessException e) {
            log.error(ERRO_SALVAR_SALDO_CONTA.getMsg(), contas.get(0).getIdCLiente());
            throw new ErroAoConsultarSaldoException("Erro ao gravar saldo Conta");
        } finally {
            log.info(FIM_SALVAR_SALDO_CONTA.getMsg(), contas.get(0).getIdCLiente(),
                    System.currentTimeMillis() - tempoRequisicao);
        }
    }
}
