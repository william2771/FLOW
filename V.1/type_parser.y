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
                                           FileWriter writer = new FileWriter(new File(symbols.get("node_type") + ".java"));
                                           writer.write($1.sval);
                                           writer.flush();
                                           writer = new FileWriter(new File(symbols.get("arc_type") + ".java"));
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
                                         $$.sval = "public class " + $2.sval + " extends flow.structure.Node {\n  public " + $2.sval + "(";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += symbols.get(s) + " " + s;
                                           comma = true;
                                         }
                                         $$.sval += ") {\n";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "    this." + s + " = " + s + ";\n";
                                         }
                                         $$.sval += "  }\n";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "  private " + symbols.get(s) + " " + s + ";\n  public " + symbols.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "}"; }
;

arc_type_def : ARC_T ID '(' param_list ')' ';'
                                       { symbols.put("arc_type", $2.sval);
                                         $$.sval = "public class " + $2.sval + " extends flow.structure.Arc {\n  public " + $2.sval + "(" + symbols.get("node_type") + " source, " + symbols.get("node_type") + " dest, ";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += symbols.get(s) + " " + s;
                                           comma = true;
                                         }
                                         $$.sval += ") {\n    super(source, dest);\n";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "    this." + s + " = " + s + ";\n";
                                         }
                                         $$.sval += "  }\n";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "  private " + symbols.get(s) + " " + s + ";\n  public " + symbols.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "}"; }
;

param_list : param_list ',' param      { $$.obj = $1.obj;
                                         ((ArrayList<String>) $$.obj).add($3.sval); }
| param                                { $$.obj = new ArrayList<String>();
                                         ((ArrayList<String>) $$.obj).add($1.sval); }
| /* empty string */                   { /* nothing */ }
;

param : type ID                        { symbols.put($2.sval, $1.sval);
                                         $$.sval = $2.sval; }
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

  public TypeParser(Reader r) {
    lexer = new TypeLexer(r, this);
  }

  public TypeParser(Reader r, Hashtable symbols)
  {
    lexer = new TypeLexer(r, this);
    this.symbols = symbols;
  }
