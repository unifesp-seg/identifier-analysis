package unifesp.seg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class IdentifierAnalyzer {

  private static String prefix;
  public static int FILE_COUNTER;
  private String outputDirPath;
  private FolderInputProcessor inputProcessor;
  private String methodAnalysisFileName;
  private String classAnalysisFileName;
  private String interfaceAnalysisFileName;
  private String fieldAnalysisFileName;
  private String varAnalysisFileName;
  private String paramAnalysisFileName;
  private String errorsFileName;

  public IdentifierAnalyzer(String filename) {
    IdentifierAnalyzer.prefix = this.getBaseName(filename);
    this.outputDirPath = IdentifierAnalyzer.prefix + "_analysis_output";
    File outDir = new File(this.outputDirPath);
    if (!outDir.exists()) {
      outDir.mkdirs();
    }
    this.methodAnalysisFileName = "method-analysis.file";
    this.classAnalysisFileName = "class-analysis.file";
    this.interfaceAnalysisFileName = "interface-analysis.file";
    this.fieldAnalysisFileName = "field-analysis.file";
    this.varAnalysisFileName = "variable-analysis.file";
    this.paramAnalysisFileName = "parameter-analysis.file";
    this.errorsFileName = "errors.file";
  }

  private String getBaseName(String path) {
    File inputFile = new File(path);
    String fileName = inputFile.getName();
    int pos = fileName.lastIndexOf(".");
    if (pos > 0) {
      fileName = fileName.substring(0, pos);
    }
    return fileName;
  }

  public static void main(String[] args) throws FileNotFoundException {
    if (args.length > 0) {
      System.out.println(args[0]);
      String filename = args[0];
      IdentifierAnalyzer idA = new IdentifierAnalyzer(filename);
      System.out.println("File name: " + filename);
      idA.handleInput(filename);
    }
  }

  private void initializeWriters() throws IOException {
    FileWriters.methodIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.methodAnalysisFileName, false);
    FileWriters.classIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.classAnalysisFileName, false);
    FileWriters.interfaceIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.interfaceAnalysisFileName, false);
    FileWriters.fieldIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.fieldAnalysisFileName, false);
    FileWriters.variableIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.varAnalysisFileName, false);
    FileWriters.parameterIdAnalysisFW = Util.openFile(this.outputDirPath + File.separator + this.paramAnalysisFileName, false);

    FileWriters.errorsFW = Util.openFile(this.outputDirPath + File.separator + this.errorsFileName, false);
  }

  private void closeWriters() {
    Util.closeOutputFile(FileWriters.methodIdAnalysisFW);
    Util.closeOutputFile(FileWriters.classIdAnalysisFW);
    Util.closeOutputFile(FileWriters.interfaceIdAnalysisFW);
    Util.closeOutputFile(FileWriters.fieldIdAnalysisFW);
    Util.closeOutputFile(FileWriters.variableIdAnalysisFW);
    Util.closeOutputFile(FileWriters.parameterIdAnalysisFW);
    Util.closeOutputFile(FileWriters.errorsFW);
  }

  private void handleInput(String filename) {
    BufferedReader br;
    try {
      this.initializeWriters();
      this.addHeadersToWriters();
      this.inputProcessor = new FolderInputProcessor();

      br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null && line.trim().length() > 0) {
        line = line.trim();
        this.inputProcessor.processInput(line);
      }
      try {
        br.close();
      } catch (IOException e) {
        System.out.println("WARN, couldn't close inputfile: " + filename);
      }

      this.closeWriters();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addHeadersToWriters() {
    try {
      FileWriters.methodIdAnalysisFW.append("identifier,length,format,num_terms,english,contains_verb\n"); // add contains verb (check only for English words)
      FileWriters.classIdAnalysisFW.append("identifier,length,format,num_terms,english,only_nouns\n"); // add contains only nouns? (check only for English words)
      FileWriters.interfaceIdAnalysisFW.append("identifier,length,format,num_terms,english,leading_I,end_ible,end_able,only_nouns\n");
      FileWriters.fieldIdAnalysisFW.append("identifier,length,format,num_terms,english,contains_$,only_nouns\n");
      FileWriters.variableIdAnalysisFW.append("identifier,length,format,num_terms,english,contains_$,single_char,only_nouns\n");
      FileWriters.parameterIdAnalysisFW.append("identifier,length,format,num_terms,english,contains_$,single_char,only_nouns\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
