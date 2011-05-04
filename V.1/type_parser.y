%{
  import java.io.*;
  import java.util.*;
%}

%token NODE_T
%token ARC_T
%token INT_T
%token FLT_T
%token STR_T
%token ID

%%

type_def : node_type_def arc_type_def  { try
                                         {
                                           FileWriter writer = new FileWriter(new File("Node.java"));
                                           writer.write($1.sval);
                                           writer.flush();
                                           writer = new FileWriter(new File("Arc.java"));
                                           writer.write($2.sval);
                                           writer.flush(); 
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not produce type files.");
                                         } }
;

node_type_def : NODE_T ID '(' param_list ')' label_list ';'
                                       { symbols.put("node_type", $2.sval);
                                         $$.sval = "public class Node extends flow.structure.SuperNode {\n  public Node(";
                                         boolean comma = false;
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += s[0] + " " + s[1];
                                           comma = true;
                                         }
                                         $$.sval += ") {\n";
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           $$.sval += "    this." + s[1] + " = " + s[1] + ";\n";
                                         }
                                         $$.sval += "  }\n";
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           $$.sval += "  private " + s[0] + " " + s[1] + ";\n  public " + s[1] + " get" + s[0] + "()\n  { return " + s[0] + "; }\n";
                                         }
                                         $$.sval += "}"; 
					symbols.put("node_params", (ArrayList<String[]>)$4.obj);
					}
;

arc_type_def : ARC_T ID '(' param_list ')' ';'
                                       { symbols.put("arc_type", $2.sval);
                                         $$.sval = "public class Arc extends flow.structure.SuperArc {\n  public Arc(Node source, Node dest, ";
                                         boolean comma = false;
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += s[0] + " " + s[1];
                                           comma = true;
                                         }
                                         $$.sval += ") {\n    super(source, dest);\n";
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           $$.sval += "    this." + s[1] + " = " + s[1] + ";\n";
                                         }
                                         $$.sval += "  }\n";
                                         for (String[] s : (ArrayList<String[]>) $4.obj)
                                         {
                                           $$.sval += "  private " + s[0] + " " + s[1] + ";\n  public " + s[0] + " get" + s[1] + "()\n  { return " + s[1] + "; }\n";
                                         }
                                         $$.sval += "}"; 
					symbols.put("arc_params", (ArrayList<String[]>)$4.obj);
					}
;

param_list : param_list ',' param      { $$.obj = $1.obj;
                                         ((ArrayList<String[]>) $$.obj).add((String[])$3.obj); //subsequent params will be put onto the param_list
					}
| param                                { $$.obj = new ArrayList<String[]>(); //the first param will get reduced to param_list
                                         ((ArrayList<String[]>) $$.obj).add((String[])$1.obj); }
| /* empty string */                   { /* nothing */ }
;

//Not too sure about putting the names into the symbol table at this point
//If Node and Arc have a parameter with the same name but diff types, this will break -Mark
param : type ID                        { //symbols.put($2.sval, $1.sval);
                                         //$$.sval = $2.sval; 
					//the object of parameter will store the type name and the id in an anonymous array
					$$.obj = String[]{$1.sval, $2.sval};
					}
;

type : INT_T                           { $$.sval = "int"; }
| FLT_T                                { $$.sval = "double"; }
| STR_T                                { $$.sval = "String"; }
;

label_list : label_list ',' ID         { $$.obj = $1.obj;
                                         ((ArrayList<String>) $$.obj).add($3.sval); }
| ID                                   { $$.obj = new ArrayList<String>();
                                         ((ArrayList<String>) $$.obj).add($1.sval); }
| /* empty string */                   { /* nothing */ }
;

%%

  private TypeLexer lexer;
  private Hashtable symbols;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new TypeParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }

  public TypeParser(Reader r, Hashtable symbols)
  {
    lexer = new TypeLexer(r, this);
    this.symbols = symbols;
  }
