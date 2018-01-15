package eu.metraf.safe.safe_chat.model;

public class Weather {
  private String name;
  private String region;
  private String country;
  private String localtime;
  private double temp;
  private double wind_speed;
  private String icon;
  private int cloud;
  private String text;
  private int humidity;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getLocaltime() {
    return localtime;
  }

  public void setLocaltime(String localtime) {
    this.localtime = localtime;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public double getWind_speed() {
    return wind_speed;
  }

  public void setWind_speed(double wind_speed) {
    this.wind_speed = wind_speed;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public int getCloud() {
    return cloud;
  }

  public void setCloud(int cloud) {
    this.cloud = cloud;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getHumidity() {
    return humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }
}
