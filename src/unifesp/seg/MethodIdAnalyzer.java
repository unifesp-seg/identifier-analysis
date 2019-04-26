package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import com.google.common.base.CaseFormat;

public class MethodIdAnalyzer {

  private File f;

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

  private static String getCaseFormatName(String s) throws IllegalFormatException {

    if (s.contains("_")) {

      if (s.startsWith("_")) {
        return "LEAD_UNDERSCORE";
      }

      if (s.toUpperCase().equals(s))
        return CaseFormat.UPPER_UNDERSCORE.name();

      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_UNDERSCORE.name();

    } else if (s.contains("-")) {

      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_HYPHEN.name();
      
      if(s.toUpperCase().equals(s)) 
        return "UPPER_HYPHEN";
      
    } else if (Character.isLowerCase(s.charAt(0))) {

      if (s.matches("([a-z]+[A-Z]+\\w+)+"))
        return CaseFormat.LOWER_CAMEL.name();

    } else if (s.matches("([A-Z]+[a-z]+\\w+)+")) 
        return CaseFormat.UPPER_CAMEL.name();
    
    if(s.toLowerCase().equals(s)) {
      return "ALL_LOWER";
    } else if (s.toUpperCase().equals(s)) {
      return "ALL_UPPER";
    }
    return "UNKNOWN";
  }

  public static void main(String[] args) {
    List<String> ls = new ArrayList<String>();
    ls.add("aTest_WITH_Almost-all-Types");
    ls.add("_TEST");
    ls.add("a_test");
    ls.add("A_TEST");
    ls.add("a-test");
    ls.add("A-TEST");
    ls.add("oneTest");
    ls.add("ATest");
    ls.add("thisisatest");
    ls.add("TEST");
    for(String s : ls) 
      System.out.println(s + " : " + getCaseFormatName(s));
    
  }

}
