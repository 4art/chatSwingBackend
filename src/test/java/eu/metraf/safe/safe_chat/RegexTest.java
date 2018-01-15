package eu.metraf.safe.safe_chat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;

@SpringBootTest
public class RegexTest {

  Map<String, String> matches;

  @Before
  public void initMatched(){
    matches = new HashMap<>();
    matches.put("info", "\\!info");
    matches.put("hello", "\\!hello");
    matches.put("crypto", "\\!crypto\\.([^.]+)");
    matches.put("weather", "\\!weather\\.([^.]+)");
    matches.put("weatherDefault", "\\!weather");

  }

  @Test
  public void infoTest(){
    String info = "!info";
    assertTrue(info.matches(matches.get("info")));
    assertEquals(findMatch(info), "info");
  }

  @Test
  public void helloTest(){
    String info = "!hello";
    assertTrue(info.matches(matches.get("hello")));
    assertEquals(findMatch(info), "hello");
  }

  @Test
  public void weatherDefaultTest(){
    String info = "!weather";
    assertTrue(info.matches(matches.get("weatherDefault")));
    assertEquals(findMatch(info), "weatherDefault");
  }

  @Test
  public void cryptoTest(){
    String info = "!crypto.bitcoin";
    assertTrue(info.matches(matches.get("crypto")));
    assertEquals(findMatch(info), "crypto");
    String[] s = info.split("\\.");
    assertEquals(s[1], "bitcoin");
  }

  @Test
  public void weatherTest(){
    String info = "!weather.la";
    assertTrue(info.matches(matches.get("weather")));
    assertEquals(findMatch(info), "weather");
    String[] s = info.split("\\.");
    assertEquals(s[1], "la");
  }


  private String findMatch(String s){
    return matches.entrySet().stream().filter(stringStringEntry -> s.matches(stringStringEntry.getValue())).findFirst().orElse(null).getKey();
  }

}
