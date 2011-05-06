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






//#line 2 "solver_parser.y"
  import java.io.*;
  import java.util.*;
  import flow.ast.*;
  import flow.structure.*;
//#line 22 "SolverParser.java"




public class SolverParser
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
//public class SolverParserVal is defined in SolverParserVal.java


String   yytext;//user variable to return contextual strings
SolverParserVal yyval; //used to return semantic vals from action routines
SolverParserVal yylval;//the 'lval' (result) I got from yylex()
SolverParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new SolverParserVal[YYSTACKSIZE];
  yyval=new SolverParserVal();
  yylval=new SolverParserVal();
  valptr=-1;
}
void val_push(SolverParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
SolverParserVal val_pop()
{
  if (valptr<0)
    return new SolverParserVal();
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
SolverParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new SolverParserVal();
  return valstk[ptr];
}
final SolverParserVal dup_yyval(SolverParserVal val)
{
  SolverParserVal dup = new SolverParserVal();
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
public final static short RET=268;
public final static short PRINT=269;
public final static short STR=270;
public final static short FLT=271;
public final static short INT=272;
public final static short LIST=273;
public final static short ID=274;
public final static short ARC=275;
public final static short EQ=276;
public final static short NEQ=277;
public final static short LTE=278;
public final static short GTE=279;
public final static short UNK=280;
public final static short CAST=281;
public final static short NEG=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    3,    3,    3,    3,    5,    5,
    5,    4,    4,    4,    4,    4,    4,   13,   19,    8,
    6,    7,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   18,   18,   18,   17,   11,   11,   21,
    9,    9,   20,   20,   20,   10,   16,   16,   24,   15,
   23,   23,   23,   22,   22,   22,   12,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    4,    0,    8,
    7,    7,    3,    4,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    3,    1,    0,    2,    3,    3,    4,
    4,    8,    1,    1,    1,    4,    1,    3,    1,    1,
    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   61,   62,   63,   54,   55,    0,    0,    0,    0,    0,
    0,   60,    0,    1,    0,    0,    0,    6,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,    0,    0,
   53,    0,    0,    0,    0,    0,   66,   65,   64,    0,
    0,   39,   42,    0,    0,    0,   43,    0,    0,    0,
    8,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    0,   25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    7,
    0,    0,    0,   57,    0,   45,    0,    0,    0,    0,
    0,    0,    0,   38,   23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,   35,   37,   18,    0,
   50,   19,    0,   47,    0,    0,    0,    0,   24,   58,
    0,   44,    0,    0,    0,    0,    0,   21,   22,    0,
   52,   20,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   42,   25,   43,   82,   45,   83,   28,   87,  121,   29,
   46,   47,   31,   84,
};
final static short yysindex[] = {                       254,
    0,    0,    0,    0,    0, -265,  -29,  -19, -267,   47,
   47,    0,    0,    0,  272,  272,  -30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  165,   -3, -241,  -14,
    0,   71,   47,   47,   -2,   25,    0,    0,    0,   47,
    6,    0,    0,   48,  106,  -14,    0,   48,  272,   18,
    0,    0,   47,   47,   47,   71,    0,   33,   47, -176,
    7,   21,    0, -176,    0,   37,   66,   47,   47,   47,
   47,   47,   47,   47,   47,   47,   47,   47, -176,    0,
  171,   48,  107,    0,   -7,    0,  116, -159,   47,  171,
   64,   20,   35,    0,    0,   47,   59,   59,  171,  171,
  171,  171,  117,  117,   58,    0,    0,    0,    0,   47,
    0,    0,   71,    0,   48,   65,  272,  272,    0,    0,
   40,    0,   47,  -84,  -65,  272,  -32,    0,    0,  -36,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  169,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  111,  -37,  -28,    0,  128,  201,    0,
    0,    0,    0,    0,    0,  147,  -22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   79,   31,    0,    0,    0,    0,    0,    0,    0,   85,
  148,    0,    0,    0,    0,    0,  335,  364,   29,   90,
   96,  109,   68,   73,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  151,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  127,   87,  166,    0,    0,    0,    0,    0,
  273,    0,  377,  378,  353,   42,  -54,    0,    0,  130,
  380,    0,    0,  110,
};
final static int YYTABLESIZE=546;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   32,   86,   35,   41,   41,   41,   41,   41,   40,   41,
   33,  110,   40,   40,   40,   40,   40,   47,   40,   36,
   34,   41,   41,   36,   41,   36,   36,   36,   52,   76,
   40,   40,   57,   40,   77,   75,   56,   74,   60,   78,
  128,   36,   36,   76,   36,   41,   59,   92,   77,   75,
   40,   74,   72,   78,   73,   41,   63,   76,  122,  129,
  131,   93,   77,   75,   40,   74,   72,   78,   73,   29,
   64,   59,   29,   76,   59,   36,   80,   95,   77,   75,
   72,   74,   73,   78,   76,  111,   41,   29,  132,   77,
   75,   40,   74,   89,   78,   76,   72,   12,   73,   77,
   77,   75,   50,   74,   78,   78,   96,   72,   33,   73,
   33,   33,   33,   32,  114,   32,   32,   32,   72,   49,
   73,   29,   49,   59,  116,   48,   33,   33,   48,   33,
   27,   32,   32,   27,   32,   50,   28,   49,   49,   28,
   49,   49,  117,   48,   48,   54,   48,  109,   27,   26,
  110,   79,   26,   76,   28,  123,  112,  118,   77,  113,
   33,   60,  126,   78,  127,   32,   53,   26,    3,   17,
   67,   49,    1,    2,    3,    4,    5,   48,    6,    7,
    8,   51,   27,   10,   11,   88,   67,   46,   28,   12,
   46,    1,    2,    3,    4,    5,   55,    6,    7,    8,
    2,   26,   10,   11,   54,    0,   51,   76,   12,   56,
   50,   50,   77,   75,   51,   74,   50,   78,    0,  120,
    1,    2,    3,    4,    5,   53,    6,    7,    8,    0,
    0,   10,   11,    0,    0,    0,    0,   12,   41,   41,
   41,   41,   88,  124,  125,    0,    0,   40,   40,   40,
   40,    0,  130,    0,    0,   55,    0,    0,   36,   36,
   36,   36,    1,    2,    3,    4,    5,   36,   68,   69,
   70,   71,   24,    0,    0,   37,   38,   39,    0,   12,
    0,    0,   68,   69,   70,   71,    0,   24,   24,   51,
   51,    0,    0,    0,    0,   51,   68,   69,   70,   71,
    0,    0,    0,    0,   29,   29,    0,    0,   36,    0,
    0,    0,   68,   69,   70,   71,   37,   38,   39,    0,
   12,   24,    0,   68,   69,   70,   71,    1,    2,    3,
    4,    5,    0,    0,    0,    0,   70,   71,    0,    0,
    0,    0,    0,   33,   33,   33,   33,    0,   32,   32,
   32,   32,   27,    0,   49,   49,   49,   49,    0,    0,
   48,   48,   48,   48,    0,   27,   27,   27,   27,    0,
    0,   28,   28,    0,    0,   31,   26,    0,   31,   30,
    0,   58,    0,    0,   26,   26,    0,   44,   48,   24,
   24,   26,   26,   31,   30,   30,   24,   24,   24,    0,
    0,   27,   24,    0,   30,    0,    0,   30,    0,    0,
   61,   62,   91,    0,    0,    0,   94,   65,   66,    0,
    0,    0,   30,    0,    0,   26,    0,   31,   30,    0,
   81,  108,   85,    0,    0,    0,   90,    0,    0,    0,
    0,    0,    0,    0,    0,   97,   98,   99,  100,  101,
  102,  103,  104,  105,  106,  107,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  115,    0,    0,   27,
   27,    0,    0,  119,    0,    0,   27,   27,   27,    0,
    0,    0,   27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   26,   26,    0,   30,   30,    0,    0,
   26,   26,   26,   30,   30,   30,   26,    0,    0,   30,
    1,    2,    3,    4,    5,    0,    6,    7,    8,    0,
    9,   10,   11,    0,    0,    0,    0,   12,    1,    2,
    3,    4,    5,    0,    6,    7,    8,    0,    0,   10,
   11,    0,    0,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   56,  270,   41,   42,   43,   44,   45,   37,   47,
   40,   44,   41,   42,   43,   44,   45,   40,   47,   37,
   40,   59,   60,   41,   62,   43,   44,   45,   59,   37,
   59,   60,  274,   62,   42,   43,   40,   45,   61,   47,
  125,   59,   60,   37,   62,   40,   61,   41,   42,   43,
   45,   45,   60,   47,   62,   93,   59,   37,  113,  125,
   93,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   46,   41,   44,   37,   44,   93,   59,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,  125,   42,
   43,   45,   45,   61,   47,   37,   60,  274,   62,   42,
   42,   43,   16,   45,   47,   47,   41,   60,   41,   62,
   43,   44,   45,   41,  274,   43,   44,   45,   60,   41,
   62,   93,   44,   93,   61,   41,   59,   60,   44,   62,
   41,   59,   60,   44,   62,   49,   41,   59,   60,   44,
   62,   15,  123,   59,   60,   40,   62,   41,   59,   41,
   44,   46,   44,   37,   59,   91,   41,  123,   42,   44,
   93,   32,  123,   47,  123,   93,   61,   59,    0,   59,
   41,   93,  257,  258,  259,  260,  261,   93,  263,  264,
  265,   16,   93,  268,  269,   56,   59,   41,   93,  274,
   44,  257,  258,  259,  260,  261,   91,  263,  264,  265,
    0,   93,  268,  269,   40,   -1,   59,   37,  274,   59,
  124,  125,   42,   43,   49,   45,  130,   47,   -1,  110,
  257,  258,  259,  260,  261,   61,  263,  264,  265,   -1,
   -1,  268,  269,   -1,   -1,   -1,   -1,  274,  276,  277,
  278,  279,  113,  117,  118,   -1,   -1,  276,  277,  278,
  279,   -1,  126,   -1,   -1,   91,   -1,   -1,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  262,  276,  277,
  278,  279,    0,   -1,   -1,  270,  271,  272,   -1,  274,
   -1,   -1,  276,  277,  278,  279,   -1,   15,   16,  124,
  125,   -1,   -1,   -1,   -1,  130,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  276,  277,   -1,   -1,  262,   -1,
   -1,   -1,  276,  277,  278,  279,  270,  271,  272,   -1,
  274,   49,   -1,  276,  277,  278,  279,  257,  258,  259,
  260,  261,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,   -1,  276,  277,
  278,  279,    0,   -1,  276,  277,  278,  279,   -1,   -1,
  276,  277,  278,  279,   -1,  276,  277,   15,   16,   -1,
   -1,  276,  277,   -1,   -1,   41,    0,   -1,   44,    0,
   -1,   29,   -1,   -1,  276,  277,   -1,   10,   11,  117,
  118,   15,   16,   59,   15,   16,  124,  125,  126,   -1,
   -1,   49,  130,   -1,   41,   -1,   -1,   44,   -1,   -1,
   33,   34,   60,   -1,   -1,   -1,   64,   40,   41,   -1,
   -1,   -1,   59,   -1,   -1,   49,   -1,   93,   49,   -1,
   53,   79,   55,   -1,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   68,   69,   70,   71,   72,
   73,   74,   75,   76,   77,   78,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   89,   -1,   -1,  117,
  118,   -1,   -1,   96,   -1,   -1,  124,  125,  126,   -1,
   -1,   -1,  130,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  117,  118,   -1,  117,  118,   -1,   -1,
  124,  125,  126,  124,  125,  126,  130,   -1,   -1,  130,
  257,  258,  259,  260,  261,   -1,  263,  264,  265,   -1,
  267,  268,  269,   -1,   -1,   -1,   -1,  274,  257,  258,
  259,  260,  261,   -1,  263,  264,  265,   -1,   -1,  268,
  269,   -1,   -1,   -1,   -1,  274,
};
}
final static short YYFINAL=13;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"INT_T","FLT_T","STR_T","NODE_T",
"ARC_T","GRAPH","LIST_T","WHILE","IF","OF","USE","RET","PRINT","STR","FLT",
"INT","LIST","ID","ARC","EQ","NEQ","LTE","GTE","UNK","CAST","NEG",
};
final static String yyrule[] = {
"$accept : valid_program",
"valid_program : solver",
"solver : type_link solver_stmt_list",
"solver : solver_stmt_list",
"type_link : USE STR ';'",
"solver_stmt_list : solver_stmt ';'",
"solver_stmt_list : block_stmt",
"solver_stmt_list : solver_stmt_list solver_stmt ';'",
"solver_stmt_list : solver_stmt_list block_stmt",
"block_stmt : while_stmt",
"block_stmt : if_stmt",
"block_stmt : func_dec",
"solver_stmt : list_dec",
"solver_stmt : prim_dec",
"solver_stmt : assignment",
"solver_stmt : print_stmt",
"solver_stmt : func_call",
"solver_stmt : RET expr",
"func_call : id '(' attr_list ')'",
"$$1 :",
"func_dec : param '(' param_list ')' $$1 '{' solver_stmt_list '}'",
"while_stmt : WHILE '(' expr ')' '{' solver_stmt_list '}'",
"if_stmt : IF '(' expr ')' '{' solver_stmt_list '}'",
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
"param_list : param_list ',' param",
"param_list : param",
"param_list :",
"param : type ID",
"assignment : access '=' expr",
"assignment : id '=' expr",
"access : id '[' expr ']'",
"list_dec : LIST_T OF type id",
"list_dec : LIST_T OF type id '=' '[' attr_list ']'",
"type : ptype",
"type : NODE_T",
"type : ARC_T",
"prim_dec : type id '=' expr",
"attr_list : attr",
"attr_list : attr_list ',' attr",
"attr : expr",
"id : ID",
"ptype : INT_T",
"ptype : FLT_T",
"ptype : STR_T",
"pvalue : INT",
"pvalue : FLT",
"pvalue : STR",
"print_stmt : PRINT expr",
};

