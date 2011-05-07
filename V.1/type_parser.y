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
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("node_attributes")).put(s, inter.get(s));
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
                                           $$.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "}"; }
;

arc_type_def : ARC_T ID '(' param_list ')' ';'
                                       { symbols.put("arc_type", $2.sval);
                                         $$.sval = "public class Arc extends flow.structure.SuperArc {\n  public Arc(Node source, Node dest, ";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("arc_attributes")).put(s, inter.get(s));
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
                                           $$.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "}"; }
;

param_list : param_list ',' param      { $$.obj = $1.obj;
                                         ((ArrayList<String>) $$.obj).add($3.sval); }
| param                                { $$.obj = new ArrayList<String>();
                                         ((ArrayList<String>) $$.obj).add($1.sval); }
| /* empty string */                   { /* nothing */ }
;

param : type ID                        { inter.put($2.sval, $1.sval);
                                         $$.sval = $2.sval; }
;

type : INT_T                           { $$.sval = "int"; }
| FLT_T                                { $$.sval = "double"; }
| STR_T                                { $$.sval = "String"; }
;

label_list : label_list ',' ID         { $$.obj = $1.obj;
                                         ((ArrayList<String>) $$.obj).add($3.sval); }
| ID                                   { $$.obj = new ArrayList<String>();
                                         ((ArrayList<String>) $$.obj).add($1.sval);
                                         ((Hashtable) symbols.get("labels")).put($1.sval, "Node"); }
| /* empty string */                   { /* nothing */ }
;

%%

  private TypeLexer lexer;
  private Hashtable symbols;
  private Hashtable inter;

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

    //A new hashtable to hold node attributes
    Hashtable node_attributes = new Hashtable();
    //These attributes are automatic
    node_attributes.put("degree", "int");
    node_attributes.put("inDegree", "int");
    node_attributes.put("outDegree", "int");
    node_attributes.put("arcs", "list Arc");
    node_attributes.put("arcsIn", "list Arc");
    node_attributes.put("arcsOut", "list Arc");
    symbols.put("node_attributes", node_attributes);

    //A new hashtable to hold arc attributes
    Hashtable arc_attributes = new Hashtable();
    //These attributes are automatic
    arc_attributes.put("nodes", "list Node");
    arc_attributes.put("from", "Node");
    arc_attributes.put("to", "Node");
    symbols.put("arc_attributes", arc_attributes);

    //A new hashtable to store all the graph attributes and labels
    Hashtable labels = new Hashtable();
    //These attributes are automatic
    labels.put("arcs", "list Arc");
    labels.put("nodes", "list Node");
    labels.put("numArcs", "int");
    labels.put("numNodes", "int");
    symbols.put("labels", labels);

    inter = new Hashtable();
  }
