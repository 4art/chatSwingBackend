package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class WeatherSerciveImpl implements WeatherService {

  @Value("${service.url.weather}")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Optional<Weather> getWeather() {
    return getWeather("");
  }

  @Override
  public Optional<Weather> getWeather(String city) {
    final ResponseEntity<Weather> weatherResponseEntity = restTemplate.getForEntity(
        String.format("%s/%s", url, city), Weather.class
    );
    if (weatherResponseEntity.getStatusCode() == HttpStatus.OK) {

      Weather weather = weatherResponseEntity.getBody();
      return Optional.of(weather);
    }
    return Optional.empty();
  }
}
