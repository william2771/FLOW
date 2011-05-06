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
    5,    4,    4,    4,    4,    4,    4,   13,    8,    6,
    7,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   18,   18,   18,   17,   11,   11,   20,    9,
    9,   19,   19,   19,   10,   16,   16,   23,   15,   22,
   22,   22,   21,   21,   21,   12,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    4,    7,    7,
    7,    3,    4,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    3,    1,    0,    2,    3,    3,    4,    4,
    8,    1,    1,    1,    4,    1,    3,    1,    1,    1,
    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   60,   61,   62,   53,   54,    0,    0,    0,    0,    0,
    0,   59,    0,    1,    0,    0,    0,    6,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,    0,    0,
   52,    0,    0,    0,    0,    0,   65,   64,   63,    0,
    0,   38,   41,    0,    0,    0,   42,    0,    0,    0,
    8,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    0,   24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    7,
    0,    0,    0,   56,    0,   44,    0,    0,    0,    0,
    0,    0,    0,   37,   22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,   34,   36,   18,    0,
   49,    0,    0,   46,    0,    0,    0,    0,   23,   57,
    0,   43,    0,    0,    0,    0,    0,   20,   21,   19,
   51,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   42,   25,   43,   82,   45,   83,   28,   87,   29,   46,
   47,   31,   84,
};
final static short yysindex[] = {                       170,
    0,    0,    0,    0,    0, -265,  -29,  -19, -267,   47,
   47,    0,    0,    0,  147,  147,  -47,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  165,  -11, -241,  -24,
    0,  -14,   47,   47,  -12,   25,    0,    0,    0,   47,
    6,    0,    0,   48,  191,  -24,    0,   48,  147,   18,
    0,    0,   47,   47,   47,  -14,    0,   39,   47, -176,
    7,   21,    0, -176,    0,   37,   66,   47,   47,   47,
   47,   47,   47,   47,   47,   47,   47,   47, -176,    0,
  286,   48,  166,    0,   -7,    0,  173, -159,   47,  286,
   64,   19,   23,    0,    0,   47,   59,   59,  286,  286,
  286,  286,  115,  115,  101,    0,    0,    0,    0,   47,
    0,   28,  -14,    0,   48,   65,  147,  147,    0,    0,
  147,    0,   47,  -84,  -65,  -36,   61,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  158,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  106,  -37,  -28,    0,  127,  187,    0,
    0,    0,    0,    0,    0,  213,  -22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   79,   31,    0,    0,    0,    0,    0,    0,    0,   85,
  129,    0,    0,    0,    0,    0,  123,  249,   29,   90,
   96,  109,   68,   73,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  132,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,   42,   45,   87,    0,    0,    0,    0,    0,
  273,    0,  365,  474,  353,   74,  -54,    0,  174,  377,
    0,    0,   91,
};
final static int YYTABLESIZE=570;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
   32,   86,   35,   40,   40,   40,   40,   40,   39,   40,
   33,   52,   39,   39,   39,   39,   39,   46,   39,   35,
   34,   40,   40,   35,   40,   35,   35,   35,   56,   76,
   39,   39,   57,   39,   77,   75,   59,   74,   59,   78,
  128,   35,   35,   76,   35,   41,   63,   92,   77,   75,
   40,   74,   72,   78,   73,   40,   49,   76,  122,  129,
   50,   93,   77,   75,   39,   74,   72,   78,   73,   28,
   64,   58,   28,   76,   58,   35,   80,   95,   77,   75,
   72,   74,   73,   78,   76,  111,   41,   28,  130,   77,
   75,   40,   74,   50,   78,   76,   72,   12,   73,   89,
   77,   75,   51,   74,  110,   78,   96,   72,   32,   73,
   32,   32,   32,   31,  114,   31,   31,   31,   72,   48,
   73,   28,   48,   58,  116,   47,   32,   32,   47,   32,
   26,   31,   31,   26,   31,   51,   27,   48,   48,   27,
   48,  117,   77,   47,   47,  118,   47,   78,   26,   25,
  121,   76,   25,  131,   27,  123,   77,    3,  124,  125,
   32,   78,  126,   30,   17,   31,   30,   25,   50,   50,
   50,   48,    1,    2,    3,    4,    5,   47,    6,    7,
    8,   30,   26,   10,   11,   66,    2,   50,   27,   12,
   55,    1,    2,    3,    4,    5,  127,    6,    7,    8,
  120,   25,   10,   11,   54,   60,  109,    0,   12,  110,
   51,   51,   51,  112,   67,   30,  113,    0,    0,    0,
    1,    2,    3,    4,    5,   53,    6,    7,    8,   88,
   54,   10,   11,    0,    0,    0,   79,   12,   40,   40,
   40,   40,    1,    2,    3,    4,    5,   39,   39,   39,
   39,   53,    0,   45,    0,   55,   45,    0,   35,   35,
   35,   35,    1,    2,    3,    4,    5,   36,   68,   69,
   70,   71,   24,    0,    0,   37,   38,   39,    0,   12,
    0,   55,   68,   69,   70,   71,   88,   24,   24,   29,
    0,    0,   29,    0,    0,    0,   68,   69,   70,   71,
    0,    0,    0,    0,   28,   28,    0,   29,   36,    0,
    0,    0,   68,   69,   70,   71,   37,   38,   39,    0,
   12,   24,   76,   68,   69,   70,   71,   77,   75,    0,
   74,    0,   78,    0,    0,    0,   70,   71,    0,    0,
    0,   29,    0,   32,   32,   32,   32,    0,   31,   31,
   31,   31,   27,    0,   48,   48,   48,   48,    0,    0,
   47,   47,   47,   47,   26,   26,   26,   27,   27,    0,
    0,   27,   27,    0,    0,    0,   30,    0,    0,   26,
   26,   58,    0,    0,   25,   25,    0,    0,    0,   24,
   24,   30,   30,   24,    0,    0,   24,   24,   24,    0,
    0,   27,    0,    1,    2,    3,    4,    5,    0,    6,
    7,    8,   91,   26,   10,   11,   94,    0,    0,    0,
   12,    0,    0,    0,    0,   30,    1,    2,    3,    4,
    5,  108,    6,    7,    8,    0,    9,   10,   11,    0,
    0,    0,    0,   12,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   27,
   27,    0,    0,   27,    0,    0,   27,   27,   27,    0,
    0,   26,   26,   44,   48,   26,    0,    0,   26,   26,
   26,    0,    0,   30,   30,    0,    0,   30,    0,    0,
   30,   30,   30,    0,    0,    0,   61,   62,    0,    0,
    0,    0,    0,   65,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   81,    0,   85,    0,
    0,    0,   90,    0,    0,    0,    0,    0,    0,    0,
    0,   97,   98,   99,  100,  101,  102,  103,  104,  105,
  106,  107,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,    0,    0,    0,    0,    0,  119,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   56,  270,   41,   42,   43,   44,   45,   37,   47,
   40,   59,   41,   42,   43,   44,   45,   40,   47,   37,
   40,   59,   60,   41,   62,   43,   44,   45,   40,   37,
   59,   60,  274,   62,   42,   43,   61,   45,   61,   47,
  125,   59,   60,   37,   62,   40,   59,   41,   42,   43,
   45,   45,   60,   47,   62,   93,   15,   37,  113,  125,
   16,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   46,   41,   44,   37,   44,   93,   59,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,  125,   42,
   43,   45,   45,   49,   47,   37,   60,  274,   62,   61,
   42,   43,   16,   45,   44,   47,   41,   60,   41,   62,
   43,   44,   45,   41,  274,   43,   44,   45,   60,   41,
   62,   93,   44,   93,   61,   41,   59,   60,   44,   62,
   41,   59,   60,   44,   62,   49,   41,   59,   60,   44,
   62,  123,   42,   59,   60,  123,   62,   47,   59,   41,
  123,   37,   44,   93,   59,   91,   42,    0,  117,  118,
   93,   47,  121,   41,   59,   93,   44,   59,  124,  125,
  126,   93,  257,  258,  259,  260,  261,   93,  263,  264,
  265,   59,   93,  268,  269,   59,    0,   59,   93,  274,
   59,  257,  258,  259,  260,  261,  123,  263,  264,  265,
  110,   93,  268,  269,   40,   32,   41,   -1,  274,   44,
  124,  125,  126,   41,   41,   93,   44,   -1,   -1,   -1,
  257,  258,  259,  260,  261,   61,  263,  264,  265,   56,
   40,  268,  269,   -1,   -1,   -1,   46,  274,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  276,  277,  278,
  279,   61,   -1,   41,   -1,   91,   44,   -1,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  262,  276,  277,
  278,  279,    0,   -1,   -1,  270,  271,  272,   -1,  274,
   -1,   91,  276,  277,  278,  279,  113,   15,   16,   41,
   -1,   -1,   44,   -1,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  276,  277,   -1,   59,  262,   -1,
   -1,   -1,  276,  277,  278,  279,  270,  271,  272,   -1,
  274,   49,   37,  276,  277,  278,  279,   42,   43,   -1,
   45,   -1,   47,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   93,   -1,  276,  277,  278,  279,   -1,  276,  277,
  278,  279,    0,   -1,  276,  277,  278,  279,   -1,   -1,
  276,  277,  278,  279,    0,  276,  277,   15,   16,   -1,
   -1,  276,  277,   -1,   -1,   -1,    0,   -1,   -1,   15,
   16,   29,   -1,   -1,  276,  277,   -1,   -1,   -1,  117,
  118,   15,   16,  121,   -1,   -1,  124,  125,  126,   -1,
   -1,   49,   -1,  257,  258,  259,  260,  261,   -1,  263,
  264,  265,   60,   49,  268,  269,   64,   -1,   -1,   -1,
  274,   -1,   -1,   -1,   -1,   49,  257,  258,  259,  260,
  261,   79,  263,  264,  265,   -1,  267,  268,  269,   -1,
   -1,   -1,   -1,  274,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  117,
  118,   -1,   -1,  121,   -1,   -1,  124,  125,  126,   -1,
   -1,  117,  118,   10,   11,  121,   -1,   -1,  124,  125,
  126,   -1,   -1,  117,  118,   -1,   -1,  121,   -1,   -1,
  124,  125,  126,   -1,   -1,   -1,   33,   34,   -1,   -1,
   -1,   -1,   -1,   40,   41,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   53,   -1,   55,   -1,
   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   68,   69,   70,   71,   72,   73,   74,   75,   76,
   77,   78,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   89,   -1,   -1,   -1,   -1,   -1,   -1,   96,
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
"func_dec : param '(' param_list ')' '{' solver_stmt_list '}'",
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

