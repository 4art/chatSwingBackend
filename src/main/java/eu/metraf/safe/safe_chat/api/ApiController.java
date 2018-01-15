package eu.metraf.safe.safe_chat.api;

import eu.metraf.safe.safe_chat.model.Health;
import eu.metraf.safe.safe_chat.model.Message;
import eu.metraf.safe.safe_chat.repo.MessageRepo;
import eu.metraf.safe.safe_chat.service.BotService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {

  @Autowired
  private MessageRepo messageRepo;

  @Autowired
  private BotService botService;

  @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Health> checkHealth() {
    Health health = new Health();
    health.setSuccess(true);
    return new ResponseEntity<>(health, HttpStatus.OK);
  }

  @RequestMapping(value = "/message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<Message>> getMessages(HttpServletRequest httpServletRequest,
                                                   @Value("${x-auth-token}") String token) {
    if (httpServletRequest.getHeader("x-auth-token") != null && httpServletRequest.getHeader("x-auth-token").equals(token)) {

      final List<Message> messages = messageRepo.findAll();
      return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ArrayList<Message>(), HttpStatus.UNAUTHORIZED);

  }

  @RequestMapping(value = "/message", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Message> setMessage(@Valid @RequestBody Message message,
                                            HttpServletRequest httpServletRequest,
                                            @Value("${x-auth-token}") String token) {
    if (httpServletRequest.getHeader("x-auth-token") != null && httpServletRequest.getHeader("x-auth-token").equals(token)) {

      final Message savedMessage = messageRepo.save(message);

      final Optional<Message> messageOptional = botService.askBot(message);
      if(messageOptional.isPresent()){
        messageRepo.save(messageOptional.get());
      }

      return new ResponseEntity<>(savedMessage, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

}
