package org.scribe.utils;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class URLUtilsTest
{
  @Test
  public void shouldPercentEncodeMap()
  {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("key", "value");
    params.put("key with spaces", "value with spaces");
    params.put("&symbols!", "#!");

    String expected = "key=value&key+with+spaces=value+with+spaces&%26symbols%21=%23%21";
    assertEquals(expected, URLUtils.formURLEncodeMap(params));
  }

  @Test
  public void shouldReturnEmptyStringForEmptyMap()
  {
    Map<String, String> params = new LinkedHashMap<String, String>();
    String expected = "";
    assertEquals(expected, URLUtils.formURLEncodeMap(params));
  }

  @Test
  public void shouldFormURLEncodeMapWithMissingValues()
  {
    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("key", "value");
    params.put("key with spaces", null);

    String expected = "key=value&key+with+spaces";
    assertEquals(expected, URLUtils.formURLEncodeMap(params));
  }

  @Test
  public void shouldPercentEncodeString()
  {
    String toEncode = "this is a test &^";
    String expected = "this%20is%20a%20test%20%26%5E";
    assertEquals(expected, URLUtils.percentEncode(toEncode));
  }

  @Test
  public void shouldFormURLEncodeString()
  {
    String toEncode = "this is a test &^";
    String expected = "this+is+a+test+%26%5E";
    assertEquals(expected, URLUtils.formURLEncode(toEncode));
  }

  @Test
  public void shouldFormURLDecodeString()
  {
    String toDecode = "this+is+a+test+%26%5E";
    String expected = "this is a test &^";
    assertEquals(expected, URLUtils.formURLDecode(toDecode));
  }

  @Test
  public void shouldPercentEncodeAllSpecialCharacters()
  {
    String plain = "!*'();:@&=+$,/?#[]";
    String encoded = "%21%2A%27%28%29%3B%3A%40%26%3D%2B%24%2C%2F%3F%23%5B%5D";
    assertEquals(encoded, URLUtils.percentEncode(plain));
    assertEquals(plain, URLUtils.formURLDecode(encoded));
  }

  @Test
  public void shouldNotPercentEncodeReservedCharacters()
  {
    String plain = "abcde123456-._~";
    String encoded = plain;
    assertEquals(encoded, URLUtils.percentEncode(plain));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfMapIsNull()
  {
    Map<String, String> nullMap = null;
    URLUtils.formURLEncodeMap(nullMap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfStringToEncodeIsNull()
  {
    String toEncode = null;
    URLUtils.percentEncode(toEncode);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionIfStringToDecodeIsNull()
  {
    String toDecode = null;
    URLUtils.formURLDecode(toDecode);
  }

  @Test
  public void shouldPercentEncodePlusSymbol()
  {
    String plain = "7aEP+jNAwvjc0mjhqg0nuXPf";
    String encoded = "7aEP%2BjNAwvjc0mjhqg0nuXPf";

    Assert.assertEquals(encoded, URLUtils.percentEncode(plain));
  }

  @Test
  public void shouldURLDecodePlusSymbol()
  {
    String encoded = "oauth_verifier=7aEP%2BjNAwvjc0mjhqg0nuXPf";
    String expected = "oauth_verifier=7aEP+jNAwvjc0mjhqg0nuXPf";

    Assert.assertEquals(expected, URLUtils.formURLDecode(encoded));
  }

  @Test
  public void shouldPercentEncodeCorrectlyTwitterCodingExamples()
  {
    // These tests are part of the Twitter dev examples here -> https://dev.twitter.com/docs/auth/percent-encoding-parameters
    String sources[] = {"Ladies + Gentlemen", "An encoded string!", "Dogs, Cats & Mice"};
    String encoded[] = {"Ladies%20%2B%20Gentlemen", "An%20encoded%20string%21", "Dogs%2C%20Cats%20%26%20Mice"};

    for(int i = 0; i < sources.length; i++)
    {
      Assert.assertEquals(encoded[i], URLUtils.percentEncode(sources[i]));
    }
  }
}
