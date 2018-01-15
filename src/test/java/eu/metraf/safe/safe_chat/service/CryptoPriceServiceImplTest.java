package eu.metraf.safe.safe_chat.service;

import eu.metraf.safe.safe_chat.model.CryptoPrice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CryptoPriceServiceImplTest {

  @Autowired
  private CryptoPriceService cryptoPriceService;

  @Test
  public void getCryptoPrice(){
    Optional<CryptoPrice> cryptoPrice = cryptoPriceService.getCryptoPrice("bitcoin");
    assertTrue(cryptoPrice.isPresent());
    assertEquals(cryptoPrice.get().getName(), "Bitcoin");
  }

}
