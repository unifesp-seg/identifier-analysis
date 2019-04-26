package unifesp.seg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestMethodIdAnalyzer {

  private static List<String> ls = new ArrayList<String>();
  
  @Test
  void testUnknown() {
    assertEquals("UNKNOWN", MethodIdAnalyzer.getCaseFormatName("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testLeadUnderscore() {
    assertEquals("LEAD_UNDERSCORE", MethodIdAnalyzer.getCaseFormatName("_TEST"));
  }
  
  @Test
  void testLowerUnderscore() {
    assertEquals("LOWER_UNDERSCORE", MethodIdAnalyzer.getCaseFormatName("a_test"));
  }
  
  @Test
  void testUpperUnderscore() {
    assertEquals("UPPER_UNDERSCORE", MethodIdAnalyzer.getCaseFormatName("A_TEST"));
  }
  
  @Test
  void testLowerHyphen() {
    assertEquals("LOWER_HYPHEN", MethodIdAnalyzer.getCaseFormatName("a-test"));
  }
  
  @Test
  void testUpperHyphen() {
    assertEquals("UPPER_HYPHEN", MethodIdAnalyzer.getCaseFormatName("A-TEST"));
  }
  
  @Test
  void testLowerCamel() {
    assertEquals("LOWER_CAMEL", MethodIdAnalyzer.getCaseFormatName("oneTest"));
  }
  
  @Test
  void testUpperCamel() {
    assertEquals("UPPER_CAMEL", MethodIdAnalyzer.getCaseFormatName("OneTest"));
  }

  @Test
  void testAllLower() {
    assertEquals("ALL_LOWER", MethodIdAnalyzer.getCaseFormatName("thisisatest"));
  }
  
  @Test
  void testAllUpper() {
    assertEquals("ALL_UPPER", MethodIdAnalyzer.getCaseFormatName("TEST"));
  }
  
  @Test
  void testTermsUnknown() {
    assertEquals(1, MethodIdAnalyzer.numTerms("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testTermsLeadUnderscore() {
    assertEquals(1, MethodIdAnalyzer.numTerms("_TEST"));
  }
  
  @Test
  void testTermsLowerUnderscore() {
    assertEquals(3, MethodIdAnalyzer.numTerms("a_testasd_cool"));
  }
  
  @Test
  void testTermsUpperUnderscore() {
    assertEquals(4, MethodIdAnalyzer.numTerms("A_TEST_DIFFICULT_ONE"));
  }
  
  @Test
  void testTermsLowerHyphen() {
    assertEquals(2, MethodIdAnalyzer.numTerms("alkj-test"));
  }
  
  @Test
  void testTermsUpperHyphen() {
    assertEquals(5, MethodIdAnalyzer.numTerms("A-TEST-WITH-MANY-TERMS"));
  }
  
  @Test
  void testTermsLowerCamel() {
    assertEquals(2, MethodIdAnalyzer.numTerms("oneTestasd"));
  }
  
  @Test
  void testTermsUpperCamel() {
    assertEquals(2, MethodIdAnalyzer.numTerms("OneTest"));
  }

  @Test
  void testTermsAllLower() {
    assertEquals(1, MethodIdAnalyzer.numTerms("thisisatest"));
  }
  
  @Test
  void testTermsAllUpper() {
    assertEquals(1, MethodIdAnalyzer.numTerms("TEST"));
  }
  
  @Test
  void testEnglishUnknown() {
    assertFalse(MethodIdAnalyzer.isEnglish("aTest_WITH_Almost-all-Types"));
  }
  
  @Test
  void testEnglishLeadUnderscore() {
    assertTrue(MethodIdAnalyzer.isEnglish("_TEST"));
  }
  
  @Test
  void testEnglishLowerUnderscore() {
    assertFalse(MethodIdAnalyzer.isEnglish("a_testasd_cool"));
  }
  
  @Test
  void testEnglishUpperUnderscore() {
    assertTrue(MethodIdAnalyzer.isEnglish("A_TEST"));
  }
  
  @Test
  void testEnglishLowerHyphen() {
    assertFalse(MethodIdAnalyzer.isEnglish("alkj-test"));
  }
  
  @Test
  void testEnglishUpperHyphen() {
    assertTrue(MethodIdAnalyzer.isEnglish("A-TEST"));
  }
  
  @Test
  void testEnglishLowerCamel() {
    assertFalse(MethodIdAnalyzer.isEnglish("oneTestasd"));
  }
  
  @Test
  void testEnglishUpperCamel() {
    assertTrue(MethodIdAnalyzer.isEnglish("OneTest"));
  }

  @Test
  void testEnglishAllLower() {
    assertFalse(MethodIdAnalyzer.isEnglish("thisisatest"));
  }
  
  @Test
  void testEnglishAllUpper() {
    assertTrue(MethodIdAnalyzer.isEnglish("TEST"));
  }
  
}
