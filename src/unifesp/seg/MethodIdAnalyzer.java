package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
        FileWriters.methodIdAnalysisFW.append(Boolean.valueOf(isCamelCase(line)).toString());
        FileWriters.methodIdAnalysisFW.append(",");
        FileWriters.methodIdAnalysisFW.append(Boolean.valueOf(isPascalCase(line)).toString());
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
  
  private boolean isCamelCase(String s) {
    return s.matches("([a-z]+[A-Z]+\\w+)+");
  }
  
  private boolean isPascalCase(String s) {
    return s.matches("([A-Z]+[a-z]+\\w+)+");
  }

}
