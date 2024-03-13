package com.jean.gabriel.transferenciaBancaria.api;

import com.google.gson.Gson;
import com.jean.gabriel.transferenciaBancaria.request.BacenRequest;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransferenciaBancariaStepDefinitionTest {
    private HttpResponse<String> responseConta;
    private HttpResponse<String> responseBacen;
    private String idConta;
    private BacenRequest bacenRequest;
    private Gson gson = new Gson();

    @Dado("uma requisição para consultar nome do cliente por id {string}")
    public void umaRequisiçãoParaConsultarNomeDoClientePorId(String idconta) {
        this.idConta = idconta;
    }

    @Quando("eu realizo uma solicitação GET para {string}")
    public void euRealizoUmaSolicitacaoGETPara(String endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endpoint + idConta)).build();
        this.responseConta = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    @Entao("o status da resposta deve ser {int} contendo no corpo da requisicao o nome cliente")
    public void oStatusDaRespostaDeveSerContendoNoCorpoDaRequisicaoONomeCliente(int statusCode) {
        Assertions.assertEquals(200, responseConta.statusCode());
        Assertions.assertEquals("{\n" +
                "  \"id_cliente\":\"00360021003\",\n" +
                "  \"nome_cliente\":\"José Neto Filho\"\n" +
                "}", responseConta.body());
        assertEquals(statusCode, 200);
    }

    @Dado("uma requisição para transferir R$ {double} da conta com id {string} para a conta com id {string}")
    public void umaRequisiçãoParaTransferirR$DaContaComIdParaAContaComId(double valor,
                                                                         String contaRemetente,
                                                                         String contaDestinatario) {

        this.bacenRequest = new BacenRequest(contaRemetente, contaDestinatario,
                new BigDecimal(valor));

    }

    @Quando("eu realizo uma solicitação POST para {string} com o corpo da requisição")
    public void euRealizoUmaSolicitaçãoPOSTParaComOCorpoDaRequisição(String uri) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3001" + uri))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(bacenRequest)))
                .build();
        this.responseBacen = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    @Entao("o status da resposta deve ser {int} o corpo da resposta deve conter o id de registro bacen")
    public void oStatusDaRespostaDeveSerOCorpoDaRespostaDeveConterOIdDeRegistroBacen(int statusCode) {
        assertEquals(statusCode, this.responseBacen.statusCode());
        assertDoesNotThrow(() -> UUID.fromString(responseBacen.body().replace("\"", "")));
    }

}
