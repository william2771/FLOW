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
                                         $$.sval = "import flow.structure.*; public class Node {\n  public Node(";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) $4.obj)
					   {
					     if (comma) $$.sval += ", ";
                                           $$.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("node_attributes")).put(s, inter.get(s));
                                           comma = true;
					   }
                                         $$.sval += ") {\n arcsIn = new FlowList<Arc>(); arcsOut = new FlowList<Arc>(); arcs = new FlowList<Arc>(); degree = inDegree = outDegree = 0;";
                                         for (String s : (ArrayList<String>) $4.obj)
					   {
					     $$.sval += "    this." + s + " = " + s + ";\n";
					   }
                                         $$.sval += "  }\n void addInArc(Arc in)\n   {\n      arcsIn.add(in);\n      arcs.add(in);\n      inDegree++;\n      degree++;\n   }\n\n   void addOutArc(Arc out)\n   {\n      arcsOut.add(out);\n      arcs.add(out);\n      outDegree++;\n      degree++;\n   }\n\n   int getdegree() { return degree; }\n   int getdnDegree() { return inDegree; }\n   int getoutDegree() { return outDegree; }\n\n   FlowList<Arc> getarcs() { return arcs; }\n   FlowList<Arc> getarcsIn() { return arcsOut; }\n   FlowList<Arc> getarcsOut() { return arcsIn; }\n\n   int inDegree;\n   int outDegree;\n   int degree;\n\n   FlowList<Arc> arcsIn;\n   FlowList<Arc> arcsOut;\n   FlowList<Arc> arcs;";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "}"; }


|NODE_T ID '(' ')' label_list ';'
                                       { symbols.put("node_type", $2.sval);
                                         $$.sval = "import flow.structure.*; public class Node {\n  public Node(";
                                         boolean comma = false;
                                         $$.sval += ") {\n arcsIn = new FlowList<Arc>(); arcsOut = new FlowList<Arc>(); arcs = new FlowList<Arc>(); degree = inDegree = outDegree = 0;}\n void addInArc(Arc in)\n   {\n      arcsIn.add(in);\n      arcs.add(in);\n      inDegree++;\n      degree++;\n   }\n\n   void addOutArc(Arc out)\n   {\n      arcsOut.add(out);\n      arcs.add(out);\n      outDegree++;\n      degree++;\n   }\n\n   int getdegree() { return degree; }\n   int getdnDegree() { return inDegree; }\n   int getoutDegree() { return outDegree; }\n\n   FlowList<Arc> getarcs() { return arcs; }\n   FlowList<Arc> getarcsIn() { return arcsOut; }\n   FlowList<Arc> getarcsOut() { return arcsIn; }\n\n   int inDegree;\n   int outDegree;\n   int degree;\n\n   FlowList<Arc> arcsIn;\n   FlowList<Arc> arcsOut;\n   FlowList<Arc> arcs;}"; }
;

arc_type_def : ARC_T ID '(' param_list ')' ';'
                                       { symbols.put("arc_type", $2.sval);
                                         $$.sval = "import flow.structure.*; public class Arc {\n  private Node fromNode;\n\t\n\tprivate Node toNode; public Arc(Node source, Node dest, ";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           if (comma) $$.sval += ", ";
                                           $$.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("arc_attributes")).put(s, inter.get(s));
                                           comma = true;
                                         }
                                         $$.sval += ") {\n    fromNode = source; toNode = dest;\n source.addOutArc(this);  dest.addInArc(this); ";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "    this." + s + " = " + s + ";\n";
                                         }
                                         $$.sval += "  }\n";
                                         for (String s : (ArrayList<String>) $4.obj)
                                         {
                                           $$.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         $$.sval += "public Node getto(){\n\t\treturn toNode;\n\t}\n\t\n\tpublic Node getfrom(){\n\t\treturn fromNode;\n\t}\n\n\tpublic boolean setto(Node to){\n\t\ttoNode = to;\n\t\treturn true;\n\t}\n\t\n\tpublic boolean setfrom(Node from){\n\t\tfromNode = from;\n\t\treturn true;\n\t}\n\t\n\tpublic boolean setNodes(Node from, Node to){\n\t\tfromNode = from;\n\t\ttoNode = to;\n\t\treturn true;\n\t}\n\t\n\n\tpublic FlowList<Node> getNodes(Node from, Node to){\n\t\tfromNode = from;\n\t\ttoNode = to;\n\t\tFlowList<Node> twoNodes = new FlowList<Node>();\n\t\ttwoNodes.add(from);\n\t\ttwoNodes.add(to);\n\t\treturn twoNodes;\n\t}} "; }
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
