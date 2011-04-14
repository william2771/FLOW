%{
  import java.io.*;
  import java.util.*;
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
%token ID     /* identifier */

/* multicharacter operators */
%token ARC    /* arc connection operator -> */
%token EQ     /* equality operator == */
%token NEQ    /* inequality operator != */
%token LTE    /* less than or equal to <= */
%token GTE    /* greater than or equal to >= */

/* operator precedence */
%nonassoc EQ NEQ
%nonassoc '<' '>' LTE GTE
%left '-' '+'
%left '%'
%left '*' '/'
%right OF           /* used for defining List types */
%right NEG          /* negation--unary minus */
      
%%

valid_program : type_def
| graph_decl
;

/* Beginning of typedef section */

type_def : node_type_def arc_type_def  { System.out.println($2.sval); }
;

node_type_def : NODE_T ID '(' param_list ')' label_list ';'
                                       { symbols.put("node_type", $2.sval); }
;

arc_type_def : ARC_T ID '(' param_list ')' ';'
                                       { symbols.put("arc_type", $2.sval);
                                         $$.sval = "public class " + $2.sval + " extends Arc {\n  public " + $2.sval + "(" + symbols.get("node_type") + " source, " + symbols.get("node_type") + " dest, " + $4.sval + ") {\n    this(source, dest);\n  }\n}"; }
;

param_list : param_list ',' param      { $$.sval = $1.sval + ", " + $3.sval; }
| param                                { $$.sval = $1.sval; }
| /* empty string */                   { /* nothing */ }
;

param : type ID                        { $$.sval = $1.sval + " " + $2.sval; }
;

type : INT_T                           { $$.sval = "int"; }
| FLT_T                                { $$.sval = "double"; }
| STR_T                                { $$.sval = "String"; }
;

label_list : label_list ',' ID         { $$.sval = $1.sval + ", " + $3.sval;
                                         $$.obj = $1.obj;
                                         ((ArrayList<String>) $$.obj).add($3.sval); }
| ID                                   { $$.sval = $1.sval;
                                         $$.obj = new ArrayList<String>();
                                         ((ArrayList<String>) $$.obj).add($1.sval); }
| /* empty string */                   { /* nothing */ }
;

/* Beginning of graph declaration section */

graph_decl : type_link graph_stmt_list { System.out.println("Syntax is correct for the graph declaration."); }
;

type_link : USE STR ';'                { System.out.println($2.sval); }
;

graph_stmt_list : graph_stmt ';'       {}
| graph_stmt_list graph_stmt ';'       {}
;

graph_stmt : label_app                 {}
| node_dec                             {}
| arc_dec                              {}
;

label_app : ID ':' node_dec            {System.out.println("label on line " + lexer.getLine());}
;

node_dec : '@' ID attr_list            {}
| '@' ID                               {}
;

arc_dec : ID ARC ID attr_list          {}
| ID ARC ID                            {}
;

attr_list : attr                       {}
| attr_list ',' attr                   {}
;

attr : INT                             {System.out.println(yylval.ival + " on line " + lexer.getLine());}
;

%%

  private Yylex lexer;
  private Hashtable symbols;

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
