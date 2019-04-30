package unifesp.seg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
  
  private List<String> dict = new ArrayList<String>();
  
  public Dictionary(String file) {
    BufferedReader br;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
      String line = null;
      while ((line = br.readLine()) != null && line.trim().length() > 0) {
        line = line.trim();
        dict.add(line.replaceAll("\'", ""));
      }
    } catch (UnsupportedEncodingException | FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
  public boolean contains(String s) {
    if(dict.contains(s.toLowerCase())) 
      return true;
    return false;
  }
  
  public static void main(String[] args) {
    Dictionary d = new Dictionary("word-list-scowl.txt");
    System.out.println(d.contains("teste"));
  }

}
