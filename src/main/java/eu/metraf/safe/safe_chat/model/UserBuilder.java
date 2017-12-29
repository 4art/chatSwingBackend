package eu.metraf.safe.safe_chat.model;

public class UserBuilder {
  private String username;
  private String color;

  public UserBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder withColor(String color) {
    this.color = color;
    return this;
  }

  public User build(){
    return new User(username, color);
  }
}
