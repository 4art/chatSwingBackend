package eu.metraf.safe.safe_chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Message {
  @Id
  @JsonIgnore
  private String id;
  private User user;
  private String message;
  private LocalDateTime localtime;

  public Message() {
  }

  public Message(User user, String message, LocalDateTime localtime) {
    this.user = user;
    this.message = message;
    this.localtime = localtime;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public LocalDateTime getLocaltime() {
    return localtime;
  }

  public void setLocaltime(LocalDateTime localtime) {
    this.localtime = localtime;
  }
}
