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
   14,   18,   18,   18,   17,   11,   11,   20,    9,    9,
   19,   19,   19,   10,   16,   16,   23,   15,   22,   22,
   22,   21,   21,   21,   12,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    2,    4,    7,    7,
    7,    3,    4,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    1,    1,    1,    1,
    1,    3,    1,    0,    2,    3,    3,    4,    4,    8,
    1,    1,    1,    4,    1,    3,    1,    1,    1,    1,
    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   59,   60,   61,   52,   53,    0,    0,    0,    0,    0,
    0,   58,    0,    1,    0,    0,    0,    6,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,    0,    0,
   51,    0,    0,    0,    0,   64,   63,   62,    0,    0,
   37,   40,    0,    0,    0,   41,    0,    0,    0,    8,
    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    4,   24,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,   55,    0,   43,    0,    0,    0,    0,    0,    0,
    0,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,   34,   36,   18,    0,   48,    0,    0,
   45,    0,    0,    0,    0,   23,   56,    0,   42,    0,
    0,    0,    0,    0,   20,   21,   19,   50,
};
final static short yydgoto[] = {                         13,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   41,   25,   42,   80,   44,   81,   28,   85,   29,   45,
   46,   31,   82,
};
final static short yysindex[] = {                       233,
    0,    0,    0,    0,    0, -265,  -38,  -29, -267,   47,
   47,    0,    0,    0,  251,  251,  -30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  191,   -3, -241,   -2,
    0,  -14,   47,   47,  -12,    0,    0,    0,   47,    6,
    0,    0,   48,  319,   -2,    0,   48,  251,   12,    0,
    0,   47,   47,   47,  -14,    0,   16,   47, -174,    7,
   21,    0,    0,   37,   64,   47,   47,   47,   47,   47,
   47,   47,   47,   47,   47,   47, -174,    0,  168,   48,
  -23,    0,   -7,    0,  102, -167,   47,  168,   81,    2,
   35,    0,   47,   59,   59,  168,  168,  168,  168,  265,
  265,   56,    0,    0,    0,    0,   47,    0,   39,  -14,
    0,   48,   61,  251,  251,    0,    0,  251,    0,   47,
  -84,  -65,  -36,  -32,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  154,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  104,  -37,  -28,    0,  105,  165,    0,    0,
    0,    0,    0,    0,  107,   75,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,   31,
    0,    0,    0,    0,    0,    0,    0,   85,  110,    0,
    0,    0,    0,  175,  334,   29,   90,   96,  109,   68,
   73,  -17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  111,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   42,  170,  210,    0,    0,    0,    0,    0,
  274,    0,  320,  412,  294,   51,  -16,    0,  127,  354,
    0,    0,   80,
};
final static int YYTABLESIZE=525;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   32,   33,   35,   39,   39,   39,   39,   39,   38,   39,
   34,  107,   38,   38,   38,   38,   38,  106,   38,   35,
  107,   39,   39,   35,   39,   35,   35,   35,   51,   74,
   38,   38,   56,   38,   75,   73,   55,   72,   84,   76,
  125,   35,   35,   74,   35,   40,   62,   90,   75,   73,
   39,   72,   70,   76,   71,   39,   48,   74,   58,  126,
  128,   91,   75,   73,   38,   72,   70,   76,   71,   28,
   78,   57,   28,   74,   57,   35,   87,   92,   75,   73,
   70,   72,   71,   76,   74,  108,   40,   28,  127,   75,
   73,   39,   72,  119,   76,   74,   70,   75,   71,   12,
   75,   73,   76,   72,   93,   76,  111,   70,   32,   71,
   32,   32,   32,   31,   45,   31,   31,   31,   70,   47,
   71,   28,   47,   57,  114,   46,   32,   32,   46,   32,
   26,   31,   31,   26,   31,   58,   27,   47,   47,   27,
   47,  113,  109,   46,   46,  110,   46,   44,   26,   25,
   44,  120,   25,    3,   27,  121,  122,  115,   59,  123,
   32,  118,   17,   65,    2,   31,   65,   25,   49,   54,
  124,   47,    1,    2,    3,    4,    5,   46,    6,    7,
    8,   86,   26,   10,   11,   49,  117,    0,   27,   12,
    0,    1,    2,    3,    4,    5,    0,    6,    7,    8,
    0,   25,   10,   11,   74,    0,    0,    0,   12,   75,
   73,    0,   72,    0,   76,   30,    0,   49,   30,    0,
    1,    2,    3,    4,    5,   50,    6,    7,    8,    0,
   53,   10,   11,   30,    0,    0,   86,   12,   39,   39,
   39,   39,    1,    2,    3,    4,    5,   38,   38,   38,
   38,   52,    0,    0,    0,    0,    0,   50,   35,   35,
   35,   35,    1,    2,    3,    4,    5,   30,   66,   67,
   68,   69,    0,   24,    0,   36,   37,   38,    0,   12,
    0,   54,   66,   67,   68,   69,    0,    0,   24,   24,
   49,   49,   49,   27,    0,    0,   66,   67,   68,   69,
    0,   74,    0,    0,   28,   28,   75,    0,   27,   27,
    0,   76,   66,   67,   68,   69,   36,   37,   38,   26,
   12,   24,   57,   66,   67,   68,   69,    0,    0,    0,
   50,   50,   50,    0,   26,   26,   68,   69,    0,    0,
    0,   27,    0,   32,   32,   32,   32,    0,   31,   31,
   31,   31,   89,   30,   47,   47,   47,   47,   53,    0,
   46,   46,   46,   46,   77,   26,   26,   26,   30,   30,
  105,   27,   27,    0,   29,    0,    0,   29,    0,   52,
    0,    0,    0,    0,   25,   25,    0,   24,   24,    0,
    0,   24,   29,    0,   24,   24,   24,    0,    0,    0,
    0,   30,    0,    0,    0,    0,    0,   27,   27,   54,
    0,   27,    0,    0,   27,   27,   27,    0,    0,    0,
    0,   43,   47,    0,    0,    0,   29,    0,    0,    0,
    0,    0,    0,   26,   26,    0,    0,   26,    0,    0,
   26,   26,   26,    0,   60,   61,    0,    0,    0,    0,
   63,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,    0,   83,    0,   30,   30,   88,
    0,   30,    0,    0,   30,   30,   30,   94,   95,   96,
   97,   98,   99,  100,  101,  102,  103,  104,    0,    1,
    2,    3,    4,    5,    0,    6,    7,    8,  112,    9,
   10,   11,    0,    0,  116,    0,   12,    1,    2,    3,
    4,    5,    0,    6,    7,    8,    0,    0,   10,   11,
    0,    0,    0,    0,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   40,  270,   41,   42,   43,   44,   45,   37,   47,
   40,   44,   41,   42,   43,   44,   45,   41,   47,   37,
   44,   59,   60,   41,   62,   43,   44,   45,   59,   37,
   59,   60,  274,   62,   42,   43,   40,   45,   55,   47,
  125,   59,   60,   37,   62,   40,   59,   41,   42,   43,
   45,   45,   60,   47,   62,   93,   15,   37,   61,  125,
   93,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   59,   41,   44,   37,   44,   93,   61,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,  125,   42,
   43,   45,   45,  110,   47,   37,   60,   42,   62,  274,
   42,   43,   47,   45,   41,   47,  274,   60,   41,   62,
   43,   44,   45,   41,   40,   43,   44,   45,   60,   41,
   62,   93,   44,   93,  123,   41,   59,   60,   44,   62,
   41,   59,   60,   44,   62,   61,   41,   59,   60,   44,
   62,   61,   41,   59,   60,   44,   62,   41,   59,   41,
   44,   91,   44,    0,   59,  114,  115,  123,   32,  118,
   93,  123,   59,   59,    0,   93,   40,   59,   59,   59,
  120,   93,  257,  258,  259,  260,  261,   93,  263,  264,
  265,   55,   93,  268,  269,   16,  107,   -1,   93,  274,
   -1,  257,  258,  259,  260,  261,   -1,  263,  264,  265,
   -1,   93,  268,  269,   37,   -1,   -1,   -1,  274,   42,
   43,   -1,   45,   -1,   47,   41,   -1,   48,   44,   -1,
  257,  258,  259,  260,  261,   16,  263,  264,  265,   -1,
   40,  268,  269,   59,   -1,   -1,  110,  274,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  276,  277,  278,
  279,   61,   -1,   -1,   -1,   -1,   -1,   48,  276,  277,
  278,  279,  257,  258,  259,  260,  261,   93,  276,  277,
  278,  279,   -1,    0,   -1,  270,  271,  272,   -1,  274,
   -1,   91,  276,  277,  278,  279,   -1,   -1,   15,   16,
  121,  122,  123,    0,   -1,   -1,  276,  277,  278,  279,
   -1,   37,   -1,   -1,  276,  277,   42,   -1,   15,   16,
   -1,   47,  276,  277,  278,  279,  270,  271,  272,    0,
  274,   48,   29,  276,  277,  278,  279,   -1,   -1,   -1,
  121,  122,  123,   -1,   15,   16,  278,  279,   -1,   -1,
   -1,   48,   -1,  276,  277,  278,  279,   -1,  276,  277,
  278,  279,   59,    0,  276,  277,  278,  279,   40,   -1,
  276,  277,  278,  279,   46,  276,  277,   48,   15,   16,
   77,  276,  277,   -1,   41,   -1,   -1,   44,   -1,   61,
   -1,   -1,   -1,   -1,  276,  277,   -1,  114,  115,   -1,
   -1,  118,   59,   -1,  121,  122,  123,   -1,   -1,   -1,
   -1,   48,   -1,   -1,   -1,   -1,   -1,  114,  115,   91,
   -1,  118,   -1,   -1,  121,  122,  123,   -1,   -1,   -1,
   -1,   10,   11,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,  114,  115,   -1,   -1,  118,   -1,   -1,
  121,  122,  123,   -1,   33,   34,   -1,   -1,   -1,   -1,
   39,   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   52,   -1,   54,   -1,  114,  115,   58,
   -1,  118,   -1,   -1,  121,  122,  123,   66,   67,   68,
   69,   70,   71,   72,   73,   74,   75,   76,   -1,  257,
  258,  259,  260,  261,   -1,  263,  264,  265,   87,  267,
  268,  269,   -1,   -1,   93,   -1,  274,  257,  258,  259,
  260,  261,   -1,  263,  264,  265,   -1,   -1,  268,  269,
   -1,   -1,   -1,   -1,  274,
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

