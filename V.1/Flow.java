import java.io.*;
import java.util.*;

public class Flow
{
  public static void main(String[] args)
  {
    if (args.length > 0)
    {

      System.out.println("Reading from " + args[0]);

      try
      {
        symbols = new Hashtable();
        File file = new File(args[0]);
        symbols.put("filepath", file.getParent() + File.pathSeparator);
        parser = new GraphParser(new FileReader(file), symbols);
      }
      catch(FileNotFoundException e)
      {
        System.out.println("The file you specified cannot be found.");
      }
    }
    else
    {
      System.out.println("Please specify a file to compile.");
      return;
    }

    parser.yyparse();

  } /* End main method */

  private static GraphParser parser;
  private static Hashtable symbols;
}