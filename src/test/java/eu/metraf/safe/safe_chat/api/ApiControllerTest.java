package eu.metraf.safe.safe_chat.api;

import eu.metraf.safe.safe_chat.model.*;
import eu.metraf.safe.safe_chat.repo.MessageRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ApiControllerTest {

  @Autowired
  private MessageRepo messageRepo;

  @Autowired
  private RestTemplate restTemplate;

  private List<Message> list = new ArrayList<>();

  @LocalServerPort
  private int port;

  @Before
  public void setUp() throws Exception {
    list = createListMessage();
    messageRepo.save(list.get(0));
  }

  @After
  public void tearDown() throws Exception {
    list.forEach(message -> messageRepo.delete(message));
  }

  @Test
  public void checkHealth() {
    messageRepo.save(list.get(1));
    final ResponseEntity<Health> healthResponseEntity = restTemplate.getForEntity(String.format("http://localhost:%d/", port), Health.class);
    assertEquals(healthResponseEntity.getStatusCode(), HttpStatus.OK);
    Health health = healthResponseEntity.getBody();
    assertEquals(true, health.isSuccess());
  }

  @Test
  public void getMessages() {
    messageRepo.save(list.get(1));
    ParameterizedTypeReference<List<Message>> listParameterizedTypeReference = new ParameterizedTypeReference<List<Message>>() {
    };
    final ResponseEntity<List<Message>> listResponseEntity = restTemplate.exchange(String.format("http://localhost:%d/message", port), HttpMethod.GET, null, listParameterizedTypeReference);
    assertEquals(listResponseEntity.getStatusCode(), HttpStatus.OK);
    List<Message> messages = listResponseEntity.getBody();
    assertNotNull(messages);
    assertTrue(messages.size() >= 2);
  }

  @Test
  public void setMessage() {
  }

  private List<Message> createListMessage() {

    User user = new UserBuilder()
        .withColor("#000000")
        .withUsername("safe")
        .build();

    return Arrays.asList(
        new MessageBuilder()
            .withMessage("some message")
            .withLocaltime(LocalDateTime.now())
            .withUser(user)
            .build(),
        new MessageBuilder()
            .withMessage("some another message")
            .withLocaltime(LocalDateTime.now())
            .withUser(user)
            .build());
  }
}
