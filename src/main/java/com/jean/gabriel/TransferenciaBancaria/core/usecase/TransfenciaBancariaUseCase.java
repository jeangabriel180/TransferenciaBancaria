package com.jean.gabriel.TransferenciaBancaria.core.usecase;

import com.jean.gabriel.TransferenciaBancaria.adapters.in.request.TransferenciaBancariaRequest;
import com.jean.gabriel.TransferenciaBancaria.adapters.in.response.TransferenciaBancariaResponse;
import com.jean.gabriel.TransferenciaBancaria.core.ports.in.TransferenciaBancariaAdapterIn;
import com.jean.gabriel.TransferenciaBancaria.core.ports.out.*;
import com.jean.gabriel.TransferenciaBancaria.core.usecase.exception.ErroTransferenciaBancariaException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransfenciaBancariaUseCase implements TransferenciaBancariaAdapterIn {

    private final ConsultarContaPorIdAdapterOut consultarContaPorIdAdapterOut;
    private final ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut;
    private final BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut;
    private final NotificarBacenAdapterOut notificarBacenAdapterOut;
    //   private final SalvarTransferenciaAdapterOut salvarTransferenciaAdapterOut;

    public TransfenciaBancariaUseCase(ConsultarContaPorIdAdapterOut consultarContaPorIdAdapterOut,
                                      ConsultarTotalTransferenciaDiaPorContaAdapterOut consultarTotalTransferenciaDiaPorContaAdapterOut,
                                      BuscarNomeClienteAdapterOut buscarNomeClienteAdapterOut, NotificarBacenAdapterOut
                                              notificarBacenAdapterOut) {
        this.consultarContaPorIdAdapterOut = consultarContaPorIdAdapterOut;
        this.consultarTotalTransferenciaDiaPorContaAdapterOut = consultarTotalTransferenciaDiaPorContaAdapterOut;
        this.buscarNomeClienteAdapterOut = buscarNomeClienteAdapterOut;
        this.notificarBacenAdapterOut = notificarBacenAdapterOut;
    }

    @Override
    public TransferenciaBancariaResponse executar(String idConta, TransferenciaBancariaRequest request) throws Exception {
        var contaRemetente = consultarContaPorIdAdapterOut.consultarContaPorId(idConta);
        var totalTransferenciaDia = consultarTotalTransferenciaDiaPorContaAdapterOut.consultarTotalTransferenciaDia(idConta);
        var nomeCliente = buscarNomeClienteAdapterOut.buscarNomeCliente(contaRemetente.getIdCLiente());
        var contaDestinatario = consultarContaPorIdAdapterOut.consultarContaPorId(request.idContaDestinatario());


        //TODO acrescentar logs e try catch aqui ver como ísso vai ser capturado na controller
        //TODO criar um respository pra salvar a operacao de transferencia e o novo saldo das contas
        if (!contaRemetente.getAtiva()) throw new ErroTransferenciaBancariaException("Conta inativa");
        if (totalTransferenciaDia.compareTo(new BigDecimal("1000.0")) > 0)
            throw new ErroTransferenciaBancariaException("Limite atingido");

        contaDestinatario.deposito(request.valorTransferencia());
        contaRemetente.retirada(request.valorTransferencia());

        var codigoBacenTransferencia = notificarBacenAdapterOut.notificarBacen(idConta, request);

        return new TransferenciaBancariaResponse(nomeCliente, contaRemetente.getNumeroConta(),
                contaDestinatario.getNumeroConta(), request.valorTransferencia(), codigoBacenTransferencia);
    }
}
