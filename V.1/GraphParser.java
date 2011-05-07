//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 1 "graph_parser.y"

  import java.io.*;
  import java.util.*;
  import flow.ast.*;
  import flow.structure.*;
//#line 23 "GraphParser.java"




public class GraphParser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class GraphParserVal is defined in GraphParserVal.java


String   yytext;//user variable to return contextual strings
GraphParserVal yyval; //used to return semantic vals from action routines
GraphParserVal yylval;//the 'lval' (result) I got from yylex()
GraphParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new GraphParserVal[YYSTACKSIZE];
  yyval=new GraphParserVal();
  yylval=new GraphParserVal();
  valptr=-1;
}
void val_push(GraphParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
GraphParserVal val_pop()
{
  if (valptr<0)
    return new GraphParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
GraphParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new GraphParserVal();
  return valstk[ptr];
}
final GraphParserVal dup_yyval(GraphParserVal val)
{
  GraphParserVal dup = new GraphParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short INT_T=257;
public final static short FLT_T=258;
public final static short STR_T=259;
public final static short NODE_T=260;
public final static short ARC_T=261;
public final static short GRAPH=262;
public final static short LIST_T=263;
public final static short WHILE=264;
public final static short IF=265;
public final static short OF=266;
public final static short USE=267;
public final static short PRINT=268;
public final static short STR=269;
public final static short FLT=270;
public final static short INT=271;
public final static short LIST=272;
public final static short ID=273;
public final static short ARC=274;
public final static short EQ=275;
public final static short NEQ=276;
public final static short LTE=277;
public final static short GTE=278;
public final static short UNK=279;
public final static short CAST=280;
public final static short NEG=281;
public final static short func_call=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    4,    4,    4,    4,    4,
    4,    5,    6,    6,    7,    7,    8,    8,   13,    9,
    9,   12,   12,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   16,   15,   15,   11,   14,   14,
   14,   14,   14,   17,   17,   17,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    2,    4,    3,    4,    8,    1,    2,
    4,    1,    3,    3,    4,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    4,    3,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,   49,   50,   51,   52,   53,
    0,    0,   56,   55,   54,   48,    0,    0,    0,   43,
    0,    0,    6,    7,    8,    9,   10,    0,    0,    0,
   19,   40,    0,   44,    3,    0,    0,   26,    0,    0,
    0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,   24,    0,    5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   35,   36,
    0,    0,   12,    0,   38,    0,    0,    0,    0,   25,
    0,   45,    0,    0,    0,    0,    0,   18,
};
final static short yydgoto[] = {                          2,
    3,    4,   21,   22,   23,   24,   25,   26,   27,   65,
   39,   66,   30,   31,   32,   33,   34,
};
final static short yysindex[] = {                      -265,
 -262,    0,    0,  -40,  -49,    0,    0,    0,    0,    0,
  -34, -252,    0,    0,    0,    0,  -17, -258,  -23,    0,
  -40,  -39,    0,    0,    0,    0,    0,   71,  114, -258,
    0,    0,  -31,    0,    0, -258,   14,    0,  -21,  -17,
   36,  -14,  -28,    0,  -17,  -17,  -17,  -17,  -17,  -17,
  -17,  -17,  -17,  -17,  -17, -258,  -17,  -32,  -17, -258,
    5,  -17,    0, -258,   71,    3,    0,  -17,    0,   80,
   80,  151,  151,  151,  151,  -29,  -29,  -36,    0,    0,
  -17,  151,    0,   22,    0,  -17,  151,    7,  -17,    0,
    3,    0,   71,  -15,   71,  -17,  -35,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   86,    0,    0,    0,    0,    0,    0,   21,   50,    0,
    0,    0,   -8,    0,    0,    0,    0,    0,    1,   29,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   30,    0,    0,    0,  -43,   31,    0,    0,    0,  133,
  168,   62,  108,  122,  132,   85,   91,   12,    0,    0,
   32,   97,    0,    0,    0,    0,  102,   40,    0,    0,
   41,    0,   42,    0,  -18,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   81,    0,    4,    0,    0,    0,  372,
  150,  -77,  -16,    0,    0,    0,    0,
};
final static int YYTABLESIZE=461;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         19,
   22,    1,   42,   91,   17,   54,    5,   53,   89,   35,
   55,   36,   54,   37,   16,   22,   19,   55,   97,   44,
   64,   17,   19,   18,   60,   23,   68,   17,   41,   62,
   69,   18,   41,   41,   41,   41,   41,   42,   41,   57,
   23,   42,   42,   42,   42,   42,   89,   42,   37,   22,
   41,   41,   37,   41,   37,   37,   37,   98,   53,   42,
   42,   83,   42,   54,   52,   86,   51,   94,   55,   59,
   37,   37,   53,   37,   23,   96,   67,   54,   52,   11,
   51,   49,   55,   50,   41,    2,   42,   14,   20,   13,
   16,   42,   42,   42,   42,   49,   42,   50,   17,   15,
   21,   43,   30,    0,   37,   30,    0,   53,   42,   42,
    0,   42,   54,   52,   92,   51,   53,   55,    0,    0,
   30,   54,   52,    0,   51,   34,   55,   34,   34,   34,
   49,   33,   50,   33,   33,   33,    0,   47,    0,   49,
   47,   50,   46,   34,   34,   46,   34,    0,   28,   33,
   33,   28,   33,   29,   30,   47,   47,    0,   47,   60,
   46,   46,   29,   46,    0,   29,   28,   40,    0,    0,
   29,   58,   27,   32,   57,   27,   32,   34,    0,   61,
   29,    0,    0,   33,    0,   63,    0,   53,    0,   47,
   27,   32,   54,   52,   46,   51,    0,   55,    0,    0,
   28,    0,    0,    0,   59,   81,    0,    0,   31,   85,
    0,   31,    0,   88,   29,    0,    6,    7,    8,    9,
   10,   11,   12,    0,   27,   32,   31,    0,   13,   14,
   15,    0,   16,    6,    7,    8,    9,   10,   11,    0,
    0,   20,    0,    0,   11,   13,   14,   15,    0,   16,
    0,   13,   14,   15,    0,   16,    0,    0,   20,    0,
   31,    0,    0,    0,   20,    0,   41,   41,   41,   41,
    6,    7,    8,    9,   10,   42,   42,   42,   42,    0,
    0,    0,    0,    0,    0,    0,   37,   37,   37,   37,
    0,    0,    0,    0,    0,    0,   45,   46,   47,   48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   45,   46,   47,   48,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,   42,   42,   42,    0,    0,
    0,    0,    0,    0,    0,    0,   30,   30,    0,    0,
    0,    0,    0,    0,    0,   45,   46,   47,   48,    0,
    0,    0,    0,    0,    0,    0,   47,   48,    0,   34,
   34,   34,   34,    0,    0,   33,   33,   33,   33,    0,
    0,   47,   47,   47,   47,   28,   46,   46,   46,   46,
    0,    0,   28,   28,    0,    0,    0,   56,   38,    0,
   41,    0,   28,    0,    0,    0,   29,   29,    0,    0,
    0,    0,    0,    0,    0,    0,   27,   27,    0,    0,
    0,    0,    0,    0,    0,    0,   70,   71,   72,   73,
   74,   75,   76,   77,   78,   79,   80,    0,   82,    0,
   84,    0,    0,   87,    0,    0,    0,    0,    0,   90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   93,    0,    0,
   95,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   44,  267,   19,   81,   45,   42,  269,   37,   44,   59,
   47,   46,   42,  266,  273,   59,   40,   47,   96,   59,
   37,   45,   40,   64,   46,   44,   41,   45,   37,   61,
   59,   64,   41,   42,   43,   44,   45,   37,   47,   61,
   59,   41,   42,   43,   44,   45,   44,   47,   37,   93,
   59,   60,   41,   62,   43,   44,   45,   93,   37,   59,
   60,   58,   62,   42,   43,   61,   45,   61,   47,   91,
   59,   60,   37,   62,   93,   91,   41,   42,   43,   59,
   45,   60,   47,   62,   93,    0,   37,   59,   59,   59,
   59,   42,   43,   93,   45,   60,   47,   62,   59,   59,
   59,   21,   41,   -1,   93,   44,   -1,   37,   59,   60,
   -1,   62,   42,   43,   93,   45,   37,   47,   -1,   -1,
   59,   42,   43,   -1,   45,   41,   47,   43,   44,   45,
   60,   41,   62,   43,   44,   45,   -1,   41,   -1,   60,
   44,   62,   41,   59,   60,   44,   62,   -1,   41,   59,
   60,   44,   62,    4,   93,   59,   60,   -1,   62,   46,
   59,   60,   41,   62,   -1,   44,   59,   18,   -1,   -1,
   21,   58,   41,   41,   61,   44,   44,   93,   -1,   30,
   59,   -1,   -1,   93,   -1,   36,   -1,   37,   -1,   93,
   59,   59,   42,   43,   93,   45,   -1,   47,   -1,   -1,
   93,   -1,   -1,   -1,   91,   56,   -1,   -1,   41,   60,
   -1,   44,   -1,   64,   93,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,   93,   93,   59,   -1,  269,  270,
  271,   -1,  273,  257,  258,  259,  260,  261,  262,   -1,
   -1,  282,   -1,   -1,  262,  269,  270,  271,   -1,  273,
   -1,  269,  270,  271,   -1,  273,   -1,   -1,  282,   -1,
   93,   -1,   -1,   -1,  282,   -1,  275,  276,  277,  278,
  257,  258,  259,  260,  261,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  275,  276,  277,  278,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,  275,
  276,  277,  278,   -1,   -1,  275,  276,  277,  278,   -1,
   -1,  275,  276,  277,  278,    4,  275,  276,  277,  278,
   -1,   -1,  275,  276,   -1,   -1,   -1,  274,   17,   -1,
   19,   -1,   21,   -1,   -1,   -1,  275,  276,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   45,   46,   47,   48,
   49,   50,   51,   52,   53,   54,   55,   -1,   57,   -1,
   59,   -1,   -1,   62,   -1,   -1,   -1,   -1,   -1,   68,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   86,   -1,   -1,
   89,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"INT_T","FLT_T","STR_T","NODE_T",
"ARC_T","GRAPH","LIST_T","WHILE","IF","OF","USE","PRINT","STR","FLT","INT",
"LIST","ID","ARC","EQ","NEQ","LTE","GTE","UNK","CAST","NEG","func_call",
};
final static String yyrule[] = {
"$accept : valid_program",
"valid_program : graph_decl",
"graph_decl : type_link graph_stmt_list",
"type_link : USE STR ';'",
"graph_stmt_list : graph_stmt ';'",
"graph_stmt_list : graph_stmt_list graph_stmt ';'",
"graph_stmt : label_app",
"graph_stmt : node_dec",
"graph_stmt : arc_dec",
"graph_stmt : list_dec",
"graph_stmt : prim_dec",
"graph_stmt : expr",
"label_app : id ':' node_dec",
"node_dec : '@' id attr_list",
"node_dec : '@' id",
"arc_dec : id ARC id attr_list",
"arc_dec : id ARC id",
"list_dec : LIST_T OF type id",
"list_dec : LIST_T OF type id '=' '[' attr_list ']'",
"type : ptype",
"prim_dec : type id",
"prim_dec : type id '=' expr",
"attr_list : expr",
"attr_list : attr_list ',' expr",
"expr : '(' expr ')'",
"expr : '(' type ')' expr",
"expr : '-' expr",
"expr : expr '>' expr",
"expr : expr GTE expr",
"expr : expr '<' expr",
"expr : expr LTE expr",
"expr : expr NEQ expr",
"expr : expr EQ expr",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr '%' expr",
"expr : id '.' id",
"expr : GRAPH '.' id",
"expr : assignment",
"expr : access",
"expr : id",
"expr : func_call",
"expr : pvalue",
"access : id '[' expr ']'",
"assignment : access '=' expr",
"assignment : id '=' expr",
"id : ID",
"ptype : INT_T",
"ptype : FLT_T",
"ptype : STR_T",
"ptype : NODE_T",
"ptype : ARC_T",
"pvalue : INT",
"pvalue : FLT",
"pvalue : STR",
};

