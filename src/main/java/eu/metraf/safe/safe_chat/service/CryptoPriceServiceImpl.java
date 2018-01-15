package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.CryptoPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CryptoPriceServiceImpl implements CryptoPriceService {

  @Value("${service.url.crypto}")
  private String url;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Optional<CryptoPrice> getCryptoPrice(String currency) {
    final ResponseEntity<CryptoPrice> cryptoPriceResponseEntity = restTemplate.getForEntity(
        String.format("%s/%s", url, currency), CryptoPrice.class
    );
    if (cryptoPriceResponseEntity.getStatusCode() == HttpStatus.OK) {

      CryptoPrice cryptoPrice = cryptoPriceResponseEntity.getBody();
      return Optional.of(cryptoPrice);
    }
    return Optional.empty();
  }
}
