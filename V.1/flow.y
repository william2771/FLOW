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
%left '-' '+'
%left '%'
%left '*' '/'
%right OF           /* used for defining List types */
%right NEG          /* negation--unary minus */
      
%%

valid_program : graph_decl
| solver
;

/* Beginning of graph declaration section */

graph_decl : type_link graph_stmt_list { System.out.println($2.obj);
                                         for (String label : labels)
                                         {
                                           System.out.println("private Node " + label + ";\npublic Node " + label + "() {\n  return " + label + ";\n}\n");
                                         } }
;

type_link : USE STR ';'                { /* process the typedef file */ 
                                         labels = new ArrayList<String>();
                                         try
                                         {
                                           String filepath = "../test_files/" + $2.sval;
                                           System.out.println("\nTryin to open " + filepath + "\n");
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

node_dec : '@' id attr_list            { $$.obj = new NodeDec((ID) $2.obj, (AttrList) $3.obj); }
| '@' id                               { $$.obj = new NodeDec((ID) $2.obj, null); }
;

arc_dec : id ARC id attr_list          { $$.obj = new ArcDec((ID) $1.obj, (ID) $3.obj, (AttrList) $4.obj); }
| id ARC id                            { $$.obj = new ArcDec((ID) $1.obj, (ID) $3.obj, null); }
;

list_dec : LIST_T OF type ID           { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, null); }
| LIST_T OF type id '=' '[' attr_list ']' { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, (AttrList) $7.obj); }
;

type : ptype                           { $$.obj = $1.obj; }
;

prim_dec : ptype id '=' pvalue         { $$.obj = new PrimDec((pType) $1.obj, (ID) $2.obj, (pValue) $4.obj); }
;

attr_list : attr                       { $$.obj = new AttrList(null, (Attr) $1.obj); }
| attr_list ',' attr                   { $$.obj = new AttrList((AttrList) $1.obj, (pValue) $3.obj); }
;

attr : pvalue                          { $$.obj = $$.obj; }
;

expr : id assignop expr                { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, (String) $2.sval); }
| id '[' expr ']'                      { $$.obj = new ListAccess((ID) $1.obj, (Expression) $3.obj); }
;

assignop : '='                         { $$.sval = "="; }
;

id : ID                                { $$.obj = new ID($1.sval); }
| UNK                                  { yyerror("Invalid identifier on line " + lexer.getLine());
                                         $$.obj = new ID($1.sval); }
;

ptype : INT_T                          { $$.obj = new pType("int"); }
| FLT_T                                { $$.obj = new pType("double"); }
| STR_T                                { $$.obj = new pType("String"); }
;

pvalue : INT                           { $$.obj = new pValue($1.ival); }
;

/* Productions unique to the solver - This is of course ridiculous right now, and solver will not go to ID, but this is a starting point */

solver: ID
;

%%

  private Yylex lexer;
  private Hashtable symbols;
  private ArrayList<String> labels;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
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

  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  public Parser(Reader r, Hashtable symbols)
  {
    lexer = new Yylex(r, this);
    this.symbols = symbols;
  }

  static boolean interactive;

  public static void main(String args[]) throws IOException {

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
      yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();

    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }
