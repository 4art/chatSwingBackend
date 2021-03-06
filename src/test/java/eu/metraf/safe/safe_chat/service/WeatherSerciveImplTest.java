package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.Weather;
import eu.metraf.safe.safe_chat.testSettings.IntegrationJUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WeatherSerciveImplTest extends IntegrationJUnit{

  @Autowired
  private WeatherService weatherService;

  @Test
  public void getWeather() {
    Optional<Weather> weather = weatherService.getWeather();
    assertTrue(weather.isPresent());
    assertEquals(weather.get().getName(), "Frankfurt");
  }

  @Test
  public void getWeatherByCityName() {
    Optional<Weather> weather = weatherService.getWeather("Berlin");
    assertTrue(weather.isPresent());
    assertEquals(weather.get().getName(), "Berlin");
  }
}
