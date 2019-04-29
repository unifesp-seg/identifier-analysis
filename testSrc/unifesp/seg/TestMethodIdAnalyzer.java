package unifesp.seg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMethodIdAnalyzer {

  
  @Test
  void testUnknown() {
    assertEquals("UNKNOWN", Util.getCaseFormatName("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testLeadUnderscore() {
    assertEquals("LEAD_UNDERSCORE", Util.getCaseFormatName("_TEST"));
  }
  
  @Test
  void testLowerUnderscore() {
    assertEquals("LOWER_UNDERSCORE", Util.getCaseFormatName("a_test"));
  }
  
  @Test
  void testUpperUnderscore() {
    assertEquals("UPPER_UNDERSCORE", Util.getCaseFormatName("A_TEST"));
  }
  
  @Test
  void testLowerCamel() {
    assertEquals("LOWER_CAMEL", Util.getCaseFormatName("oneTest"));
  }
  
  @Test
  void testUpperCamel() {
    assertEquals("UPPER_CAMEL", Util.getCaseFormatName("OneTest"));
  }

  @Test
  void testAllLower() {
    assertEquals("ALL_LOWER", Util.getCaseFormatName("thisisatest"));
  }
  
  @Test
  void testAllUpper() {
    assertEquals("ALL_UPPER", Util.getCaseFormatName("TEST"));
  }
  
  @Test
  void testTermsUnknown() {
    assertEquals(1, Util.numTerms("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testTermsLeadUnderscore() {
    assertEquals(1, Util.numTerms("_TEST"));
  }
  
  @Test
  void testTermsLowerUnderscore() {
    assertEquals(3, Util.numTerms("a_testasd_cool"));
  }
  
  @Test
  void testTermsUpperUnderscore() {
    assertEquals(4, Util.numTerms("A_TEST_DIFFICULT_ONE"));
  }
  
  @Test
  void testTermsLowerCamel() {
    assertEquals(2, Util.numTerms("oneTestasd"));
  }
  
  @Test
  void testTermsUpperCamel() {
    assertEquals(2, Util.numTerms("OneTest"));
  }

  @Test
  void testTermsAllLower() {
    assertEquals(1, Util.numTerms("thisisatest"));
  }
  
  @Test
  void testTermsAllUpper() {
    assertEquals(1, Util.numTerms("TEST"));
  }
  
  @Test
  void testEnglishUnknown() {
    assertFalse(Util.isEnglish("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testEnglishLeadUnderscore() {
    assertTrue(Util.isEnglish("_TEST"));
  }
  
  @Test
  void testEnglishLowerUnderscore() {
    assertFalse(Util.isEnglish("a_testasd_cool"));
  }
  
  @Test
  void testEnglishUpperUnderscore() {
    assertTrue(Util.isEnglish("A_TEST"));
  }
  
  @Test
  void testEnglishLowerCamel() {
    assertFalse(Util.isEnglish("oneTestasd"));
  }
  
  @Test
  void testEnglishUpperCamel() {
    assertTrue(Util.isEnglish("OneTest"));
  }

  @Test
  void testEnglishAllLower() {
    assertFalse(Util.isEnglish("thisisatest"));
  }
  
  @Test
  void testEnglishAllUpper() {
    assertTrue(Util.isEnglish("TEST"));
  }
  
}
