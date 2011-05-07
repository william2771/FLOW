%{
  import java.io.*;
  import java.util.*;
  import flow.ast.*;
  import flow.structure.*;
%}

/* keywords */
%token INT_T  /* keyword int, the type */
%token FLT_T  /* keyword float, the type */
%token STR_T  /* keyword string, the type */
%token NODE_T /* keyword Node, the type */
%token ARC_T  /* keyword Arc, the type */
%token GRAPH  /* keyword Graph */
%token LIST_T /* keyword List, the type */
%token WHILE  /* keyword while */
%token IF     /* keyword if */
%token OF     /* keyword of */
%token USE    /* keyword use */
%token PRINT  /* keyword print */

%token STR    /* string literal */
%token FLT    /* floating-point number literal */
%token INT    /* integer literal */
%token LIST   /* list literal */
%token ID     /* identifier */

/* multicharacter operators */
%token ARC    /* arc connection operator -> */
%token EQ     /* equality operator == */
%token NEQ    /* inequality operator != */
%token LTE    /* less than or equal to <= */
%token GTE    /* greater than or equal to >= */

/* error tokens */
%token UNK /* an illegal identifier */

/* operator precedence */
%nonassoc EQ NEQ
%nonassoc '<' '>' LTE GTE
%right '='
%left '-' '+'
%left '%'
%left '*' '/'
%right OF           /* used for defining List types */
%right CAST
%right NEG          /* negation--unary minus */
      
%%

valid_program : graph_decl
;

/* Beginning of graph declaration section */

graph_decl : type_link graph_stmt_list { $$.sval = "import flow.structure.*;\nimport java.util.ArrayList;\npublic class Graph {\npublic Graph() {\nnodes = new FlowList<Node>();\narcs = new FlowList<Arc>();\n" + $2.obj.toString() + "\n}\nprivate FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }\n private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } \n public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}\n";

                                         for (String label : labels)
                                         {
                                           $$.sval += "private Node " + label + ";\npublic Node get" + label + "() {\n  return " + label + ";\n}\n";
                                         }

                                         $$.sval += "\n}\n";

                                         try
                                         {
                                           FileWriter graph_file = new FileWriter(new File("Graph.java"));
                                           graph_file.write($$.sval);
                                           graph_file.flush();
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not create Graph file.");
                                         } }
;

type_link : USE STR ';'                { /* process the typedef file */ 
                                         labels = new ArrayList<String>();
                                         try
                                         {
                                           String filepath = symbols.get("filepath") + $2.sval;
                                           TypeParser tparser = new TypeParser(new FileReader(filepath), new Hashtable());
                                           tparser.yyparse();
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not open typdef file.");
                                         } }
;

graph_stmt_list : graph_stmt ';'       { $$.obj = new SequenceNode(null, (StatementNode) $1.obj); }
| graph_stmt_list graph_stmt ';'       { $$.obj = new SequenceNode((SequenceNode) $1.obj, (StatementNode) $2.obj); }
;

graph_stmt : label_app
| node_dec
| arc_dec
| list_dec
| prim_dec
| expr                                 { $$.obj = $1.obj; }
;

label_app : id ':' node_dec            { $$.obj = new LabelNode((ID) $1.obj, (NodeDec) $3.obj);
                                         labels.add($1.sval); }
;

node_dec : '@' id attr_list            { $$.obj = new NodeDec((ID) $2.obj, (AttrList) $3.obj); symbols.put(((ID)$2.obj).toString(), (ID)$2.obj);}
| '@' id                               { $$.obj = new NodeDec((ID) $2.obj, null); symbols.put(((ID)$2.obj).toString(), (ID)$2.obj);}
;

arc_dec : id ARC id attr_list          { $$.obj = new ArcDec((ID) $1.obj, (ID) $3.obj, (AttrList) $4.obj);}
| id ARC id                            { $$.obj = new ArcDec((ID) $1.obj, (ID) $3.obj, null); }
;

