package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.core.domain.Transferencia;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.*;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.exception.ErroTransferenciaBancariaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.jean.gabriel.TransferenciaBancaria.utils.MensagensLogsEnum.*;

@Component
public class TransfenciaBancariaUseCase implements TransferenciaBancariaAdapterIn {

    private final ConsultarContaRemetentePorIdAdapterOut consultarContaRemetentePorIdAdapterOut;
    private final ConsultarContaDestinatarioPorIdAdapterOut consultarContaDestinatarioPorIdAdapterOut;
    private final ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut;
    private final BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut;
    private final NotificarBacenAdapterOut notificarBacenAdapterOut;
    private final SalvarTransferenciaAdapterOut salvarTransferenciaAdapterOut;
    private final SalvarNovoSaldoContasAdapterOut salvarNovoSaldoContasAdapterOut;

    private final static Logger log = LoggerFactory.getLogger(TransfenciaBancariaUseCase.class);


    public TransfenciaBancariaUseCase(ConsultarContaRemetentePorIdAdapterOut consultarContaRemetentePorIdAdapterOut, ConsultarContaDestinatarioPorIdAdapterOut consultarContaDestinatarioPorIdAdapterOut,
                                      ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut,
                                      BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut, NotificarBacenAdapterOut
                                              notificarBacenAdapterOut, SalvarTransferenciaAdapterOut salvarTransferenciaAdapterOut, SalvarNovoSaldoContasAdapterOut salvarNovoSaldoContasAdapterOut) {
        this.consultarContaRemetentePorIdAdapterOut = consultarContaRemetentePorIdAdapterOut;
        this.consultarContaDestinatarioPorIdAdapterOut = consultarContaDestinatarioPorIdAdapterOut;
        this.consultarTotalTransferenciaDiaPorContaAdapterOut = consultarTotalTransferenciaDiaPorContaAdapterOut;
        this.buscarNomeClienteAdapterOut = buscarNomeClienteAdapterOut;
        this.notificarBacenAdapterOut = notificarBacenAdapterOut;
        this.salvarTransferenciaAdapterOut = salvarTransferenciaAdapterOut;
        this.salvarNovoSaldoContasAdapterOut = salvarNovoSaldoContasAdapterOut;
    }

    @Override
    public TransferenciaBancariaResponse executar(String idConta, TransferenciaBancariaRequest request) {
        try {
            log.info(INICIO_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
            var contaRemetente = consultarContaRemetentePorIdAdapterOut.consultarContaPorId(idConta);
            var totalTransferenciaDia = consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(idConta);

            if (totalTransferenciaDia.compareTo(new BigDecimal("1000.0")) > 0)
                throw new ErroTransferenciaBancariaException("Limite diario atingido");

            if (!contaRemetente.getAtiva())
                throw new ErroTransferenciaBancariaException("Conta inativa");

            var nomeCliente = buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente());
            var contaDestinatario = consultarContaDestinatarioPorIdAdapterOut.consultarContaPorId(request.getIdContaDestinatario());

            var novaTransferencia = new Transferencia(
                    null,
                    request.getValorTransferencia(),
                    idConta,
                    request.getIdContaDestinatario(),
                    LocalDateTime.now()
            );

            contaDestinatario.deposito(request.getValorTransferencia());
            contaRemetente.retirada(request.getValorTransferencia());
            salvarNovoSaldoContasAdapterOut.salvarNovoSaldoContas(Arrays.asList(contaRemetente, contaDestinatario));
            salvarTransferenciaAdapterOut.salvar(novaTransferencia);
            var codigoBacenTransferencia = notificarBacenAdapterOut.notificarBacen(idConta, request);

            return new TransferenciaBancariaResponse(nomeCliente, contaRemetente.getNumeroConta(),
                    contaDestinatario.getNumeroConta(), request.getValorTransferencia(), codigoBacenTransferencia);
        } catch (Exception e) {
            log.error(ERRO_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
            throw new ErroTransferenciaBancariaException(e.getMessage());
        } finally {
            log.info(FIM_TRANSFERENCIA_BANCARIA.getMsg(), idConta);
        }
    }
}
