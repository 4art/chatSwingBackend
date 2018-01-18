package eu.metraf.safe.safe_chat.testSettings;

import eu.metraf.safe.safe_chat.model.Message;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public abstract class IntegrationJUnit {
    protected Message message;
}
