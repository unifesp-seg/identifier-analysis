package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {

  private File f;

  public Analyzer(File file) {
    f = file;
  }

  public void analyze(IdType type) {
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null && line.trim().length() > 0) {
        line = line.trim();
        Writer fw = null;
        switch (type) {
        case METHOD:
          fw = FileWriters.methodIdAnalysisFW;
          addBaseFeatures(line, fw);
          fw.append(",");
          fw.append(Util.containsVerb(line.replace("$", "")) ? "1" : "0");
          break;
        case CLASS:
          fw = FileWriters.classIdAnalysisFW;
          addBaseFeatures(line, fw);
          break;
        case INTERFACE:
          fw = FileWriters.interfaceIdAnalysisFW;
          addBaseFeatures(line, fw);
          break;
        case FIELD:
          fw = FileWriters.fieldIdAnalysisFW;
          addBaseFeatures(line, fw);
          break;
        case VARIABLE:
          fw = FileWriters.variableIdAnalysisFW;
          addBaseFeatures(line, fw);
          break;
        case PARAMETER:
          fw = FileWriters.parameterIdAnalysisFW;
          addBaseFeatures(line, fw);
          break;
        }
        fw.append("\n");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void addBaseFeatures(String line, Writer w) {
    int len = line.length();
    try {
      w.append(line);
      w.append(",");
      w.append(String.valueOf(len));
      w.append(",");
      w.append(Util.getCaseFormatName(line).toString());
      w.append(",");
      w.append(String.valueOf(Util.numTerms(line)));
      w.append(",");
      w.append(Util.isEnglish(line.replace("$", "")) ? "1" : "0");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
