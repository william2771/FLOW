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
    0,    1,    1,    2,    3,    3,    3,    3,    6,    6,
    6,    6,    5,    5,    5,    8,    8,    4,    4,    4,
    4,    4,    7,    7,    7,    7,    7,    7,   16,   22,
   11,    9,   10,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   17,   17,   17,   17,   21,   21,   21,   20,   14,   14,
   24,   12,   12,   23,   23,   23,   13,   19,   19,   27,
   18,   26,   26,   26,   25,   25,   25,   15,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    2,    1,
    3,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    2,    4,    0,
    8,    7,    7,    3,    4,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    3,    1,    0,    2,    3,    3,
    4,    4,    8,    1,    1,    1,    4,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   72,   73,   74,   65,   66,    0,    0,    0,    0,    0,
   71,    0,    1,    0,    0,    0,    6,   13,   14,   15,
   18,   19,   20,   21,   22,    0,    0,    0,    0,   64,
    0,    0,    0,    0,    0,   77,   76,   75,    0,    0,
   50,   53,    0,    0,    0,   54,    0,    0,    8,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    0,   36,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,   68,    0,   56,    0,    0,    0,    0,    0,    0,
    0,   49,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   45,   46,   48,   29,    0,   61,   30,
    0,   58,    0,    0,    0,    0,   35,   69,    0,   55,
    0,    0,    0,    0,    0,   32,   33,    0,    0,    0,
   10,   16,   17,   23,   24,   25,   26,   27,    0,   63,
    0,   31,    0,   12,    9,   11,
};
final static short yydgoto[] = {                         12,
   13,   14,   15,   16,   17,  129,  130,  131,   18,   19,
   20,   21,   22,   41,   24,   42,   80,   44,   81,   27,
   85,  119,   28,   45,   46,   30,   82,
};
final static short yysindex[] = {                       146,
    0,    0,    0,    0,    0, -265,  -38,  -29, -249,   47,
    0,    0,    0,  294,  294,  -30,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  127,   -3, -241,  -14,    0,
   -4,   47,   47,   12,   52,    0,    0,    0,   47,    6,
    0,    0,   48,  161,  -14,    0,  294,   35,    0,    0,
   47,   47,   47,   -4,    0,   46,   47, -159,    7,   21,
    0, -159,    0,   37,   95,   47,   47,   47,   47,   47,
   47,   47,   47,   47,   47,   47, -159,    0,  382,   48,
  184,    0,   -7,    0,  194, -132,   47,  382,   91,   33,
   39,    0,    0,   47,   59,   59,  382,  382,  382,  382,
  182,  182,   58,    0,    0,    0,    0,   47,    0,    0,
   -4,    0,   48,   69,  294,  294,    0,    0,   63,    0,
   47,  -66,  -48,  276,  -32,    0,    0,   47,  -84,  111,
    0,    0,    0,    0,    0,    0,    0,    0, -159,    0,
   48,    0,  112,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  169,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   35,  -37,  -28,    0,  187,    0,    0,    0,
    0,    0,    0,  202,  -22,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,   31,
    0,    0,    0,    0,    0,    0,    0,   85,  123,    0,
    0,    0,    0,    0,  186,  333,   29,   90,   96,  109,
   68,   73,  -17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  137,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  141,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   89,   42,  110,    0,   77,  105,   19,   22,
    0,   30,   34,  273,  107,  328,  455,  354,   99,  -51,
    0,    0,  183,  376,    0,    0,  136,
};
final static int YYTABLESIZE=583;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   31,   32,   84,   52,   52,   52,   52,   52,   51,   52,
   33,  108,   51,   51,   51,   51,   51,   58,   51,   47,
   34,   52,   52,   47,   52,   47,   47,   47,   50,   74,
   51,   51,   55,   51,   75,   73,   54,   72,   71,   76,
  142,   47,   47,   74,   47,   40,   57,   90,   75,   73,
   39,   72,   70,   76,   71,   52,   48,   74,  126,  120,
  140,   91,   75,   73,   51,   72,   70,   76,   71,   40,
   61,   70,   40,   74,   70,   47,  127,   93,   75,   73,
   70,   72,   71,   76,   74,  109,   40,   40,   48,   75,
   73,   39,   72,   78,   76,   74,   70,   62,   71,   75,
   75,   73,   47,   72,   76,   76,   87,   70,   44,   71,
   44,   44,   44,   43,   11,   43,   43,   43,   70,   60,
   71,   40,   60,   70,   49,   59,   44,   44,   59,   44,
   38,   43,   43,   38,   43,   94,   39,   60,   60,   39,
   60,  112,  132,   59,   59,  133,   59,  132,   38,   37,
  133,  114,   37,  134,   39,  115,   49,  135,  134,  121,
   44,  116,  135,   48,   48,   43,   52,   37,    3,  145,
  146,   60,    1,    2,    3,    4,    5,   59,    6,    7,
    8,   62,   38,  128,   10,  124,    2,   51,   39,   11,
    1,    2,    3,    4,    5,   67,    6,    7,    8,   28,
   52,   37,   10,  122,  123,  143,   77,   11,    1,    2,
    3,    4,    5,   58,    6,    7,    8,   53,   74,  125,
   10,   51,   65,   75,  107,   11,   42,  108,   76,   42,
  137,   49,   49,  144,  110,  137,   86,  111,   52,   52,
   52,   52,   57,  118,   42,   57,    0,   51,   51,   51,
   51,   53,    1,    2,    3,    4,    5,    0,   47,   47,
   47,   47,    1,    2,    3,    4,    5,   35,   66,   67,
   68,   69,   23,    0,    0,   36,   37,   38,   42,   11,
    0,    0,   66,   67,   68,   69,   23,   23,    0,    0,
    0,    0,    0,   86,    0,    0,   66,   67,   68,   69,
    0,    0,    0,    0,   40,   40,  139,    0,   35,    0,
    0,  139,   66,   67,   68,   69,   36,   37,   38,   23,
   11,    0,    0,   66,   67,   68,   69,   25,    0,    0,
    0,    0,    0,    0,    0,    0,   68,   69,    0,    0,
    0,   25,   25,   44,   44,   44,   44,    0,   43,   43,
   43,   43,    0,   26,   60,   60,   60,   60,    0,    0,
   59,   59,   59,   59,    0,   38,   38,   26,   26,    0,
    0,   39,   39,   41,   25,   29,   41,    0,    0,    0,
    0,   56,    0,    0,   37,   37,    0,   23,   23,   29,
   29,   41,    0,    0,   23,   23,  136,    0,    0,    0,
   26,  136,    1,    2,    3,    4,    5,    0,    6,    7,
    8,   89,    9,    0,   10,   92,    0,    0,   74,   11,
    0,    0,   29,   75,   73,   41,   72,    0,   76,    0,
  106,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   25,   25,    0,    0,    0,    0,    0,   25,
   25,  138,    0,    0,    0,    0,  138,    0,    0,    0,
    0,    0,    0,    0,   43,    0,    0,    0,   26,   26,
    0,    0,    0,    0,    0,   26,   26,   26,    0,    0,
    0,    0,   26,    0,    0,    0,   59,   60,    0,    0,
   29,   29,   56,   63,   64,    0,    0,   29,   29,   29,
    0,    0,    0,    0,   29,   79,    0,   83,    0,    0,
    0,   88,    0,    0,    0,    0,    0,    0,    0,    0,
   95,   96,   97,   98,   99,  100,  101,  102,  103,  104,
  105,    0,    1,    2,    3,    4,    5,    0,    6,    7,
    8,  113,    0,  128,   10,    0,    0,    0,  117,   11,
    1,    2,    3,    4,    5,    0,    6,    7,    8,    0,
    0,    0,   10,    0,    0,    0,    0,   11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   40,   54,   41,   42,   43,   44,   45,   37,   47,
   40,   44,   41,   42,   43,   44,   45,   40,   47,   37,
  270,   59,   60,   41,   62,   43,   44,   45,   59,   37,
   59,   60,  274,   62,   42,   43,   40,   45,   61,   47,
  125,   59,   60,   37,   62,   40,   61,   41,   42,   43,
   45,   45,   60,   47,   62,   93,   15,   37,  125,  111,
   93,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   59,   41,   44,   37,   44,   93,  125,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,   47,   42,
   43,   45,   45,   59,   47,   37,   60,   46,   62,   42,
   42,   43,   14,   45,   47,   47,   61,   60,   41,   62,
   43,   44,   45,   41,  274,   43,   44,   45,   60,   41,
   62,   93,   44,   93,   15,   41,   59,   60,   44,   62,
   41,   59,   60,   44,   62,   41,   41,   59,   60,   44,
   62,  274,  124,   59,   60,  124,   62,  129,   59,   41,
  129,   61,   44,  124,   59,  123,   47,  124,  129,   91,
   93,  123,  129,  122,  123,   93,   40,   59,    0,   59,
   59,   93,  257,  258,  259,  260,  261,   93,  263,  264,
  265,   59,   93,  268,  269,  123,    0,   61,   93,  274,
  257,  258,  259,  260,  261,   59,  263,  264,  265,   59,
   40,   93,  269,  115,  116,  129,   46,  274,  257,  258,
  259,  260,  261,   31,  263,  264,  265,   91,   37,  121,
  269,   61,   40,   42,   41,  274,   41,   44,   47,   44,
  124,  122,  123,  129,   41,  129,   54,   44,  276,  277,
  278,  279,   41,  108,   59,   44,   -1,  276,  277,  278,
  279,   91,  257,  258,  259,  260,  261,   -1,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  262,  276,  277,
  278,  279,    0,   -1,   -1,  270,  271,  272,   93,  274,
   -1,   -1,  276,  277,  278,  279,   14,   15,   -1,   -1,
   -1,   -1,   -1,  111,   -1,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  276,  277,  124,   -1,  262,   -1,
   -1,  129,  276,  277,  278,  279,  270,  271,  272,   47,
  274,   -1,   -1,  276,  277,  278,  279,    0,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   14,   15,  276,  277,  278,  279,   -1,  276,  277,
  278,  279,   -1,    0,  276,  277,  278,  279,   -1,   -1,
  276,  277,  278,  279,   -1,  276,  277,   14,   15,   -1,
   -1,  276,  277,   41,   47,    0,   44,   -1,   -1,   -1,
   -1,   28,   -1,   -1,  276,  277,   -1,  115,  116,   14,
   15,   59,   -1,   -1,  122,  123,  124,   -1,   -1,   -1,
   47,  129,  257,  258,  259,  260,  261,   -1,  263,  264,
  265,   58,  267,   -1,  269,   62,   -1,   -1,   37,  274,
   -1,   -1,   47,   42,   43,   93,   45,   -1,   47,   -1,
   77,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  115,  116,   -1,   -1,   -1,   -1,   -1,  122,
  123,  124,   -1,   -1,   -1,   -1,  129,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   10,   -1,   -1,   -1,  115,  116,
   -1,   -1,   -1,   -1,   -1,  122,  123,  124,   -1,   -1,
   -1,   -1,  129,   -1,   -1,   -1,   32,   33,   -1,   -1,
  115,  116,  139,   39,   40,   -1,   -1,  122,  123,  124,
   -1,   -1,   -1,   -1,  129,   51,   -1,   53,   -1,   -1,
   -1,   57,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   66,   67,   68,   69,   70,   71,   72,   73,   74,   75,
   76,   -1,  257,  258,  259,  260,  261,   -1,  263,  264,
  265,   87,   -1,  268,  269,   -1,   -1,   -1,   94,  274,
  257,  258,  259,  260,  261,   -1,  263,  264,  265,   -1,
   -1,   -1,  269,   -1,   -1,   -1,   -1,  274,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  128,
};
}
final static short YYFINAL=12;
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
"func_stmt_list : func_stmt ';'",
"func_stmt_list : func_block_stmt",
"func_stmt_list : func_stmt_list func_stmt ';'",
"func_stmt_list : func_stmt_list func_block_stmt",
"block_stmt : while_stmt",
"block_stmt : if_stmt",
"block_stmt : func_dec",
"func_block_stmt : while_stmt",
"func_block_stmt : if_stmt",
"solver_stmt : list_dec",
"solver_stmt : prim_dec",
"solver_stmt : assignment",
"solver_stmt : print_stmt",
"solver_stmt : func_call",
"func_stmt : list_dec",
"func_stmt : prim_dec",
"func_stmt : assignment",
"func_stmt : print_stmt",
"func_stmt : func_call",
"func_stmt : RET expr",
"func_call : id '(' attr_list ')'",
"$$1 :",
"func_dec : param '(' param_list ')' $$1 '{' func_stmt_list '}'",
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

