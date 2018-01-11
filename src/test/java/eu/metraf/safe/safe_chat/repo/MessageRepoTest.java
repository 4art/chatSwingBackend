package eu.metraf.safe.safe_chat.repo;

import eu.metraf.safe.safe_chat.model.Message;
import eu.metraf.safe.safe_chat.model.MessageBuilder;
import eu.metraf.safe.safe_chat.model.UserBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageRepoTest {

  @Autowired
  private MessageRepo messageRepo;
  private Message message;

  @Before
  public void createMessage() {

    message = new MessageBuilder()
        .withMessage("some message")
        .withLocaltime(LocalDateTime.now())
        .withUser(
            new UserBuilder()
            .withColor("#000000")
            .withUsername("safe")
            .build()
        ).build();
    messageRepo.save(message);
  }

  @Test
  public void checkFindAll() {

    List<Message> messages = messageRepo.findAll();
    assertNotNull(messages);
    assertNotEquals(messages.size(), 0);
  }

  @After
  public void removeMessage() {

    messageRepo.delete(message);
  }

}
