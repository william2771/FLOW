import java.io.*;
import java.util.*;

/**
   The compiler for the language Flow, created by David Hughes, Stephanie
   Ng, Moses Nakamura, Mark Liu, and William Liu for the Programming
   Languages and Translators class at Columbia University in Spring of
   2011.  This program will compile either the .flow or the .flowg files
   into java code.
*/
public class Flow {
  public static void main(String[] args) {
    if (args.length > 0) {
      //The command-line argument is the name of the file to be compiled
      filename = args[0];
      int index = filename.lastIndexOf(".f"); //find the file extension

      if (index < 0) { //The file extension is not formatted correctly
        System.out.println("Error: Invalid File");
        return;
      }

      filetype = filename.substring(index + 1); //+1 to skip the .
      symbols = new Hashtable();
      File file = new File(filename);
      symbols.put("filepath", file.getParent() + File.separator);

      if (filetype.equals("flowg")) { //The graph spec file
        try {
          gParser = new GraphParser(new FileReader(file), symbols);
          gParser.yyparse();
        }
        catch(FileNotFoundException e) {
          System.out.println("The file you specified cannot be found.");
        }
      }
      else if (filetype.equals("flow")) { //The solver file
        try {
          sParser = new SolverParser(new FileReader(file), symbols);
          sParser.yyparse();
        }
        catch(FileNotFoundException e) {
          System.out.println("The file you specified cannot be found.");
        }
      }
      else { //A different file type
        System.out.println("Error: Invalid File");
        return;
      }
    }
    else { //No command-line arguments
      System.out.println("Please specify a file to compile.");
      return;
    }
  } /* End main method */

  private static String filename;
  private static String filetype;
  private static GraphParser gParser;
  private static SolverParser sParser;
  private static Hashtable symbols;
}