//#line 301 "graph_parser.y"


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
//#line 433 "GraphParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 56 "graph_parser.y"
{ yyval.sval = "import flow.structure.*;\nimport java.util.ArrayList;\npublic class Graph {\npublic Graph() {\nnodes = new FlowList<Node>();\narcs = new FlowList<Arc>();\n" + val_peek(0).obj.toString() + "\n}\nprivate FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }\n private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } \n public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}\n";

                                         for (String label : labels)
                                         {
                                           yyval.sval += "private Node " + label + ";\npublic Node get" + label + "() {\n  return " + label + ";\n}\n";
                                         }

                                         yyval.sval += "\n}\n";
                                         /*if (errors == 0) { //only create output java file if there are no syntax errors*/
                                         try {
                                           FileWriter graph_file = new FileWriter(new File("Graph.java"));
                                           graph_file.write(yyval.sval);
                                           graph_file.flush();
                                         }
                                         catch(IOException e) {
                                           yyerror("Could not create Solver file.");
                                         }
                                       /*}*/
                                       /*else {*/
                                         System.out.println("\n" + errors + " errors\n");
                                       }
break;
case 3:
//#line 79 "graph_parser.y"
{ /* process the typedef file */ 
                                         labels = new ArrayList<String>();
                                         try
                                         {
                                           String filepath = symbols.get("filepath") + val_peek(1).sval;
                                           TypeParser tparser = new TypeParser(new FileReader(filepath), new Hashtable());
                                           tparser.yyparse();
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not open typdef file.");
                                         } }
