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
%right NEG          /* negation--unary minus */
%left '.'           /* attribute access for aggregate types */

%%

valid_program : solver
;

solver: type_link solver_stmt_list { $$.sval = "import java.util.*;\n\npublic class Solver {\npublic static void main(String[] args) {\n" + $2.obj.toString() + "}\n}";
                                     if (errors == 0) { //only create output java file if there are no syntax errors
                                       try {
                                         FileWriter graph_file = new FileWriter(new File("Solver.java"));
                                         graph_file.write($$.sval);
                                         graph_file.flush();
                                       }
                                       catch(IOException e) {
                                         yyerror("Could not create Solver file.");
                                       }
                                     }
                                     else {
                                       System.out.println("\n" + errors + " errors\n");
                                     } }
| solver_stmt_list                 { yyerror("The first statement in the file must be a typelink.");
                                     System.out.println("\n" + errors + " errors\n"); }
;

type_link : USE STR ';'    { /* process the typedef file */ 
                             labels = new ArrayList<String>();
                             try {
                               String filepath = symbols.get("filepath") + $2.sval;
                               //System.out.println("\nTrying to open " + filepath + "\n");
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
| assignment
| print_stmt
| func_call                         { $$.obj = $1.obj; }
| RET expr                          { /*This is different - make it somehow*/ }
;

func_call : id '(' attr_list ')'                              { $$.obj = new FunctionCall((ID) $1.obj, (AttrList) $3.obj);
                                                                ((Expression) $$.obj).type = ((Expression) $1.obj).type; }
;

func_dec : param '(' param_list ')' '{' solver_stmt_list '}'  { $$.obj = new FunctionNode((Param) $1.obj, (ParamList) $3.obj, (SequenceNode) $6.obj); }
;

while_stmt : WHILE '(' expr ')' '{' solver_stmt_list '}'      { $$.obj = new WhileNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

if_stmt : IF '(' expr ')' '{' solver_stmt_list '}'            { $$.obj = new IfNode((Expression) $3.obj, (SequenceNode) $6.obj ); }
;

expr : '(' expr ')'            { $$.obj = $2.obj; }
| '(' type ')' expr %prec CAST { $$.obj = new Cast($2.sval,(Expression) $4.obj);
                                 ((Expression) $$.obj).type = (pType) $2.obj; }
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
| id '.' id                    { $$.obj = new Dot((ID) $1.obj, (ID) $3.obj);
                                 if (((Expression) $1.obj).type.type.equals("List")) {
                                   /* Some kind of magic needed here */
                                   ((Expression) $$.obj).type = ((Expression) $3.obj).type;
                                 }
                                 else if (((Expression) $1.obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) $3.obj).toString()))
                                     ((Expression) $$.obj).type = ((Type) ((Hashtable) symbols.get("node_attributes")).get(((ID) $3.obj).toString()));
                                   else {
                                     yyerror("Node attribute '" + ((Expression) $3.obj).type.type + "' is not defined");
                                     ((Expression) $$.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) $1.obj).type.type.equals("Arc")) {
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) $3.obj).toString()))
                                     ((Expression) $$.obj).type = ((Type) ((Hashtable) symbols.get("arc_attributes")).get(((ID) $3.obj).toString()));
                                   else {
                                     yyerror("Arc attribute '" + ((Expression) $3.obj).type.type + "' is not defined");
                                     ((Expression) $$.obj).type = new pType("error");
                                   }
                                 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((Expression) $1.obj).type.type);
                                   ((Expression) $$.obj).type = new pType("error");
                                 } }
| GRAPH '.' id                 { $$.obj = new Dot(new ID("Graph"), (ID) $3.obj);
                                 if (symbols.containsKey(((ID) $3.obj).toString())) {
                                   ((Expression) $$.obj).type = ((ID) symbols.get(((ID) $3.obj).toString())).type;
                                 }
                                 else {
                                   yyerror("Graph attribute '" + ((ID) $3.obj).toString() + "' is not defined");
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

param_list : param_list ',' param      { $$.obj = new ParamList((ParamList)$1.obj, (Param)$3.obj); }
| param                                { $$.obj = new ParamList(null, (Param)$1.obj); }
| /* empty string */                   { $$.obj = null; }
;

param : type ID                        { $$.obj = new Param((Type) $1.obj, (ID) $2.obj); }
;

assignment : access '=' expr           { $$.obj = ((ListAccess) $1.obj).makeLVal((Expression) $3.obj);
                                         ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
| id '=' expr                          { $$.obj = new Arithmetic((Expression) $1.obj, (Expression) $3.obj, "=");
                                         ((Expression) $$.obj).type = check_type((Expression) $1.obj, (Expression) $3.obj); }
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

list_dec : LIST_T OF type id                { $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, null);
                                                //added space, was new Type("list" ...) -> new Type("list " ...)
                                              ((ID) $4.obj).type = new Type("list " + $3.obj);
                                              symbols.put(((ID) $4.obj).toString(), $4.obj); }
| LIST_T OF type id '=' '[' attr_list ']'   { //Make a for loop across attr_list and check for type
                                                $$.obj = new ListDec((Type) $3.obj, (ID) $4.obj, (AttrList) $7.obj);
                                              ((ID) $4.obj).type = new Type("list " + $3.obj);
                                              symbols.put(((ID) $4.obj).toString(), $4.obj); 
                                              }
;

type : ptype                           { $$.obj = $1.obj; }
| NODE_T                               { $$.obj = new Type("Node"); }
| ARC_T                                { $$.obj = new Type("Arc"); }
;


prim_dec : type id '=' expr           { check_type((Type) $1.obj, (Expression) $4.obj);
                                        $$.obj = new PrimDec((Type) $1.obj, (ID) $2.obj, (Expression) $4.obj);
                                        ((Expression) $2.obj).type = (Type) $1.obj;
                                        symbols.put(((ID) $2.obj).toString(), $2.obj); }
;

attr_list : attr                       { $$.obj = new AttrList(null, (Attr) $1.obj); }
| attr_list ',' attr                   { $$.obj = new AttrList((AttrList) $1.obj, (Attr) $3.obj); }
;

//attr no longer goes to pvalue
attr : expr                            { $$.obj = $1.obj; }
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
;

pvalue : INT                           { $$.obj = new pValue($1.ival);
                                         ((Expression) $$.obj).type = new pType("int"); }
| FLT                                  { $$.obj = new pValue($1.dval);
                                         ((Expression) $$.obj).type = new pType("double"); }
| STR                                  { $$.obj = new pValue("\"" + $1.sval + "\"");
                                         ((Expression) $$.obj).type = new pType("String"); }
;

print_stmt : PRINT expr                { $$.obj = new Print((Expression) $2.obj); }
;

%%

  private SolverLexer lexer;
  private Hashtable symbols;
  private ArrayList<String> labels;
  private int errors; //Keeps track of syntax errors

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new SolverParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :" + e);
    }

    //Print the token value - used for debugging
    //System.out.println(yyl_return);

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
    ArrayList<Attr> attrs = e2.toArrayList();
    Type ret;
    for(Attr attr : attrs) {
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

  public SolverParser(Reader r, Hashtable symbols)
  {
    lexer = new SolverLexer(r, this);
    this.symbols = symbols;
    errors = 0; //no errors yet
  }
