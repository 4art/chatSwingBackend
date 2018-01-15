package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.Message;
import eu.metraf.safe.safe_chat.model.MessageBuilder;
import eu.metraf.safe.safe_chat.model.UserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BotServiceImplTest {

  @Autowired
  private BotService botService;

  private Message message;

  @Before
  public void initMessage(){
    message = new MessageBuilder()
        .withMessage("some message")
        .withLocaltime(LocalDateTime.now())
        .withUser(
            new UserBuilder()
                .withColor("#000000")
                .withUsername("safe")
                .build()
        ).build();
  }

  @Test
  public void testInfo(){
    testCommands("!info");
  }

  @Test
  public void testHello(){
    testCommands("!hello");
  }

  @Test
  public void testCrypto(){
    testCommands("!crypto.bitcoin");
  }

  @Test
  public void testWeatherDefault(){
    testCommands("!weather");
  }

  @Test
  public void testWeather(){
    testCommands("!weather.la");
  }


  private void testCommands(String msg){
    message.setMessage(msg);
    final Optional<Message> optionalMessage = botService.askBot(this.message);
    assertTrue(optionalMessage.isPresent());
    System.out.println(optionalMessage.get().getMessage());
  }

}