break;
case 4:
//#line 93 "graph_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 5:
//#line 94 "graph_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 11:
//#line 102 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 12:
//#line 105 "graph_parser.y"
{ yyval.obj = new LabelNode((ID) val_peek(2).obj, (NodeDec) val_peek(0).obj);
                                         labels.add(val_peek(2).sval); }
break;
case 13:
//#line 109 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(1).obj, (AttrList) val_peek(0).obj); symbols.put(((ID)val_peek(1).obj).toString(), (ID)val_peek(1).obj);}
break;
case 14:
//#line 110 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(0).obj, null); symbols.put(((ID)val_peek(0).obj).toString(), (ID)val_peek(0).obj);}
break;
case 15:
//#line 113 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(3).obj, (ID) val_peek(1).obj, (AttrList) val_peek(0).obj);}
break;
case 16:
//#line 114 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(2).obj, (ID) val_peek(0).obj, null); }
break;
case 17:
//#line 117 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null); symbols.put(((ID)val_peek(0).obj).toString(), (ID)val_peek(0).obj); System.out.println("3");}
break;
case 18:
//#line 118 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj); symbols.put(((ID)val_peek(4).obj).toString(), (ID)val_peek(4).obj);}
break;
case 19:
//#line 121 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 20:
//#line 124 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                         if (symbols.containsKey(val_peek(0).obj.toString())) {
                                           yyerror("Variable " + val_peek(0).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(0).obj).type = (Type) val_peek(1).obj;
                                           symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj);
                                         } }
