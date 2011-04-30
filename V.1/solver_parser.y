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
%token RET    /* keyword return */
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
%right CAST
%right OF           /* used for defining List types */
%right NEG          /* negation--unary minus */
      
%%

valid_program : solver
;


solver: type_link solver_stmt_list { $$.sval = "public class Solver {\npublic static void main(String[] args) {\n" + $2.obj.toString() + "}\n}"; 
                                     try {
                                       FileWriter graph_file = new FileWriter(new File("Solver.java"));
                                       graph_file.write($$.sval);
                                       graph_file.flush();
                                     }
                                     catch(IOException e) {
                                       yyerror("Could not create Solver file.");
                                     } }
;

type_link : USE STR ';'    { /* process the typedef file */ 
                             labels = new ArrayList<String>();
                             try {
                               String filepath = symbols.get("filepath") + $2.sval;
                               System.out.println("\nTrying to open " + filepath + "\n");
                               TypeParser tparser = new TypeParser(new FileReader(filepath), new Hashtable());
                               tparser.yyparse();
                              }
                              catch(IOException e) {
                                yyerror("Could not open typedef file.");
                            } }
;

solver_stmt_list : solver_stmt ';'  { $$.obj = new SequenceNode(null, (StatementNode) $1.obj); }
| block_stmt                        { $$.obj = new SequenceNode(null, (StatementNode) $1.obj); }
| solver_stmt_list solver_stmt ';'  { $$.obj = new SequenceNode((SequenceNode) $1.obj, (StatementNode) $2.obj); }
| solver_stmt_list block_stmt       { $$.obj = new SequenceNode((SequenceNode) $1.obj, (StatementNode) $2.obj); }
;

block_stmt: while_stmt
| if_stmt
| func_dec                          { $$.obj = $1.obj; }
;

solver_stmt: list_dec
| prim_dec
| RET expr
| assignment  { $$.obj = $1.obj; }
;

func_dec : param '(' param_list ')' '{' solver_stmt_list '}' { $$.obj = new FunctionNode((Param) $1.obj,  (ParamList) $3.obj, (SequenceNode) $6.obj); }
;

while_stmt : WHILE '(' expr ')' '{' solver_stmt_list '}' { $$.obj = new WhileNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

if_stmt : IF '(' expr ')' '{' solver_stmt_list '}' { $$.obj = new IfNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

expr: '(' expr ')'             { $$.obj = $2.obj; }
| '(' type ')' expr %prec CAST { $$.obj = new Cast($2.sval,(Expression) $4.obj); }
| '-' expr %prec NEG           { $$.obj = new Unary((Expression) $2.obj, $1.sval);
                                 ((Expression) $$.obj).type = ((Expression) $2.obj).type; }
| expr '>' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $$.obj).type = ((Expression) $2.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, ">"); }
| expr GTE expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, ">="); }
| expr '<' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "<"); }
| expr LTE expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "<="); }
| expr NEQ expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "!="); }
| expr EQ expr                 { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Comparison((Expression) $1.obj, (Expression) $3.obj, "=="); }
| expr '+' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "+"); }
| expr '-' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "-"); }
| expr '*' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "*"); }
| expr '/' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "/"); }
| expr '%' expr                { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "%"); }
| id '=' expr                  { if (((Expression) $1.obj).type.type != ((Expression) $3.obj).type.type) {
                                   yyerror("Type error at line" + lexer.getLine() + "!");
                                 }
                                 else {
                                   ((Expression) $1.obj).type = ((Expression) $3.obj).type;
                                 }
                                 $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "="); }
| id '[' expr ']'              { if (((Expression) $1.obj).type.type.substring(0,4) != "list") {
                                   yyerror("Only lists should be indexed.");
                                 }
                                 else if (((Expression) $3.obj).type.type != "int") {
                                   yyerror("Lists can only be indexed by ints.");
                                 }
                                 $$.obj = new ListAccess((ID) $1.obj, (Expression) $3.obj); }
| assignment                   { $$.obj = $1.obj; }
| access                       { $$.obj = $1.obj; }
| id                           { $$.obj = $1.obj; }
| pvalue                       { $$.obj = $1.obj; 
                                 ((Expression) $$.obj).type = new Type("int"); }
;

param_list : param_list ',' param      { $$.obj = new ParamList((ParamList)$1.obj, (Param)$3.obj); }
| param                                { $$.obj = new ParamList(null, (Param)$1.obj); }
| /* empty string */                   { $$.obj = null; }
;


param : type ID                        { $$.obj = new Param((Type) $1.obj, (ID) $2.obj); }
;


assignment : lval '=' expr { $$.obj = new Assignment((Expression) $1.obj, (Expression) $3.obj); }
;

lval : id                  { $$.obj = $1.obj; }
access                     { $$.obj = $1.obj; }
;

access : id '[' expr ']'   { $$.obj = new ListAccess((ID) $1.obj, (Expression) $3.obj); }
;

list_dec : LIST_T OF type ID                { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, null); }
| LIST_T OF type id '=' '[' attr_list ']'   { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, (AttrList) $7.obj); 
                                              ((Expression) $$.obj).type = new Type("list" + $3.obj); }
;

type : ptype                           { $$.obj = $1.obj; }
;

prim_dec : ptype id '=' expr           { $$.obj = new PrimDec((pType) $1.obj, (ID) $2.obj, (Expression) $4.obj); }
;

attr_list : attr                       { $$.obj = new AttrList(null, (Attr) $1.obj); }
| attr_list ',' attr                   { $$.obj = new AttrList((AttrList) $1.obj, (pValue) $3.obj); }
;

attr : pvalue                          { $$.obj = $$.obj; }
;

id : ID                                { $$.obj = new ID($1.sval); }
| UNK                                  { yyerror("Invalid identifier on line " + lexer.getLine());
                                         $$.obj = new ID($1.sval); }
;

ptype : INT_T                          { $$.obj = new pType("int"); }
| FLT_T                                { $$.obj = new pType("double"); }
| STR_T                                { $$.obj = new pType("String"); }
;

pvalue : INT                           { $$.obj = new pValue($1.ival);
                                         ((Expression) $$.obj).type = new pType("int"); }
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
