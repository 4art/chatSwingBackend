package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.CryptoPrice;

import java.util.Optional;

public interface CryptoPriceService {
  Optional<CryptoPrice> getCryptoPrice(String currency);
}
