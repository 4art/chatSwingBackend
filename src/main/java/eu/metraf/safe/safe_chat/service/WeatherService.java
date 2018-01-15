package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.Weather;

import java.util.Optional;

public interface WeatherService {
  Optional<Weather> getWeather();
  Optional<Weather> getWeather(String name);
}