list_dec : LIST_T OF type ID           { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, null); symbols.put(((ID)$4.obj).toString(), (ID)$4.obj);}
| LIST_T OF type id '=' '[' attr_list ']' { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, (AttrList) $7.obj); symbols.put(((ID)$4.obj).toString(), (ID)$4.obj);}
;

type : ptype                           { $$.obj = $1.obj; }
;

prim_dec : type id                     { $$.obj = new PrimDec((Type) $1.obj, (ID) $2.obj, null);
                                         if (symbols.containsKey($2.obj.toString())) {
                                           yyerror("Variable " + $2.obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) $2.obj).type = (Type) $1.obj;
                                           symbols.put(((ID) $2.obj).toString(), $2.obj);
                                         } }
| type id '=' expr                     { $$.obj = new PrimDec((Type) $1.obj, (ID) $2.obj, (Expression) $4.obj);
                                         if (symbols.containsKey($2.obj.toString())) {
                                           yyerror("Variable " + $2.obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) $2.obj).type = check_type((Type) $1.obj, (Expression) $4.obj);
                                           symbols.put(((ID) $2.obj).toString(), $2.obj);
                                         } }
;

attr_list : expr                       { $$.obj = new AttrList(null, (Expression) $1.obj); }
| attr_list ',' expr                   { $$.obj = new AttrList((AttrList) $1.obj, (pValue) $3.obj); }
;

expr : '(' expr ')'            { $$.obj = $2.obj; }
| '(' type ')' expr %prec CAST { $$.obj = new Cast($2.sval,(Expression) $4.obj);
                                 ((Expression) $$.obj).type = (Type) $2.obj; }
| '-' expr %prec NEG           { $$.obj = new Unary((Expression) $2.obj, $1.sval);
                                 if (((Expression) $1.obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = ((Expression) $2.obj).type; }
| expr '>' expr                { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, ">");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr GTE expr                { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, ">=");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '<' expr                { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "<");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr LTE expr                { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "<=");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr NEQ expr                { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "!=");
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr EQ expr                 { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "==");
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '+' expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "+");
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '-' expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "-");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '*' expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "*");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '/' expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "/");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| expr '%' expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "%");
                                 if (((Expression) $1.obj).type.type.equals("String") || ((Expression) $3.obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| id '.' id                    { $$.obj = new Dot((ID) $1.obj, $3.obj.toString());
                                 if (((Expression) $1.obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) $3.obj).toString()))
                                     ((Expression) $$.obj).type = new Type(((Hashtable) symbols.get("node_attributes")).get($3.obj.toString()).toString());
                                   else {
                                     yyerror("Node attribute '" + $3.obj.toString() + "' is not defined");
                                     ((Expression) $$.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) $1.obj).type.type.equals("Arc")) {
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) $3.obj).toString()))
                                     ((Expression) $$.obj).type = new Type(((Hashtable) symbols.get("arc_attributes")).get($3.obj.toString()).toString());
                                   else {
                                     yyerror("Arc attribute '" + $3.obj.toString() + "' is not defined");
                                     ((Expression) $$.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) $1.obj).type.type.length() > 4 && ((Expression) $1.obj).type.type.substring(0,4).equals("list")) {
                                   if ($3.obj.toString().equals("length")) {
                                     ((Expression) $$.obj).type = new pType("int");
                                   }
                                   else {
                                     yyerror("List attribute '" + $3.obj.toString() + "' is not defined");
                                     ((Expression) $$.obj).type = new pType("error");
                                   }
                                 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((ID) $1.obj).toString() + " is of type " + ((Expression) $1.obj).type.type);
                                   ((Expression) $$.obj).type = new pType("error");
                                 } }
| GRAPH '.' id                 { $$.obj = new Dot(new ID("graph"), $3.obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey($3.obj.toString())) {
                                   ((Expression) $$.obj).type = new Type(((Hashtable) symbols.get("labels")).get($3.obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + $3.obj.toString() + "' is not defined");
                                   ((Expression) $$.obj).type = new pType("error");
                                 } }
| assignment                   { $$.obj = $1.obj; }
| access                       { $$.obj = $1.obj; }
| id                           { $$.obj = $1.obj;
                                 if (!symbols.containsKey(((ID) $1.obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) $1.obj).toString() + "'");
                                   ((Expression) $$.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) $$.obj).type = ((ID) $1.obj).type;
                                 } }