break;
case 21:
//#line 132 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         if (symbols.containsKey(val_peek(2).obj.toString())) {
                                           yyerror("Variable " + val_peek(2).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(2).obj).type = check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                           symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj);
                                         } }
break;
case 22:
//#line 142 "graph_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 23:
//#line 143 "graph_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 24:
//#line 146 "graph_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 25:
//#line 147 "graph_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (Type) val_peek(2).obj; }
break;
case 26:
//#line 149 "graph_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 27:
//#line 154 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 159 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 29:
//#line 164 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 30:
//#line 169 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 31:
//#line 174 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 176 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 178 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 180 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 185 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 190 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 37:
//#line 195 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 38:
//#line 200 "graph_parser.y"
{ yyval.obj = new Dot((ID) val_peek(2).obj, val_peek(0).obj.toString());
                                 if (((Expression) val_peek(2).obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("node_attributes")).get(val_peek(0).obj.toString()).toString());
                                   else {
                                     yyerror("Node attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Arc")) {
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("arc_attributes")).get(val_peek(0).obj.toString()).toString());
                                   else {
                                     yyerror("Arc attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.length() > 4 && ((Expression) val_peek(2).obj).type.type.substring(0,4).equals("list")) {
                                   if (val_peek(0).obj.toString().equals("length")) {
                                     ((Expression) yyval.obj).type = new pType("int");
                                   }
                                   else {
                                     yyerror("List attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((ID) val_peek(2).obj).toString() + " is of type " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 39:
//#line 230 "graph_parser.y"
{ yyval.obj = new Dot(new ID("graph"), val_peek(0).obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey(val_peek(0).obj.toString())) {
                                   ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("labels")).get(val_peek(0).obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 40:
//#line 238 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 41:
//#line 239 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 42:
//#line 240 "graph_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 43:
//#line 248 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 44:
//#line 249 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 45:
//#line 253 "graph_parser.y"
{ yyval.obj = new ListAccess((ID) val_peek(3).obj, (Expression) val_peek(1).obj);
                                         if (!symbols.containsKey(((ID) val_peek(3).obj).toString())) {
                                           yyerror("Undeclared list '" + ((ID) val_peek(3).obj).toString() + "'");
                                           ((Expression) yyval.obj).type = new pType("error");
                                         }
                                         else if (!((Expression) val_peek(3).obj).type.type.substring(0,4).equals("list")) {
                                           yyerror("Only Lists can be indexed. " + ((Expression) val_peek(3).obj).type.type.substring(0,4));
                                           ((Expression) yyval.obj).type = new pType("error");
                                         }
                                         else if (((Expression) val_peek(1).obj).type.type != "int") {
                                           yyerror("Lists can only be indexed by ints.");
                                           ((Expression) yyval.obj).type = new pType("error");
                                         }
                                         else {
                                           ((Expression) yyval.obj).type = new Type(((ID) val_peek(3).obj).type.type.substring(5));
                                         } }
break;
case 46:
//#line 271 "graph_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 273 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 277 "graph_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 49:
//#line 285 "graph_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 50:
//#line 286 "graph_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 51:
//#line 287 "graph_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 52:
//#line 288 "graph_parser.y"
{ yyval.obj = new pType("Node"); }
break;
case 53:
//#line 289 "graph_parser.y"
{ yyval.obj = new pType("Arc"); }
break;
case 54:
//#line 292 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 55:
//#line 294 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 56:
//#line 296 "graph_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
//#line 936 "GraphParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public GraphParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public GraphParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