//#line 390 "solver_parser.y"

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
//#line 517 "SolverParser.java"
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
case 9:
//#line 91 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 10:
//#line 92 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(0).obj); }
break;
case 11:
//#line 93 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 12:
//#line 94 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj); 
   if (((StatementNode) val_peek(0).obj).type == null){
     ((FuncSequenceNode) yyval.obj).type = ((FuncSequenceNode) val_peek(1).obj).type;
   }
   else if(((FuncSequenceNode) val_peek(1).obj).type == null){
     ((FuncSequenceNode) yyval.obj).type = ((FuncSequenceNode) val_peek(1).obj).type;   
   }
   else if (((FuncSequenceNode) val_peek(1).obj).type != ((StatementNode) val_peek(0).obj).type){
     yyerror("You are returning the wrong type.");
   }
   else{
     ((FuncSequenceNode) yyval.obj).type = ((StatementNode) val_peek(0).obj).type;   
   }
 }
break;
case 15:
//#line 112 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 22:
//#line 123 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 27:
//#line 130 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 28:
//#line 131 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type;}
break;
case 29:
//#line 134 "solver_parser.y"
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
case 30:
//#line 150 "solver_parser.y"
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
case 31:
//#line 179 "solver_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(7).obj, (ParamList) val_peek(5).obj, (SequenceNode) val_peek(2).obj);
                                                                if (!((Param) val_peek(7).obj).type.type.equals(((FuncSequenceNode) val_peek(2).obj).type.type))
                                                                {
                                                                    yyerror("You are returning the wrong type.");
                                                                }
                                                                /*Restore the old symbol table*/
                                                                symbols = old;
                                                                }
