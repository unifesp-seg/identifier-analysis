package unifesp.seg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

import com.google.common.base.CaseFormat;

public class Util {

  private static Dictionary englishDict = new Dictionary("word-list-scowl.txt");
  private static Dictionary englishVerbs = new Dictionary("wordnet-verbs.txt");
  private static Dictionary englishNouns = new Dictionary("wordnet-nouns.txt");
  
  public static Writer openFile(String filename, boolean append) throws IOException {
    try {
      Writer pWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, append), "UTF-8"),
          1024 * 100);
      return pWriter;

    } catch (IOException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  public static void closeOutputFile(Writer pWriter) {
    if (null != pWriter) {
      try {
        pWriter.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        pWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static String getCaseFormatName(String s) throws IllegalFormatException {
    if (s.contains("_")) {
      if (s.startsWith("_"))
        return "LEAD_UNDERSCORE";
      if (s.toUpperCase().equals(s))
        return CaseFormat.UPPER_UNDERSCORE.name();
      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_UNDERSCORE.name();
    } else if (Character.isLowerCase(s.charAt(0))) {
      if (s.matches("([a-z]+[A-Z]+\\w+)+"))
        return CaseFormat.LOWER_CAMEL.name();
    } else if (s.matches("([A-Z]+[a-z]+\\w+)+"))
      return CaseFormat.UPPER_CAMEL.name();
    if (s.toLowerCase().equals(s)) {
      return "ALL_LOWER";
    } else if (s.toUpperCase().equals(s)) {
      return "ALL_UPPER";
    }
    return "UNKNOWN";
  }

  public static String[] getTermsFromCamel(String s) {
    return s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
  }

  public static boolean isEnglish(String s) {
    String format = getCaseFormatName(s);
    switch (format) {
    case "LEAD_UNDERSCORE":
    case "UPPER_UNDERSCORE":
    case "LOWER_UNDERSCORE":
      return checkEachAnd(Arrays.asList(s.split("_")), englishDict);
    case "LOWER_CAMEL":
    case "UPPER_CAMEL":
      return checkEachAnd(Arrays.asList(getTermsFromCamel(s)), englishDict);
    default:
      if (!englishDict.contains(s))
        return false;
    }
    return true;
  }

  private static boolean checkEachAnd(List<String> terms, Dictionary d) {
    for (String term : terms) {
      if (term.length() > 0 && !d.contains(term)) {
        return false;
      }
    }
    return true;
  }
  
  private static boolean checkEachOr(List<String> terms, Dictionary d) {
    for (String term : terms) {
      if (term.length() > 0 && d.contains(term)) {
        return true;
      }
    }
    return false;
  }

  public static int numTerms(String s) {
    String format = getCaseFormatName(s);
    switch (format) {
    case "LEAD_UNDERSCORE":
      return Arrays.asList(s.split("_")).size() - 1;
    case "UPPER_UNDERSCORE":
    case "LOWER_UNDERSCORE":
      return Arrays.asList(s.split("_")).size();
    case "LOWER_CAMEL":
    case "UPPER_CAMEL":
      return Arrays.asList(getTermsFromCamel(s)).size();
    default:
      return 1;
    }
  }
  
  public static boolean containsVerb(String s) { // contains possible verb
    String format = getCaseFormatName(s);
    switch (format) {
    case "LEAD_UNDERSCORE":
    case "UPPER_UNDERSCORE":
    case "LOWER_UNDERSCORE":
      return checkEachOr(Arrays.asList(s.split("_")), englishVerbs);
    case "LOWER_CAMEL":
    case "UPPER_CAMEL":
      return checkEachOr(Arrays.asList(getTermsFromCamel(s)), englishVerbs);
    default:
      if (!englishVerbs.contains(s))
        return false;
    }
    return true;
  }

}
