package eu.metraf.safe.safe_chat.api;

import eu.metraf.safe.safe_chat.model.Health;
import eu.metraf.safe.safe_chat.model.Message;
import eu.metraf.safe.safe_chat.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ApiController {

  @Autowired
  private MessageRepo messageRepo;

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Health> checkHealth() {
    Health health = new Health();
    health.setSuccess(true);
    return new ResponseEntity<>(health, HttpStatus.OK);
  }

  @RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<Message>> getMessages() {
    List<Message> messages = messageRepo.findAll();
    return new ResponseEntity<>(messages, HttpStatus.OK);
  }

  @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Message> setMessage(@Valid @RequestBody Message message) {
    Message savedMessage = messageRepo.save(message);
    return new ResponseEntity<>(savedMessage, HttpStatus.OK);
  }

}
