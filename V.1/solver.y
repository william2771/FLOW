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

valid_program : solver
;

/* Productions unique to the solver - This is of course ridiculous right now, and solver will not go to ID, but this is a starting point */

solver: type_link solver_stmt_list { System.out.println($2.obj); }
;

solver_stmt_list : solver_stmt ';'  { $$.obj = new SequenceNode(null, (StatementNode) $1.obj); }
| solver_stmt_list solver_stmt ';'  { $$.obj = new SequenceNode((SequenceNode) $1.obj, (StatementNode) $2.obj); }

solver_stmt: 
| list_dec
| prim_dec
| while_stmt
| if_stmt
| expr  { $$.obj = $1.obj; }
;

while_stmt : WHILE '(' expr ')' '{' stmt_list '}' { $$.obj = new WhileNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

if_stmt: IF '(' expr ')' '{' stmt_list '}' { $$.obj = new IfNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

expr: '(' expr ')' { $$.obj = $2.obj; }
;

expr: unop expr { $$.obj = new Unary((Expression) $2.obj, $1.sval); }

unop: '-' { $$.sval = "-"; };
;

expr: expr compop expr { $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, $2.sval); }
;

compop: '>' { $$.sval = ">"; }
|   GTE    { $$.sval = ">="; }
|   '<' { $$.sval = "<"; }
|   LTE { $$.sval = "<="; }
|   NEQ    { $$.sval = "!="; }
|   EQ    { $$.sval = "=="; }
;

expr: expr arithop expr { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, $2.sval); }
;

arithop: '+'    { $$.sval = "+"; }
|   '-' { $$.sval = "-"; }
|   '*' { $$.sval = "*"; }
|   '/' { $$.sval = "/"; }
|   '%' { $$.sval = "%"; }
;

//Add in all the statements and expressions from the Graph as well

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
