package com.pan.domain.resource;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class AdviceResourceTest {

  /**
   * Testando: POST /advices/credit <br>
   * <p>
   * Cenário: Quando é enviado um retorno da transação de crédito com sucesso
   * <p>
   * retorna 200.
   */
  @Test
  void testCase_POST_credit_01() {

    given().header("Content-Type", ContentType.JSON) //
        .body(mockJsonB200()) //
        .when() //
        .post("/advices/credit") //
        .then() //
        .statusCode(200);
  }

  /**
   * Testando: POST /advices/credit <br>
   * <p>
   * Cenário: Quando é enviado um retorno da transação de crédito com campo null ou vazio
   * <p>
   * retorna 400.
   */
  @Test
  void testCase_POST_credit_02() {

    given().header("Content-Type", ContentType.JSON) //
        .body(mockJsonB400()) //
        .when() //
        .post("/advices/credit") //
        .then() //
        .statusCode(400);
  }

  /**
   * Testando: POST /advices/debit <br>
   * <p>
   * Cenário: Quando é enviado um retorno da transação de débito com sucesso
   * <p>
   * retorna 200.
   */
  @Test
  void testCase_POST_debit_01() {
    given().header("Content-Type", ContentType.JSON) //
        .body(mockJsonB200()) //
        .when() //
        .post("/advices/debit") //
        .then() //
        .statusCode(200);
  }

  /**
   * Testando: POST /advices/debit <br>
   * <p>
   * Cenário: Quando é enviado um retorno da transação de débito com campo null ou vazio
   * <p>
   * retorna 400.
   */
  @Test
  void testCase_POST_debit_02() {
    given().header("Content-Type", ContentType.JSON) //
        .body(mockJsonB400()) //
        .when() //
        .post("/advices/debit") //
        .then() //
        .statusCode(400);
  }


  private String mockJsonB200() {
    return "{\"transactionId\":\"123\", \"customerDocument\":\"44444444\", \"transactionValue\":100, \"createdAt\":\"2024-03-10T12:15:50\", \"brand\":\"ViSA\"}";
  }

  private String mockJsonB400() {
    return "{\"transactionId\":\"123\", \"customerDocument\":\"\", \"transactionValue\":100, \"createdAt\":\"2024-03-10T12:15:50\", \"brand\":\"ViSA\"}";
  }


}