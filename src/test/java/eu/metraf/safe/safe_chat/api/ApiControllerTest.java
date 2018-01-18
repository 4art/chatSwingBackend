package eu.metraf.safe.safe_chat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.metraf.safe.safe_chat.model.*;
import eu.metraf.safe.safe_chat.repo.MessageRepo;
import eu.metraf.safe.safe_chat.testSettings.IntegrationJUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ApiControllerTest extends IntegrationJUnit{

  @Autowired
  private MessageRepo messageRepo;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private List<Message> list = new ArrayList<>();

  @LocalServerPort
  private int port;

  @Value("${x-auth-token}")
  private String token;

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
    final ResponseEntity<List<Message>> listResponseEntity = restTemplate.exchange(String.format("http://localhost:%d/message", port), HttpMethod.GET, new HttpEntity<>(getHeaders(false)), listParameterizedTypeReference);
    assertEquals(listResponseEntity.getStatusCode(), HttpStatus.OK);
    List<Message> messages = listResponseEntity.getBody();
    assertNotNull(messages);
    assertTrue(messages.size() >= 2);
  }

//  @Test
//  public void getMessageNoToken(){
//    ParameterizedTypeReference<List<Message>> listParameterizedTypeReference = new ParameterizedTypeReference<List<Message>>() {
//    };
//    final ResponseEntity<List<Message>> listResponseEntity = restTemplate.exchange(String.format("http://localhost:%d/message", port), HttpMethod.GET, null, listParameterizedTypeReference);
//    assertEquals(listResponseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
//  }
//
//  @Test
//  public void getMessageInvalidToken(){
//    ParameterizedTypeReference<List<Message>> listParameterizedTypeReference = new ParameterizedTypeReference<List<Message>>() {
//    };
//    final ResponseEntity<List<Message>> listResponseEntity = restTemplate.exchange(String.format("http://localhost:%d/message", port), HttpMethod.GET, new HttpEntity<>(getHeaders(true)), listParameterizedTypeReference);
//    assertEquals(listResponseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
//  }

  @Test
  public void setMessage() throws JsonProcessingException {
    ParameterizedTypeReference<Message> reference = new ParameterizedTypeReference<Message>() {
    };
    HttpHeaders headers = getHeaders(false);
    String json = objectMapper.writeValueAsString(list.get(1));
    final ResponseEntity<Message> messageResponseEntity = restTemplate.exchange(String.format("http://localhost:%d/message", port), HttpMethod.POST, new HttpEntity<>(list.get(1), headers), reference);
    assertEquals(messageResponseEntity.getStatusCode(), HttpStatus.OK);
    final Message message = messageResponseEntity.getBody();
    assertNotNull(message);
    assertEquals(list.get(1).getMessage(), message.getMessage());
    list = Arrays.asList(list.get(0), message);
    messageRepo.delete(message);
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

  private HttpHeaders getHeaders(boolean invalidToken){
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    if (invalidToken){
      headers.add("x-auth-token", "qwertyblabla");
    }
    else {
      headers.add("x-auth-token", token);
    }
    return headers;
  }
}
