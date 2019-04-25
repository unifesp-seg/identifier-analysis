package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.IllegalFormatException;

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
        try { 
        FileWriters.methodIdAnalysisFW.append(getCaseFormatName(line).toString());
        } catch(IllegalFormatException e) {
        FileWriters.methodIdAnalysisFW.append("UNKNOWN");
        }
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
  
  private CaseFormat getCaseFormatName(String s) throws IllegalFormatException {

    if (s.contains("_")) {

        if (s.toUpperCase().equals(s))
            return CaseFormat.UPPER_UNDERSCORE;

        if (s.toLowerCase().equals(s))
            return CaseFormat.LOWER_UNDERSCORE;

    } else if (s.contains("-")) {

        if (s.toLowerCase().equals(s))
            return CaseFormat.LOWER_HYPHEN;

    } else {

        if (Character.isLowerCase(s.charAt(0))) {

            if (s.matches("([a-z]+[A-Z]+\\w+)+"))
                return CaseFormat.LOWER_CAMEL;

        } else {

            if (s.matches("([A-Z]+[a-z]+\\w+)+"))
                return CaseFormat.UPPER_CAMEL;
        }
    }

    throw new IllegalArgumentException("Couldn't find the case format of the given string.");
  }

}
