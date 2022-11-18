import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.print.DocFlavor;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.containsString;

public class TesteCliente {

   String endercoApiCliente = "http://localhost:8080/";
   String endpointCliente = "cliente";

   String endpointApagaTodos = "/apagaTodos";
    String listaVazia = "{}";

@Test
@DisplayName("Quando pegar todos os clientes sem cadastrar clientes, então a lista deve estar vazia!")
public void quandoPegarListaSemCadastrar_EntãoListaDeveserservazia () {
    apagaTodosClientesDoServidor();

given()
       .contentType(ContentType.JSON)
.when()
                .get(endercoApiCliente)
.then()
       .statusCode(200)
       .assertThat().body(new IsEqual<>(listaVazia));


    }
@Test
@DisplayName("Quando cadastrar um cliente, então ele deve estar disponivel no resultado")
public void quandoCadastrarCliente_EntaoEleDeveSerSalvoComSucesso() {
String clienteParaCadastrar = "{\n" +
                "\"nome\": \"Vinny\",\n" +
                "\"idade\": 30,\n" +
                " \"id\": \"123456789\"\n" +
                "}";

 String respostaEsperada = "{\"123456789\":{\"nome\":\"Vinny\",\"idade\":30,\"id\":123456789,\"risco\":0}}";
 given()
        .contentType(ContentType.JSON)
        .body(clienteParaCadastrar)
  .when()
         .post(endercoApiCliente+endpointCliente)
   .then()
          .statusCode(HttpStatus.SC_CREATED)
          .assertThat().body(containsString(respostaEsperada));


    }
@Test
@DisplayName("Quando eu Atualizar um cliente, então ele deve estar Atualizado")
public void quandoAtualizarCliente_EntaoEleDeveSerAtualizadoComSucesso() {
    String clienteParaCadastrar = "{\n" +
            "\"nome\": \"Vinny\",\n" +
            "\"idade\": 30,\n" +
            " \"id\": \"123456789\"\n" +
            "}";
    String clienteAtualizado = "{\n" +
            "\"nome\": \"Vinny dos santos\",\n" +
            "\"idade\": 31,\n" +
            " \"id\": \"123456789\"\n" +
            "}";
        String respostaEsperada = "{\"123456789\":{\"nome\":\"Vinny dos santos\",\"idade\":31,\"id\":123456789,\"risco\":0}}";

    given()
            .contentType(ContentType.JSON)
            .body(clienteParaCadastrar)
    .when()
            .post(endercoApiCliente+endpointCliente)
    .then()
            .statusCode(201);



        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)
         .when()
                .put(endercoApiCliente+endpointCliente)
         .then()  .statusCode(200)
                  .assertThat().body(containsString(respostaEsperada));

    }
     @Test
    @DisplayName("Quando deletar um cliente ele deve ser removido com sucesso" )
    public void quandoDeletarCliente_EntaoEleDeveSerDeletadoComSucesso () {
        String clienteParacadastrar = "{\n" +
                "\"nome\": \"Vinny dos santos\",\n" +
                "\"idade\": 31,\n" +
                " \"id\": \"123456789\"\n" +
                "}";
        String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Vinny dos santos, IDADE: 31, ID: 123456789 }";
        given()
                .contentType(ContentType.JSON)
                .body(clienteParacadastrar)
        .when()
                .post(endercoApiCliente+endpointCliente)
        .then()
                .statusCode(HttpStatus.SC_CREATED);
         given()
                 .contentType(ContentType.JSON)
         .when()
                 .delete(endercoApiCliente+endpointCliente+"/123456789")
         .then()
                 .statusCode(HttpStatus.SC_OK)
                 .body(new IsEqual<>(respostaEsperada));
     }
     //Metado de apoio
      public  void apagaTodosClientesDoServidor () {
       String respostaEsperada = "{}";
          given()
                  .contentType(ContentType.JSON)
          .when()
                  .delete(endercoApiCliente+endpointCliente+endpointApagaTodos)
          .then()
                  .statusCode(HttpStatus.SC_OK)
                  .body(new IsEqual<>(listaVazia));
      }
}