//#line 307 "solver_parser.y"

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
//#line 477 "SolverParser.java"
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
                               TypeParser tparser = new TypeParser(new FileReader(filepath), symbols);
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
{ yyval.obj = new FunctionCall((ID) val_peek(3).obj, (AttrList) val_peek(1).obj);
                                                                ((Expression) yyval.obj).type = ((Expression) val_peek(3).obj).type; }
break;
case 19:
//#line 108 "solver_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(6).obj, (ParamList) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 20:
//#line 111 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 21:
//#line 114 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 22:
//#line 117 "solver_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 23:
//#line 118 "solver_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (pType) val_peek(2).obj; }
break;
case 24:
//#line 120 "solver_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 25:
//#line 125 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 26:
//#line 130 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 27:
//#line 135 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 140 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 29:
//#line 145 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 30:
//#line 147 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 31:
//#line 149 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 151 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 156 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 161 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 166 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 171 "solver_parser.y"
{ yyval.obj = new Dot((ID) val_peek(2).obj, (ID) val_peek(0).obj);
                                 if (((Expression) val_peek(2).obj).type.type.equals("List")) {
                                   /* Some kind of magic needed here */
                                   ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type;
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = ((Type) ((Hashtable) symbols.get("node_attributes")).get(((ID) val_peek(0).obj).toString()));
                                   else {
                                     yyerror("Node attribute '" + ((Expression) val_peek(0).obj).toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Arc")) {
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = ((Type) ((Hashtable) symbols.get("arc_attributes")).get(((ID) val_peek(0).obj).toString()));
                                   else {
                                     yyerror("Arc attribute '" + ((Expression) val_peek(0).obj).toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 37:
//#line 196 "solver_parser.y"
{ yyval.obj = new Dot(new ID("Graph"), (ID) val_peek(0).obj);
                                 if (symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   ((Expression) yyval.obj).type = ((ID) symbols.get(((ID) val_peek(0).obj).toString())).type;
                                 }
                                 else {
                                   yyerror("Graph attribute '" + ((ID) val_peek(0).obj).toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 38:
//#line 204 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 39:
//#line 205 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 40:
//#line 206 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 41:
//#line 214 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 42:
//#line 215 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 43:
//#line 219 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 44:
//#line 220 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 45:
//#line 221 "solver_parser.y"
{ yyval.obj = null; }
break;
case 46:
//#line 224 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 47:
//#line 227 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 229 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 49:
//#line 233 "solver_parser.y"
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
case 50:
//#line 251 "solver_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                              /*added space, was new Type("list" ...) -> new Type("list " ...)*/
                                              ((ID) val_peek(0).obj).type = new Type("list " + val_peek(1).obj);
                                              symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj); }
break;
case 51:
//#line 255 "solver_parser.y"
{ /*Make a for loop across attr_list and check for type*/
                                              yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj);
                                              ((ID) val_peek(4).obj).type = new Type("list " + val_peek(5).obj);
                                              symbols.put(((ID) val_peek(4).obj).toString(), val_peek(4).obj); 
                                              }
break;
case 52:
//#line 262 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 53:
//#line 263 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 54:
//#line 264 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 55:
//#line 268 "solver_parser.y"
{ check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                         yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         ((Expression) val_peek(2).obj).type = (Type) val_peek(3).obj;
                                         symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj); }
break;
case 56:
//#line 274 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Attr) val_peek(0).obj); }
break;
case 57:
//#line 275 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Attr) val_peek(0).obj); }
break;
case 58:
//#line 279 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 59:
//#line 282 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 60:
//#line 290 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 61:
//#line 291 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 62:
//#line 292 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 63:
//#line 295 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 64:
//#line 297 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 65:
//#line 299 "solver_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 66:
//#line 303 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1000 "SolverParser.java"
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
