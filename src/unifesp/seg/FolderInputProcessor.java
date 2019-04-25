package unifesp.seg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
public class FolderInputProcessor implements IInputProcessor{
    
    @Override
    public void processInput(String filename) throws FileNotFoundException {
        System.out.println("processing directory: " + filename);
        List<File> files = DirExplorer.finder(filename);
        for (File f : files) {
            try {
                this.analyze(f);
            } catch (FileNotFoundException e) {
                System.out.println("WARN: File not found, skipping file: " + f.getAbsolutePath());
                try {
                    FileWriters.errorsFW.write(f.getAbsolutePath() + System.lineSeparator());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("WARN: unknown error, skippig file: " + f.getAbsolutePath());
                try {
                    FileWriters.errorsFW.write(f.getAbsolutePath() + System.lineSeparator());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
    
    private void analyze(final File file) throws FileNotFoundException {
    	  System.out.println("Analyzing from " + file.getName());
        IdentifierAnalyzer.FILE_COUNTER++;
        if(file.getName().contains("method")) {
          MethodIdAnalyzer ma = new MethodIdAnalyzer(file);
          ma.analyze();
        }
    }

}
