package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;

import com.google.common.base.CaseFormat;

public class MethodIdAnalyzer {

  private File f;
  private static Dictionary d = new Dictionary("word-list-scowl.txt");

  public MethodIdAnalyzer(File file) {
    f = file;
  }

  public void analyze() {
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null && line.trim().length() > 0) {
        line = line.trim();
        FileWriters.methodIdAnalysisFW.append(line);
        FileWriters.methodIdAnalysisFW.append(",");
        FileWriters.methodIdAnalysisFW.append(getCaseFormatName(line).toString());
        FileWriters.methodIdAnalysisFW.append(",");
        FileWriters.methodIdAnalysisFW.append(String.valueOf(numTerms(line)));
        FileWriters.methodIdAnalysisFW.append(",");
        FileWriters.methodIdAnalysisFW.append(isEnglish(line) ? "1" : "0");
        FileWriters.methodIdAnalysisFW.append("\n");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
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
    } else if (s.contains("-")) {
      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_HYPHEN.name();
      if (s.toUpperCase().equals(s))
        return "UPPER_HYPHEN";
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
      return checkEach(Arrays.asList(s.split("_")));
    case "LOWER_HYPHEN":
    case "UPPER_HYPHEN":
      return checkEach(Arrays.asList(s.split("-")));
    case "LOWER_CAMEL":
    case "UPPER_CAMEL":
      return checkEach(Arrays.asList(getTermsFromCamel(s)));
    default:
      if(!d.contains(s))
        return false;
    }
    return true;
  }
  
  private static boolean checkEach(List<String> terms) {
    for(String term : terms) {
      if(term.length() > 0 && !d.contains(term)) {
          return false;
      }
    }
    return true;
  }

  public static int numTerms(String s) {
    String format = getCaseFormatName(s);
    switch (format) {
    case "LEAD_UNDERSCORE":
      return Arrays.asList(s.split("_")).size()-1;
    case "UPPER_UNDERSCORE":
    case "LOWER_UNDERSCORE":
      return Arrays.asList(s.split("_")).size();
    case "LOWER_HYPHEN":
    case "UPPER_HYPHEN":
      return Arrays.asList(s.split("-")).size();
    case "LOWER_CAMEL":
    case "UPPER_CAMEL":
      return Arrays.asList(getTermsFromCamel(s)).size();
    default:
      return 1;
    } 
  }

}
