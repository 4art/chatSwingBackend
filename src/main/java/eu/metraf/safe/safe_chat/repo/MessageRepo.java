package eu.metraf.safe.safe_chat.repo;

import eu.metraf.safe.safe_chat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepo extends MongoRepository<Message, String> {
}