| func_call                    { $$.obj = $1.obj; }
| pvalue                       { $$.obj = $1.obj; }
;


access : id '[' expr ']'               { $$.obj = new ListAccess((ID) $1.obj, (Expression) $3.obj);
                                         if (!symbols.containsKey(((ID) $1.obj).toString())) {
                                           yyerror("Undeclared list '" + ((ID) $1.obj).toString() + "'");
                                           ((Expression) $$.obj).type = new pType("error");
                                         }
                                         else if (!((Expression) $1.obj).type.type.substring(0,4).equals("list")) {
                                           yyerror("Only Lists can be indexed. " + ((Expression) $1.obj).type.type.substring(0,4));
                                           ((Expression) $$.obj).type = new pType("error");
                                         }
                                         else if (((Expression) $3.obj).type.type != "int") {
                                           yyerror("Lists can only be indexed by ints.");
                                           ((Expression) $$.obj).type = new pType("error");
                                         }
                                         else {
                                           ((Expression) $$.obj).type = new Type(((ID) $1.obj).type.type.substring(5));
                                         } }
;

assignment : access '=' expr           { $$.obj = ((ListAccess) $1.obj).makeLVal((Expression) $3.obj);
                                         ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| id '=' expr                          { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "=");
                                         ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
;

id : ID                                { if (symbols.containsKey($1.sval)) {
                                           $$.obj = symbols.get($1.sval);
                                         }
                                         else {
                                           $$.obj = new ID($1.sval);
                                         } }
;

ptype : INT_T                          { $$.obj = new pType("int"); }
| FLT_T                                { $$.obj = new pType("double"); }
| STR_T                                { $$.obj = new pType("String"); }
| NODE_T                                { $$.obj = new pType("Node"); }
| ARC_T                                { $$.obj = new pType("Arc"); }
;

pvalue : INT                           { $$.obj = new pValue($1.ival);
                                         ((Expression) $$.obj).type = new pType("int"); }
| FLT                                  { $$.obj = new pValue($1.dval);
                                         ((Expression) $$.obj).type = new pType("double"); }
| STR                                  { $$.obj = new pValue("\"" + $1.sval + "\"");
                                         ((Expression) $$.obj).type = new pType("String"); }

;

%%

  private GraphLexer lexer;
  private Hashtable symbols;
  private ArrayList<String> labels;
  private int errors; //Keeps track of syntax errors

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new GraphParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }

  private Type check_type(Expression e1, Expression e2) {
    if (!e1.type.type.equals(e2.type.type)) {
      yyerror("Type mismatch error:  " + e1.type.type + " != " + e2.type.type);
      return new pType("error");
    }
    else return e1.type;
  }
  
  private Type check_type(Type t1, Expression e2) {
    if (!t1.type.equals(e2.type.type)) {
      yyerror("Type mismatch error:  " + t1.type + " != " + e2.type.type);
      return new pType("error");
    }
    else return t1;
  }  
  
  private Type check_type(Type t1, AttrList e2) {
    ArrayList<Expression> attrs = e2.toArrayList();
    Type ret;
    for(Expression attr : attrs) {
        //check_type(type t1, expression e2) will put an error into yyerror
        ret = check_type(t1, attr);
        if(ret.type == "error") {
            return ret;
        }
    }
    return t1;
  }  
  
  public void yyerror (String error) {
    System.err.println("Error: " + error + "\n\tat line " + (lexer.getLine() + 1));
    errors++;
  }

  public GraphParser(Reader r) {
    lexer = new GraphLexer(r, this);
  }

  public GraphParser(Reader r, Hashtable symbols)
  {
    errors = 0;
    lexer = new GraphLexer(r, this);
    this.symbols = symbols;
  }
