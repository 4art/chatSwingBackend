package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.Message;

import java.util.Optional;

public interface BotService {
  Optional<Message> askBot(Message message);
}
