package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BotServiceImpl implements BotService {

  @Autowired
  private WeatherService weatherService;

  @Autowired
  private CryptoPriceService cryptoPriceService;

  private Map<String, String> commands;
  private final String botName = "safeBot";

  public BotServiceImpl() {
    commands = new HashMap<>();
    commands.put("info", "\\!info");
    commands.put("hello", "\\!hello");
    commands.put("crypto", "\\!crypto\\.([^.]+)");
    commands.put("weather", "\\!weather\\.([^.]+)");
    commands.put("weatherDefault", "\\!weather");
  }

  @Override
  public Optional<Message> askBot(Message message) {
    Optional<String> command = findMatch(message.getMessage());
    if (command.isPresent()) {
      try {
        Method method = getClass().getMethod(String.format("get%s", ucfirst(command.get())), Message.class);
        return (Optional<Message>) method.invoke(this, message);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    return Optional.empty();
  }

  public Optional<Message> getHello(Message message) {
    return Optional.of(getMessageFromBot(String.format("Hello %s!", message.getUser().getUsername())));
  }

  public Optional<Message> getInfo(Message message) {
    String text = String.format("Hello I'm %s. I know commands from expressions ", botName);
    text = text + commands.entrySet().stream().map(e -> "`" + e.getValue() + "` ").reduce("", String::concat);
    return Optional.of(getMessageFromBot(text));
  }

  public Optional<Message> getWeatherDefault(Message message) {
    Optional<Weather> weatherOptional = weatherService.getWeather();
    if (weatherOptional.isPresent()) {
      Weather weather = weatherOptional.get();
      String text = String.format("In %s now is %.1fC° %s. Local time is %s", weather.getName(), weather.getTemp(), weather.getText(), weather.getLocaltime());
      return Optional.of(getMessageFromBot(text));
    }
    return Optional.empty();
  }

  public Optional<Message> getWeather(Message message) {
    String city = message.getMessage().split("\\.")[1];
    Optional<Weather> weatherOptional = weatherService.getWeather(city);
    if (weatherOptional.isPresent()) {
      Weather weather = weatherOptional.get();
      String text = String.format("In %s now is %.1fC° %s. Local time is %s", weather.getName(), weather.getTemp(), weather.getText(), weather.getLocaltime());
      return Optional.of(getMessageFromBot(text));
    }
    return Optional.empty();
  }

  public Optional<Message> getCrypto(Message message) {
    String currency = message.getMessage().split("\\.")[1];
    Optional<CryptoPrice> cryptoPriceOptional = cryptoPriceService.getCryptoPrice(currency);
    if (cryptoPriceOptional.isPresent()) {
      CryptoPrice cryptoPrice = cryptoPriceOptional.get();
      String text = String.format("Price for %s is %s€. Price was changed in last 24h on %s percent", cryptoPrice.getName(), cryptoPrice.getPrice_eur(), cryptoPrice.getPercent_change_24h());
      return Optional.of(getMessageFromBot(text));
    }
    return Optional.empty();
  }

  private Optional<String> findMatch(String s) {
    final Optional<Map.Entry<String, String>> stringEntry = commands.entrySet().stream().filter(stringStringEntry -> s.matches(stringStringEntry.getValue())).findFirst();
    return Optional.ofNullable(stringEntry.isPresent() ? stringEntry.get().getKey() : null);
  }

  private String ucfirst(String s) {
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }

  private Message getMessageFromBot(String message) {
    return new MessageBuilder()
        .withMessage(message)
        .withLocaltime(LocalDateTime.now(ZoneId.of("Europe/Paris")))
        .withUser(new UserBuilder()
            .withUsername(botName)
            .withColor("green")
            .build())
        .build();
  }
}
