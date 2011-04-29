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
|func-dec : param '(' param-list ')' '{' stmt-list '}'
	{ $$.obj = new FunctionNode( (Type) $1.obj,  (ParamList)$3.obj, (SequenceNode) $6.obj ); }
|RET : 'return' expr

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

/*add to graph/solver stmt*/


param_list : param_list ',' param      { $$.obj = new ParamList((ParamList)$1.obj, (Param)$3.obj); }
| param                                { $$.obj = new ParamList(null, (Param)$1.obj); }
| /* empty string */                   { $$.obj = null; }
;


param : type ID                        { $$.obj = new Param((Type) $1.obj, (ID) $obj); }
;

%%

  private SolverLexer lexer;
  private Hashtable symbols;
  private ArrayList<String> labels;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new SolverParserVal(0);
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

  public SolverParser(Reader r) {
    lexer = new SolverLexer(r, this);
  }

  public SolverParser(Reader r, Hashtable symbols)
  {
    lexer = new SolverLexer(r, this);
    this.symbols = symbols;
  }
