package unifesp.seg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class IdentifierAnalyzer {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length > 0) {
			System.out.println(args[0]);
			String filename = args[0];
			//IdentifierAnalyzer idA = new IdentifierAnalyzer(filename);
            System.out.println("File name: " + filename);
            //idA.handleInput(inputMode, filename);
		}
	}
	
//	br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
//    String line = null;
//    while ((line = br.readLine()) != null && line.trim().length() > 0) {
//        line = line.trim();

}
