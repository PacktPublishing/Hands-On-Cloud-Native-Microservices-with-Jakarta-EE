package com.packtpub.springboot.footballplayermicroservice;

import com.jayway.jsonpath.JsonPath;
import com.packtpub.springboot.footballplayermicroservice.controller.FootballPlayerRESTController;
import com.packtpub.springboot.footballplayermicroservice.model.FootballPlayer;
import java.io.IOException;
import java.math.BigInteger;
import net.minidev.json.JSONArray;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FootballPlayerMicroserviceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FootballPlayerMicroserviceApplicationTests {

    private final HttpHeaders headers = new HttpHeaders();

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void test_1_FindAll() throws IOException {
        System.out.println("findAll");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/footballplayer"),
                HttpMethod.GET, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONArray jsonArray = JsonPath.read(response.getBody(), "$.[*]");
        assertThat(23).isEqualTo(jsonArray.size());
    }

    @Test
    public void test_2_Create() {
        System.out.println("create");
        FootballPlayer player = new FootballPlayer("Mauro", "Vocale", 38,
                "Juventus", "central midfielder", new BigInteger("100"));

        HttpEntity<FootballPlayer> entity = new HttpEntity<>(
                player, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/footballplayer/save"),
                HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(
                "{\"id\":24,\"name\":\"Mauro\",\"surname\":\"Vocale\",\"age\":38,\"team\":\"Juventus\",\"position\":\"central midfielder\",\"price\":100}");

    }

    @Test
    public void test_3_Edit() {
        System.out.println("edit");

        ResponseEntity<FootballPlayer> footballPlayer
                = restTemplate.getForEntity(createURLWithPort(
                        "/footballplayer/show/24"), FootballPlayer.class);
        if (footballPlayer != null && footballPlayer.getBody() != null) {
            footballPlayer.getBody().setPrice(new BigInteger("150"));
            HttpEntity<FootballPlayer> requestEntity = new HttpEntity<>(
                    footballPlayer.getBody());
            System.out.println("requestEntity" + requestEntity);
            ResponseEntity<FootballPlayer> responsePut = restTemplate.exchange(
                    createURLWithPort("/footballplayer/update/24"),
                    HttpMethod.PUT, requestEntity,
                    FootballPlayer.class);

            assertThat(responsePut.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(BigInteger.valueOf(150)).isEqualTo(responsePut.getBody().
                    getPrice());
        }

    }

    @Test
    public void test_4_Find() {
        System.out.println("find");
        ResponseEntity<FootballPlayer> footballPlayer
                = restTemplate.getForEntity(createURLWithPort(
                        "/footballplayer/show/24"), FootballPlayer.class);
        System.out.println("VALORE IN FIND: " + footballPlayer.getBody());
        assertThat(footballPlayer.getStatusCode()).isEqualTo(HttpStatus.OK);
        if (footballPlayer != null && footballPlayer.getBody() != null) {
            assertThat(footballPlayer.getBody().getName()).isEqualTo("Mauro");
            assertThat(footballPlayer.getBody().getSurname()).
                    isEqualTo("Vocale");
            assertThat(footballPlayer.
                    getBody().getPrice()).isEqualTo(BigInteger.valueOf(150));
        }

    }

    @Test
    public void test_5_Remove() {
        System.out.println("remove");
        ResponseEntity<FootballPlayer> responseDelete = restTemplate.exchange(
                createURLWithPort(
                        "/footballplayer/delete/24"), HttpMethod.DELETE, null, FootballPlayer.class);

        assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
