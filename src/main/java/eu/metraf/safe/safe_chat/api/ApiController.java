package eu.metraf.safe.safe_chat.api;

import eu.metraf.safe.safe_chat.model.Health;
import eu.metraf.safe.safe_chat.model.Message;
import eu.metraf.safe.safe_chat.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class ApiController {

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Health> checkHealth() {
    Health health = new Health();
    health.setSuccess(true);
    return new ResponseEntity<>(health, HttpStatus.OK);
  }

  @RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<Message>> getMessages() {
    Message message = new Message();
    User user = new User();
    message.setLocaltime(LocalDateTime.now());
    message.setMessage("some message");
    user.setColor("#000000");
    user.setUsername("safe");
    message.setUser(user);
    return new ResponseEntity<>(Arrays.asList(message), HttpStatus.OK);
  }

  @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Message> setMessage(@RequestBody Message message) {
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

}
