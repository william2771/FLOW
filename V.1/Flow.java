import java.io.*;
import java.util.*;

public class Flow
{
  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try
      {
        symbols = new Hashtable();
        parser = new Parser(new FileReader(args[0]), symbols);
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

  private static Parser parser;
  private static Hashtable symbols;
}