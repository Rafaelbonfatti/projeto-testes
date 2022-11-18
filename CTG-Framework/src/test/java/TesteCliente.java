import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;

public class TesteCliente {

    String enderecoApicliente = "http://localhost:8080/";
    String endpointCliente = "cliente";
    String endpointApagaTodos = "/apagaTodos";
    String listavazia = "{}";

   @Test
   @DisplayName("Quando pegar todos clientes sem cadastrar clientes, então a lissta deve estar vazia")
   public void pegaTodosClientes () {
       deletaTodosClientes();

       given()
               .contentType(ContentType.JSON)
       .when()
               .get(enderecoApicliente)
       .then()
               .statusCode(200)
               .assertThat().body(new IsEqual<>(listavazia));

   }

   @Test
   @DisplayName("Quando cadastrar um cliente ele deve estar disponovel no resultado")
   public void cadastraCliente() {

      String clienteparaCdastrar = "{\n" +
              "  \"id\": 1001,\n" +
              "  \"idade\": 16,\n" +
              "  \"nome\": \"Rafa\",\n" +
              "  \"risco\": 0\n" +
              "}";


      }


      String respostaEsperada = "{\n" +
              "  \"id\": 1001,\n" +
              "  \"idade\": 16,\n" +
              "  \"nome\": \"Rafa\",\n" +
              "  \"risco\": 0\n" +
              "}";
   given()
                 .contentType(ContentType.JSON)
                   .body(cadastraCliente)
      .when()
               .post(enderecoApicliente+endpointCliente)
      .then()
               .statusCode(201)
                 .assertThat().body(containsString(respostaEsperada));}


   @Test
   @DisplayName("Quando eu atualizar o cliente, então o cliente deve ser atualizaado")
   public void atualizaCliente () {
       String clienteparaCdastrar ="{\n" +
               "  \"id\":8575228,\n" +
               "  \"idade\": 15,\n" +
               "  \"nome\": \"Rafael cantieri\",\n" +
               "  \"risco\": 0\n" +
               "}";

       String clienteAtualizado ="{\n" +
               "  \"id\":8575228,\n" +
               "  \"idade\": 25,\n" +
               "  \"nome\": \"Rafael cantieri\",\n" +
               "  \"risco\": 0\n" +
               "}";
       String respostaEsperada = "{\"8575228\":{\"nome\":\"Rafael cantieri\",\"idade\":25,\"id\":8575228,\"risco\":0}}";

       given()
               .contentType(ContentType.JSON)
               .body(clienteparaCdastrar)
       .when()
               .post(enderecoApicliente+endpointCliente)
       .then()
               .statusCode(201);

       given()
               .contentType(ContentType.JSON)
               .body(clienteAtualizado)
       .when()
               .put(enderecoApicliente+endpointCliente)
       .then()
               .statusCode(200)
               .assertThat().body(containsString(respostaEsperada));

   }

   @Test
   @DisplayName("Quando deletar um cliente ele deve ser removido com sucesso")
   public void deletaClienteporid () {
       String clienteparaCdastrar ="{\n" +
               "  \"id\":8575228,\n" +
               "  \"idade\": 25,\n" +
               "  \"nome\": \"Rafael cantieri\",\n" +
               "  \"risco\": 0\n" +
               "}";

       String respostaEsperada = "CLIENTE REMOVIDO: { NOME: Rafael cantieri, IDADE: 25, ID: 8575228 }";

       given()
               .contentType(ContentType.JSON)
               .body(clienteparaCdastrar)
       .when()
               .post(enderecoApicliente+endpointCliente)
       .then()
               .statusCode(201);

       given()
               .contentType(ContentType.JSON)
       .when()
               .delete(enderecoApicliente+endpointCliente+"/8575228")
       .then()
               .statusCode(200)
               .body(new IsEqual<>(respostaEsperada));


   }

   // Metado de apoio
   public void  deletaTodosClientes () {

       given()
               .contentType(ContentType.JSON)
       .when()
               .delete(enderecoApicliente+endpointCliente+endpointApagaTodos)
       .then()
               .statusCode(200)
               .body(new IsEqual<>(listavazia));
   }
}
