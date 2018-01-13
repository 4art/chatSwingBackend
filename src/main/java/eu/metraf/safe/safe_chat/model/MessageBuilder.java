package eu.metraf.safe.safe_chat.model;

import java.time.LocalDateTime;

public class MessageBuilder {
  private User user;
  private String message;
  private LocalDateTime localtime;

  public MessageBuilder withUser(User user) {
    this.user = user;
    return this;
  }

  public MessageBuilder withMessage(String message) {
    this.message = message;
    return this;
  }

  public MessageBuilder withLocaltime(LocalDateTime localtime) {
    this.localtime = localtime;
    return this;
  }

  public Message build(){
    Message message = new Message();
    message.setUser(user);
    message.setMessage(this.message);
    message.setLocaltime(localtime);
    return message;
  }
}
