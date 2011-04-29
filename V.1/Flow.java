import java.io.*;
import java.util.*;

public class Flow
{
  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      filename = args[0];
      int index = filename.lastIndexOf(".f");

      if (index < 0)
      {
        System.out.println("Error: Invalid File");
        return;
      }

      filetype = filename.substring(index + 1);
      symbols = new Hashtable();
      File file = new File(filename);
      symbols.put("filepath", file.getParent() + File.separator);

      if (filetype.equals("flowg"))
      {
        try
        {
          gParser = new GraphParser(new FileReader(file), symbols);
          gParser.yyparse();
        }
        catch(FileNotFoundException e)
        {
          System.out.println("The file you specified cannot be found.");
        }
      }
      else if (filetype.equals("flow"))
      {
        try
        {
          sParser = new SolverParser(new FileReader(file), symbols);
          sParser.yyparse();
        }
        catch(FileNotFoundException e)
        {
          System.out.println("The file you specified cannot be found.");
        }
      }
      else
      {
        System.out.println("Error: Invalid File");
        return;
      }
    }
    else
    {
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