//#line 327 "solver_parser.y"

  private SolverLexer lexer;
  private Hashtable symbols;
  //We need another Hashtable for temporary storage
  private Hashtable old;
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
    System.out.println(yyl_return);

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
  
  private Type check_type(AttrList attrs, ArrayList<Param> params) {
    ArrayList<Attr> attrslist = attrs.toArrayList();
    if(attrslist.size() == params.size()) {
        yyerror("Expected " + params.size() + " args, got " + attrslist.size());
        return new pType("error");
    }
    for(int i=0; i<params.size(); i++) {
        String paramType = params.get(i).type.type;
        String attrType = attrslist.get(i).type.type;
        if(paramType != attrType) {
            yyerror("Expected type " + paramType + " got " + "attrType");
            return new pType("error");
        }
    }
    return new pType("success");
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
//#line 493 "SolverParser.java"
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
//#line 54 "solver_parser.y"
{ yyval.sval = "import java.util.*;\n\npublic class Solver {\npublic static void main(String[] args) {\n" + val_peek(0).obj.toString() + "}\n}";
                                     if (errors == 0) { /*only create output java file if there are no syntax errors*/
                                       try {
                                         FileWriter graph_file = new FileWriter(new File("Solver.java"));
                                         graph_file.write(yyval.sval);
                                         graph_file.flush();
                                       }
                                       catch(IOException e) {
                                         yyerror("Could not create Solver file.");
                                       }
                                     }
                                     else {
                                       System.out.println("\n" + errors + " errors\n");
                                     } }
break;
case 3:
//#line 68 "solver_parser.y"
{ yyerror("The first statement in the file must be a typelink.");
                                     System.out.println("\n" + errors + " errors\n"); }
break;
case 4:
//#line 72 "solver_parser.y"
{ /* process the typedef file */ 
                             labels = new ArrayList<String>();
                             try {
                               String filepath = symbols.get("filepath") + val_peek(1).sval;
                               /*System.out.println("\nTrying to open " + filepath + "\n");*/
                               TypeParser tparser = new TypeParser(new FileReader(filepath), new Hashtable());
                               tparser.yyparse();
                              }
                              catch(IOException e) {
                                yyerror("Could not open typedef file.");
                            } }
break;
case 5:
//#line 85 "solver_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 6:
//#line 86 "solver_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(0).obj); }
break;
case 7:
//#line 87 "solver_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 8:
//#line 88 "solver_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj); }
break;
case 11:
//#line 93 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 16:
//#line 100 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 17:
//#line 101 "solver_parser.y"
{ /*This is different - make it somehow*/ }
break;
case 18:
//#line 104 "solver_parser.y"
{ /*Make sure this function was previously declared*/
                                                                try {
                                                                    ID function_name = (ID)symbols.get(val_peek(3).toString());
                                                                    fType functionType = (fType) function_name.type;
                                                                    /*Check attr_list against the parameter types*/
                                                                    check_type((AttrList)val_peek(1).obj, functionType.paramTypes);
                                                                    yyval.obj = new FunctionCall(function_name, (AttrList) val_peek(1).obj);
                                                                    ((Expression) yyval.obj).type = function_name.type; 
                                                                }
                                                                catch(Exception e) {
                                                                    yyerror(val_peek(3).toString() + " not found, or not callable.");                                                                    
                                                                }
                                                               }
break;
case 19:
//#line 120 "solver_parser.y"
{/*At this point, we know that we are going to end up inside a function body, */
            Param param = (Param) val_peek(3).obj;
            ID function_id = param.id;
            /*Check that this function name was not previously used by something else*/
            if(symbols.containsKey(function_id.toString())) {
                yyerror(function_id.toString() + " was already declared, cannot use as function name");
            }
            Type ret_type = param.type;
            ParamList params = (ParamList) val_peek(1).obj;
            /*Add the id to the symbol table*/
            function_id.type = new fType(ret_type.type, params);
            symbols.put(function_id.toString(), function_id);
            /*so we'll save the current symbol table and start a new on for the function*/
            old = symbols;
            /*Create a new symbol table (clone because we want access to higher scoped ids too)*/
            symbols = (Hashtable)old.clone();
            /*Add the symbols from the param_list into the symbol table*/
            for(Param p : params.toArrayList()) {
                ID id = p.id;
                id.type = p.type;
                /*Check that the parameter name is not the same as the function name*/
                if(id.toString().equals(function_id.toString())) {
                    yyerror("Your parameter cannot be the same as your function name " + function_id.toString());
                }
                /*Add the id/overwrite the id into the symbol table*/
                symbols.put(id.toString(), id);
            }
            }
break;
case 20:
//#line 149 "solver_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(7).obj, (ParamList) val_peek(5).obj, (SequenceNode) val_peek(2).obj);
                                                                /*TODO: TYPE CHECK RETURNS WITH Type ret_type*/
                                                                /*Restore the old symbol table*/
                                                                symbols = old;
                                                                }