break;
case 32:
//#line 190 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 33:
//#line 193 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj ); }
break;
case 34:
//#line 196 "solver_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 35:
//#line 197 "solver_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (pType) val_peek(2).obj; }
break;
case 36:
//#line 199 "solver_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 37:
//#line 204 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 38:
//#line 209 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 39:
//#line 214 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 40:
//#line 219 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 41:
//#line 224 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 42:
//#line 226 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 43:
//#line 228 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 44:
//#line 230 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 45:
//#line 235 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 46:
//#line 240 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 245 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 250 "solver_parser.y"
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
case 49:
//#line 275 "solver_parser.y"
{ yyval.obj = new Dot(new ID("Graph"), (ID) val_peek(0).obj);
                                 if (symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   ((Expression) yyval.obj).type = ((ID) symbols.get(((ID) val_peek(0).obj).toString())).type;
                                 }
                                 else {
                                   yyerror("Graph attribute '" + ((ID) val_peek(0).obj).toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 50:
//#line 283 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 51:
//#line 284 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 52:
//#line 285 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 53:
//#line 293 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 54:
//#line 294 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 55:
//#line 298 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 56:
//#line 299 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 57:
//#line 300 "solver_parser.y"
{ yyval.obj = null; }
break;
case 58:
//#line 303 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 59:
//#line 306 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 60:
//#line 308 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 61:
//#line 312 "solver_parser.y"
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
case 62:
//#line 330 "solver_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                              /*added space, was new Type("list" ...) -> new Type("list " ...)*/
                                              ((ID) val_peek(0).obj).type = new Type("list " + val_peek(1).obj);
						/*This line below is unneccessary as id was already put into the symbol table when it was parsed*/
                                              symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj); }
break;
case 63:
//#line 336 "solver_parser.y"
{ /*Do typechecking*/
						check_type((Type) val_peek(5).obj, (AttrList) val_peek(1).obj);
                                                yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj);

                                              ((ID) val_peek(4).obj).type = new Type("list " + val_peek(5).obj);
                                              symbols.put(((ID) val_peek(4).obj).toString(), val_peek(4).obj); 
                                              }
break;
case 64:
//#line 345 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 65:
//#line 346 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 66:
//#line 347 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 67:
//#line 351 "solver_parser.y"
{ check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                         yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         ((Expression) val_peek(2).obj).type = (Type) val_peek(3).obj;
                                         symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj); }
break;
case 68:
//#line 357 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Attr) val_peek(0).obj); }
break;
case 69:
//#line 358 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Attr) val_peek(0).obj); }
break;
case 70:
//#line 362 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 71:
//#line 365 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 72:
//#line 373 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 73:
//#line 374 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 74:
//#line 375 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 75:
//#line 378 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 76:
//#line 380 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 77:
//#line 382 "solver_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 78:
//#line 386 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1125 "SolverParser.java"
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