//#line 251 "solver_parser.y"

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
    System.out.println(yyl_return);

    return yyl_return;
  }

  private Type check_type(Expression e1, Expression e2) {
    if (!e1.type.type.equals(e2.type.type)) {
      yyerror("Type mismatch error at line " + (lexer.getLine() + 1) + ":  " + e1.type.type + " != " + e2.type.type);
      return new pType("error");
    }
    else return e1.type;
  }
  
  private Type check_type(Type t1, Expression e2) {
    if (!t1.type.equals(e2.type.type)) {
      yyerror("Type mismatch error at line " + (lexer.getLine() + 1) + ":  " + t1.type + " != " + e2.type.type);
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
    System.err.println("Error: " + error);
    errors++;
  }

  public SolverParser(Reader r, Hashtable symbols)
  {
    lexer = new SolverLexer(r, this);
    this.symbols = symbols;
    errors = 0; //no errors yet
  }
//#line 465 "SolverParser.java"
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
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 25:
//#line 122 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 26:
//#line 124 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 27:
//#line 126 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 128 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 29:
//#line 130 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 30:
//#line 132 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 31:
//#line 134 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 136 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 138 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 140 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 142 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 144 "solver_parser.y"
{  }
break;
case 37:
//#line 145 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 38:
//#line 146 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 39:
//#line 147 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable on line " + lexer.getLine());
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 40:
//#line 155 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 41:
//#line 157 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 42:
//#line 161 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 43:
//#line 162 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 44:
//#line 163 "solver_parser.y"
{ yyval.obj = null; }
break;
case 45:
//#line 166 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 46:
//#line 169 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 171 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 175 "solver_parser.y"
{ yyval.obj = new ListAccess((ID) val_peek(3).obj, (Expression) val_peek(1).obj);
                                         if (!symbols.containsKey(((ID) val_peek(3).obj).toString())) {
                                           yyerror("Undeclared list at line " + lexer.getLine());
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
case 49:
//#line 193 "solver_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                                /*added space, was new Type("list" ...) -> new Type("list " ...)*/
                                              ((ID) val_peek(0).obj).type = new Type("list " + val_peek(1).obj);
						/*This line below is unneccessary as id was already put into the symbol table when it was parsed*/
                                              symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj); }
break;
case 50:
//#line 198 "solver_parser.y"
{ /*Do typechecking*/
						check_type((Type) val_peek(5).obj, (AttrList) val_peek(1));
                                                yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj);
                                              ((ID) val_peek(4).obj).type = new Type("list " + val_peek(5).obj);
                                              symbols.put(((ID) val_peek(4).obj).toString(), val_peek(4).obj); 
                                              }
break;
case 51:
//#line 206 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 52:
//#line 207 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 53:
//#line 208 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 54:
//#line 212 "solver_parser.y"
{ check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                        yyval.obj = new PrimDec((pType) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                        ((Expression) val_peek(2).obj).type = (Type) val_peek(3).obj;
                                        symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj); }
break;
case 55:
//#line 218 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Attr) val_peek(0).obj); }
break;
case 56:
//#line 219 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Attr) val_peek(0).obj); }
break;
case 57:
//#line 223 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 58:
//#line 226 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 59:
//#line 234 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 60:
//#line 235 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 61:
//#line 236 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 62:
//#line 239 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 63:
//#line 241 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 64:
//#line 243 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).sval);
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 65:
//#line 247 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 928 "SolverParser.java"
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