break;
case 21:
//#line 156 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 22:
//#line 159 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 23:
//#line 162 "solver_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 24:
//#line 163 "solver_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (pType) val_peek(2).obj; }
break;
case 25:
//#line 165 "solver_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 26:
//#line 167 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 27:
//#line 169 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 171 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 29:
//#line 173 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 30:
//#line 175 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 31:
//#line 177 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 179 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 181 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 183 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 185 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 187 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 37:
//#line 189 "solver_parser.y"
{ yyval.obj = new Dot((ID) val_peek(2).obj, (ID) val_peek(0).obj);
                                 if (((Expression) val_peek(2).obj).type.type.equals("List")) {
                                   /* Some kind of magic needed here */
                                   ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type;
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = ((Type) ((Hashtable) symbols.get("node_attributes")).get(((ID) val_peek(0).obj).toString()));
                                   else {
                                     yyerror("Node attribute '" + ((Expression) val_peek(0).obj).type.type + "' does not exist");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Arc")) {
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = ((Type) ((Hashtable) symbols.get("arc_attributes")).get(((ID) val_peek(0).obj).toString()));
                                   else {
                                     yyerror("Arc attribute '" + ((Expression) val_peek(0).obj).type.type + "' does not exist");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 38:
//#line 214 "solver_parser.y"
{ yyval.obj = new Dot(new ID("Graph"), (ID) val_peek(0).obj);
                                 if (symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   ((Expression) yyval.obj).type = ((ID) symbols.get(((ID) val_peek(0).obj).toString())).type;
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 39:
//#line 221 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 40:
//#line 222 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 41:
//#line 223 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 42:
//#line 231 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 43:
//#line 233 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 44:
//#line 237 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 45:
//#line 238 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 46:
//#line 239 "solver_parser.y"
{ yyval.obj = null; }
break;
case 47:
//#line 242 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 48:
//#line 245 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 49:
//#line 247 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 50:
//#line 251 "solver_parser.y"
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
                                           ((Expression) yyval.obj).type = new Type(((ID) val_peek(3).obj).type.type.substring(4));
                                         } }
break;
case 51:
//#line 269 "solver_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                                /*added space, was new Type("list" ...) -> new Type("list " ...)*/
                                              ((ID) val_peek(0).obj).type = new Type("list " + val_peek(1).obj);
						/*This line below is unneccessary as id was already put into the symbol table when it was parsed*/
                                              symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj); }
break;
case 52:
//#line 274 "solver_parser.y"
{ /*Do typechecking*/
						check_type((Type) val_peek(5).obj, (AttrList) val_peek(1).obj);
                                                yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj);
                                              ((ID) val_peek(4).obj).type = new Type("list " + val_peek(5).obj);
                                              symbols.put(((ID) val_peek(4).obj).toString(), val_peek(4).obj); 
                                              }
break;
case 53:
//#line 282 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 54:
//#line 283 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 55:
//#line 284 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 56:
//#line 288 "solver_parser.y"
{ check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                        yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                        ((Expression) val_peek(2).obj).type = (Type) val_peek(3).obj;
                                        symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj); }
break;
case 57:
//#line 294 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Attr) val_peek(0).obj); }
break;
case 58:
//#line 295 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Attr) val_peek(0).obj); }
break;
case 59:
//#line 299 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 60:
//#line 302 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 61:
//#line 310 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 62:
//#line 311 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 63:
//#line 312 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 64:
//#line 315 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 65:
//#line 317 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 66:
//#line 319 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).sval);
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 67:
//#line 323 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1036 "SolverParser.java"
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
public SolverParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public SolverParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
