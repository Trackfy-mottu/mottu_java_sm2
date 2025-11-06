package com.example.challenge_mottu_java;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChallengeMottuJavaApplicationTests {

    private static String BASE_URL = "http://localhost:8080";
    private static String TOKEN;
    private static String TEST_PLACA;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    // ==========================
    // TESTE 1 - LOGIN E AUTENTICAÇÃO
    // ==========================
    @Test
    @Order(1)
    void deveFazerLoginERetornarToken() {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body("{ \"username\": \"alves@gmail.com\", \"password\": \"12345\" }")
                        .when()
                        .post("/api/login")
                        .then()
                        .statusCode(200)
                        .body("token", notNullValue())
                        .body("token.length()", greaterThan(20))
                        .extract()
                        .response();

        JsonPath json = response.jsonPath();
        TOKEN = json.getString("token");

        Assertions.assertNotNull(TOKEN, "Token JWT não pode ser nulo");
        System.out.println("🔑 Token salvo: " + TOKEN.substring(0, 30) + "...");
    }

    // ==========================
    // TESTE 2 - LISTAR PÁTIOS
    // ==========================
    @Test
    @Order(2)
    void deveListarTodosOsPatios() {
        given()
                .when()
                .get("/api/court/all")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].acessCode", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].maxCapacity", notNullValue());
        System.out.println("✅ TESTE 2 PASSOU: Listou os pátios!");
    }

    // ==========================
    // TESTE 3.1 - CRIAR MOTO
    // ==========================
    @Test
    @Order(3)
    void deveCriarMotoNova() {
        Assertions.assertNotNull(TOKEN, "⚠️ Token não encontrado. Execute o TESTE 1 primeiro.");

        // Gerar placa aleatória
        TEST_PLACA = "TEST" + new Random().nextInt(9999);

        String body = "{\n" +
                "  \"placa\": \"" + TEST_PLACA + "\",\n" +
                "  \"idChassi\": 9999999999,\n" +
                "  \"localizacao\": \"DENTRO\",\n" +
                "  \"status\": \"ProntoParaUso\",\n" +
                "  \"modelo\": \"Sport\",\n" +
                "  \"court\": {\n" +
                "    \"acessCode\": \"COURT_MORUMBI\"\n" +
                "  }\n" +
                "}";

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + TOKEN)
                        .body(body)
                        .when()
                        .post("/api/bike")
                        .then()
                        .statusCode(201)
                        .body("placa", equalTo(TEST_PLACA))
                        .body("court.acessCode", equalTo("COURT_MORUMBI"))
                        .extract()
                        .response();

        System.out.println("✅ TESTE 3.1 PASSOU: Moto criada com placa " + TEST_PLACA);
    }

    // ==========================
    // TESTE 3.2 - CONSULTAR MOTO
    // ==========================
    @Test
    @Order(4)
    void deveConsultarMotoCriada() {
        Assertions.assertNotNull(TOKEN, "⚠️ Token não encontrado. Execute o TESTE 1 primeiro.");
        Assertions.assertNotNull(TEST_PLACA, "⚠️ Placa não encontrada. Execute o TESTE 3 primeiro.");

        given()
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/api/bike/" + TEST_PLACA)
                .then()
                .statusCode(200)
                .body("placa", equalTo(TEST_PLACA))
                .body("status", notNullValue())
                .body("modelo", notNullValue())
                .body("localizacao", notNullValue());

        System.out.println("✅ TESTE 3.2 PASSOU: Moto consultada com sucesso!");
    }

    // ==========================
    // TESTE 4 - LISTAR MOTOS DO PÁTIO
    // ==========================
    @Test
    @Order(5)
    void deveListarMotosDoPatio() {
        Assertions.assertNotNull(TOKEN, "⚠️ Token não encontrado. Execute o TESTE 1 primeiro.");
        Assertions.assertNotNull(TEST_PLACA, "⚠️ Placa não encontrada. Execute o TESTE 3 primeiro.");

        given()
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/api/bike/court/COURT_MORUMBI")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("court.acessCode", everyItem(equalTo("COURT_MORUMBI")))
                .body("placa", hasItem(TEST_PLACA));

        System.out.println("✅ TESTE 4 PASSOU: Listou as motos do pátio!");
    }

}
