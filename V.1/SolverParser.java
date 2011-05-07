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
    4,    7,    7,    7,    7,    7,   17,   23,   11,    9,
   12,   10,   13,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   18,   22,   22,   22,   21,   15,
   15,   25,   27,   24,   24,   24,   24,   29,   14,   14,
   20,   20,   19,   28,   28,   28,   26,   26,   26,   16,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    2,    1,
    3,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    2,    4,    0,    8,    7,
    7,    7,    7,    3,    4,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    3,    1,    0,    2,    3,
    3,    4,    3,    1,    1,    1,    1,    3,    2,    4,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   74,   75,   76,   65,   66,    0,    0,    0,    0,    0,
   73,    0,    1,    0,    0,    0,    6,   13,   14,   15,
   18,   19,   20,   21,    0,    0,    0,    0,   64,   67,
    0,    0,    0,    0,    0,   79,   78,   77,    0,    0,
    0,   50,   53,    0,    0,    0,   54,   55,    0,    0,
    8,    5,    0,    0,    0,    0,    0,    0,   68,    0,
    0,    4,    0,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    7,    0,    0,    0,   57,    0,    0,    0,    0,    0,
    0,   49,   34,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   45,   46,   48,   27,   62,
   28,    0,   59,    0,    0,    0,   35,    0,    0,   56,
    0,    0,    0,   30,   32,    0,    0,    0,    0,    0,
   10,   16,   17,   22,   23,   24,   25,    0,    0,    0,
    0,   29,    0,   12,    9,    0,    0,    0,   11,    0,
    0,    0,    0,    0,    0,   31,   33,
};
final static short yydgoto[] = {                         12,
   13,   14,   15,   16,   17,  129,  130,  131,   18,   19,
   20,  132,  133,   21,   42,   23,   43,   67,   45,   68,
   26,   86,  119,   27,   46,   47,   48,   29,   30,
};
final static short yysindex[] = {                       372,
    0,    0,    0,    0,    0, -265,  -11,   -1, -237,  145,
    0,    0,    0,  444,  444,  -18,    0,    0,    0,    0,
    0,    0,    0,    0,  141,   47, -213,   31,    0,    0,
   71,  145,  145,   35,   52,    0,    0,    0,  145,  139,
  145,    0,    0,   70,  130,   31,    0,    0,  444,   46,
    0,    0,  145,  145,  145,   71,   50,  145,    0,    7,
   21,    0, -213,    0,   37,   73,   70,  -42,  145,  145,
  145,  145,  145,  145,  145,  145,  145,  145,  145, -213,
    0,  181,  148,   -7,    0,  160, -213,  145,  181,   -3,
    2,    0,    0,  145,  145,    0,   81,   81,  181,  181,
  181,  181,  221,  221,   30,    0,    0,    0,    0,    0,
    0,   71,    0,   70,  444,  444,    0,   70,    4,    0,
  331,  349,  426,    0,    0,   76,   89,  145,  225,   99,
    0,    0,    0,    0,    0,    0,    0, -213,  145,  145,
   70,    0,  105,    0,    0,   50,   48,   59,    0,   51,
   63,  426,  426,  257,  313,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  187,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  140,  -37,  -28,    0,    0,  203,    0,
    0,    0,    0,    0,    0,  171,  -22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  103,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  101,    0,    0,    0,    0,    0,    0,  107,    0,
    0,    0,    0,    0,    0,    0,  134,  154,   16,   29,
  112,  118,   90,   95,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,  157,    0,    0,    0,  124,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  170,    0,    0,    0,    0,  174,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   57,   88,  131, -141, -108,   53,    0,    0,
    0,    0,    0,   91,  390,  102,  412,  587,  343,  165,
  -53,    0,    0,  166,  431,    0,    0,    0,    0,
};
final static int YYTABLESIZE=727;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   31,   95,   85,   52,   52,   52,   52,   52,   51,   52,
  154,  155,   51,   51,   51,   51,   51,   59,   51,   47,
  143,   52,   52,   47,   52,   47,   47,   47,   32,   77,
   51,   51,   34,   51,   78,   76,   69,   75,   33,   79,
   52,   47,   47,   77,   47,  143,  143,   90,   78,   76,
   96,   75,   73,   79,   74,   52,   40,   77,  120,   40,
   11,   91,   78,   76,   51,   75,   73,   79,   74,   38,
   49,   78,   38,   77,   40,   47,   79,   93,   78,   76,
   73,   75,   74,   79,   77,  110,   56,   38,  150,   78,
   76,   58,   75,   62,   79,   77,   73,   63,   74,  151,
   78,   76,   50,   75,   81,   79,   77,   73,   40,   74,
   88,   78,   76,   94,   75,  139,   79,   77,   73,  115,
   74,   38,   78,   76,  116,   75,  123,   79,  140,   73,
   44,   74,   44,   44,   44,   43,   50,   43,   43,   43,
   73,   61,   74,   71,   61,   51,   71,   60,   44,   44,
   60,   44,   39,   43,   43,   39,   43,  145,   37,   61,
   61,   37,   61,  149,   72,   60,   60,   72,   60,   54,
   39,  121,  122,  152,   42,   80,   37,   42,   40,   51,
   54,  144,   44,   39,   40,  153,    3,   43,  109,   39,
   53,   95,   42,   61,   41,   71,   59,   41,   80,   60,
  111,   53,    2,  112,   39,   66,  144,  144,   50,   50,
   37,   58,   41,  134,   58,   70,   72,   77,   83,  134,
   55,   87,   78,   76,  136,   75,   42,   79,   26,   41,
  136,   55,   69,    0,    0,   41,    0,    0,   52,   52,
   52,   52,  134,  134,  134,  134,   41,   51,   51,   51,
   51,   51,   51,  136,  136,  136,  136,   77,   47,   47,
   47,   47,   78,    0,    0,    0,    0,   79,   69,   70,
   71,   72,    0,    0,    0,    0,    0,   87,    0,    0,
    0,    0,   69,   70,   71,   72,    0,    0,  138,    0,
    0,   40,   40,    0,  138,    0,   69,   70,   71,   72,
    0,    0,    0,    0,   38,   38,    0,    0,    0,    0,
    0,    0,   69,   70,   71,   72,    0,  138,  138,  138,
  138,    0,    0,   69,   70,   71,   72,    1,    2,    3,
    4,    5,    0,    6,   69,   70,   71,   72,    0,    0,
    0,    0,   25,    0,    0,   69,   70,   71,   72,  142,
    0,    0,    0,    0,    0,    0,   25,   25,   71,   72,
    0,    0,    0,    0,    0,   44,   44,   44,   44,   57,
   43,   43,   43,   43,    0,    0,   61,   61,   61,   61,
    0,  156,   60,   60,   60,   60,    0,   39,   39,   22,
    0,   25,    0,   37,   37,    1,    2,    3,    4,    5,
   35,    6,    0,   22,   22,   92,   35,    0,   36,   37,
   38,   24,   11,    0,   36,   37,   38,    0,   11,    0,
    0,    0,  108,    0,    0,   24,   24,    0,    0,  113,
   28,    0,    0,    0,    0,    0,    0,  157,   22,    0,
    0,    0,    0,    0,   28,   28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  124,    0,   25,   25,    0,
   24,    0,    0,   25,   25,   25,    0,    0,    0,    0,
    0,   25,    0,  125,    0,    0,    0,    0,    0,   28,
  146,    1,    2,    3,    4,    5,    0,    6,  126,  127,
    0,    0,  128,   10,   25,   25,   25,   25,   11,    0,
    0,    0,    0,    0,   22,   22,    0,    0,    0,    0,
   22,   22,  135,    1,    2,    3,    4,    5,  135,    6,
  126,  127,    0,    0,  128,   10,   24,   24,    0,    0,
   11,    0,   24,   24,  137,    0,    0,    0,    0,    0,
  137,  135,  135,  135,  135,   28,   28,    0,    0,    0,
    0,   28,   28,   28,    0,    0,    0,    0,    0,   28,
    0,    0,    0,  137,  137,  137,  137,    0,    0,    1,
    2,    3,    4,    5,    0,    6,  126,  127,    0,    0,
  128,   10,   28,   28,   28,   28,   11,    1,    2,    3,
    4,    5,    0,    6,    7,    8,   44,    0,    0,   10,
    0,    0,    0,    0,   11,    1,    2,    3,    4,    5,
    0,    6,    7,    8,    0,    0,    0,   10,   60,   61,
    0,    0,   11,    0,    0,   64,   65,    0,    1,    2,
    3,    4,    5,    0,    6,    7,    8,    0,    9,   82,
   10,   84,    0,    0,   89,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   97,   98,   99,  100,  101,
  102,  103,  104,  105,  106,  107,    0,    0,    0,    0,
    0,    0,    0,    0,  114,    0,    0,    0,    0,    0,
  117,  118,    1,    2,    3,    4,    5,    0,    6,  126,
  127,    0,    0,  128,   10,    0,    0,    0,    0,   11,
    1,    2,    3,    4,    5,    0,    6,    7,    8,    0,
    0,    0,   10,    0,  141,    0,    0,   11,    0,    0,
    0,    0,    0,    0,    0,  147,  148,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   44,   56,   41,   42,   43,   44,   45,   37,   47,
  152,  153,   41,   42,   43,   44,   45,   40,   47,   37,
  129,   59,   60,   41,   62,   43,   44,   45,   40,   37,
   59,   60,  270,   62,   42,   43,   59,   45,   40,   47,
   59,   59,   60,   37,   62,  154,  155,   41,   42,   43,
   93,   45,   60,   47,   62,   93,   41,   37,  112,   44,
  274,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   14,   42,   44,   37,   59,   93,   47,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,   41,   42,
   43,   61,   45,   59,   47,   37,   60,   46,   62,   41,
   42,   43,   15,   45,   59,   47,   37,   60,   93,   62,
   61,   42,   43,   41,   45,   40,   47,   37,   60,  123,
   62,   93,   42,   43,  123,   45,  123,   47,   40,   60,
   41,   62,   43,   44,   45,   41,   49,   43,   44,   45,
   60,   41,   62,   41,   44,   15,   44,   41,   59,   60,
   44,   62,   41,   59,   60,   44,   62,   59,   41,   59,
   60,   44,   62,   59,   41,   59,   60,   44,   62,   40,
   59,  115,  116,  123,   41,   46,   59,   44,   40,   49,
   40,  129,   93,   45,   40,  123,    0,   93,   41,   45,
   61,   44,   59,   93,   41,   93,   31,   44,   59,   93,
   41,   61,    0,   44,   93,   40,  154,  155,  121,  122,
   93,   41,   59,  123,   44,   59,   93,   37,   54,  129,
   91,   56,   42,   43,  123,   45,   93,   47,   59,   91,
  129,   91,   59,   -1,   -1,   91,   -1,   -1,  276,  277,
  278,  279,  152,  153,  154,  155,   93,  276,  277,  278,
  279,  121,  122,  152,  153,  154,  155,   37,  276,  277,
  278,  279,   42,   -1,   -1,   -1,   -1,   47,  276,  277,
  278,  279,   -1,   -1,   -1,   -1,   -1,  112,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,  123,   -1,
   -1,  276,  277,   -1,  129,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,   -1,  152,  153,  154,
  155,   -1,   -1,  276,  277,  278,  279,  257,  258,  259,
  260,  261,   -1,  263,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,    0,   -1,   -1,  276,  277,  278,  279,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   14,   15,  278,  279,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,   27,
  276,  277,  278,  279,   -1,   -1,  276,  277,  278,  279,
   -1,  125,  276,  277,  278,  279,   -1,  276,  277,    0,
   -1,   49,   -1,  276,  277,  257,  258,  259,  260,  261,
  262,  263,   -1,   14,   15,   63,  262,   -1,  270,  271,
  272,    0,  274,   -1,  270,  271,  272,   -1,  274,   -1,
   -1,   -1,   80,   -1,   -1,   14,   15,   -1,   -1,   87,
    0,   -1,   -1,   -1,   -1,   -1,   -1,  125,   49,   -1,
   -1,   -1,   -1,   -1,   14,   15,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  125,   -1,  115,  116,   -1,
   49,   -1,   -1,  121,  122,  123,   -1,   -1,   -1,   -1,
   -1,  129,   -1,  125,   -1,   -1,   -1,   -1,   -1,   49,
  138,  257,  258,  259,  260,  261,   -1,  263,  264,  265,
   -1,   -1,  268,  269,  152,  153,  154,  155,  274,   -1,
   -1,   -1,   -1,   -1,  115,  116,   -1,   -1,   -1,   -1,
  121,  122,  123,  257,  258,  259,  260,  261,  129,  263,
  264,  265,   -1,   -1,  268,  269,  115,  116,   -1,   -1,
  274,   -1,  121,  122,  123,   -1,   -1,   -1,   -1,   -1,
  129,  152,  153,  154,  155,  115,  116,   -1,   -1,   -1,
   -1,  121,  122,  123,   -1,   -1,   -1,   -1,   -1,  129,
   -1,   -1,   -1,  152,  153,  154,  155,   -1,   -1,  257,
  258,  259,  260,  261,   -1,  263,  264,  265,   -1,   -1,
  268,  269,  152,  153,  154,  155,  274,  257,  258,  259,
  260,  261,   -1,  263,  264,  265,   10,   -1,   -1,  269,
   -1,   -1,   -1,   -1,  274,  257,  258,  259,  260,  261,
   -1,  263,  264,  265,   -1,   -1,   -1,  269,   32,   33,
   -1,   -1,  274,   -1,   -1,   39,   40,   -1,  257,  258,
  259,  260,  261,   -1,  263,  264,  265,   -1,  267,   53,
  269,   55,   -1,   -1,   58,  274,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   69,   70,   71,   72,   73,
   74,   75,   76,   77,   78,   79,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   88,   -1,   -1,   -1,   -1,   -1,
   94,   95,  257,  258,  259,  260,  261,   -1,  263,  264,
  265,   -1,   -1,  268,  269,   -1,   -1,   -1,   -1,  274,
  257,  258,  259,  260,  261,   -1,  263,  264,  265,   -1,
   -1,   -1,  269,   -1,  128,   -1,   -1,  274,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  139,  140,
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
"func_block_stmt : func_while_stmt",
"func_block_stmt : func_if_stmt",
"solver_stmt : prim_dec",
"solver_stmt : assignment",
"solver_stmt : print_stmt",
"solver_stmt : func_call",
"func_stmt : prim_dec",
"func_stmt : assignment",
"func_stmt : print_stmt",
"func_stmt : func_call",
"func_stmt : RET expr",
"func_call : id '(' attr_list ')'",
"$$1 :",
"func_dec : param '(' param_list ')' $$1 '{' func_stmt_list '}'",
"while_stmt : WHILE '(' expr ')' '{' solver_stmt_list '}'",
"func_while_stmt : WHILE '(' expr ')' '{' func_stmt_list '}'",
"if_stmt : IF '(' expr ')' '{' solver_stmt_list '}'",
"func_if_stmt : IF '(' expr ')' '{' func_stmt_list '}'",
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
"expr : list_lit",
"param_list : param_list ',' param",
"param_list : param",
"param_list :",
"param : type id",
"assignment : access '=' expr",
"assignment : id '=' expr",
"access : id '[' expr ']'",
"list_lit : '[' attr_list ']'",
"type : ptype",
"type : NODE_T",
"type : ARC_T",
"type : list_type",
"list_type : LIST_T OF type",
"prim_dec : type id",
"prim_dec : type id '=' expr",
"attr_list : expr",
"attr_list : attr_list ',' expr",
"id : ID",
"ptype : INT_T",
"ptype : FLT_T",
"ptype : STR_T",
"pvalue : INT",
"pvalue : FLT",
"pvalue : STR",
"print_stmt : PRINT expr",
};

//#line 443 "solver_parser.y"

  private SolverLexer lexer;
  private Hashtable symbols;
  //We need another Hashtable for temporary storage
  private Hashtable old;
  private ArrayList<String> labels;
  private ArrayList<String> functions;
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
    ArrayList<Expression> attrs = e2.toArrayList();
    Type ret;
    for(Expression attr : attrs) {
        //check_type(type t1, expression e2) will put an error into yyerror
        ret = check_type(t1, attr);
        if(ret.type.equals("error")) {
            return ret;
        }
    }
    return t1;
  }  

  private Type check_type(AttrList e2) {
    ArrayList<Expression> attrs = e2.toArrayList();
    Type t1 = attrs.get(0).type;
    Type ret;
    for(Expression attr : attrs) {
        //check_type(type t1, expression e2) will put an error into yyerror
        ret = check_type(t1, attr);
        if(ret.type.equals("error")) {
            return ret;
        }
    }
    return t1;
  }  
  
  private Type check_type(AttrList attrs, ArrayList<Param> params) {
    ArrayList<Expression> attrslist = attrs.toArrayList();
    if(attrslist.size() != params.size()) {
        yyerror("Expected " + params.size() + " args, got " + attrslist.size());
        return new pType("error");
    }
    for(int i=0; i<params.size(); i++) {
        String paramType = params.get(i).type.type;
        String attrType = attrslist.get(i).type.type;
        if(!paramType.equals(attrType)) {
            yyerror("Expected type " + paramType + " got " + attrType);
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
    functions = new ArrayList<String>();
  }
//#line 566 "SolverParser.java"
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
{ yyval.sval = "import java.util.*;\nimport flow.structure.*;\n\npublic class Solver {\npublic static void main(String[] args) {\ngraph = new Graph();\n" + val_peek(0).obj.toString() + "}\nprivate static Graph graph;\n";
                                    for(String string : functions) {
                                        yyval.sval += string;
                                    } 
                                    yyval.sval += "}";
                                     /*if (errors == 0) { //only create output java file if there are no syntax errors*/
                                       try {
                                         FileWriter graph_file = new FileWriter(new File("Solver.java"));
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
//#line 72 "solver_parser.y"
{ yyerror("The first statement in the file must be a typelink.");
                                     System.out.println("\n" + errors + " errors\n"); }
break;
case 4:
//#line 76 "solver_parser.y"
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
//#line 89 "solver_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 6:
//#line 90 "solver_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(0).obj); }
break;
case 7:
//#line 91 "solver_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 8:
//#line 92 "solver_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj); }
break;
case 9:
//#line 97 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(1).obj);
                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType; }
break;
case 10:
//#line 100 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(0).obj);


                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(0).obj).retType; }
break;
case 11:
//#line 104 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj);
                                           if (((StatementNode) val_peek(1).obj).retType == null) {
                                            /*If func_stmt has type null, keep the statement list's current type*/
                                             ((FuncSequenceNode) yyval.obj).retType = ((FuncSequenceNode) val_peek(2).obj).retType;
                                           }
                                           else if(((FuncSequenceNode) val_peek(2).obj).retType == null) {
                                            /*If func_stmt_list has no type, take the func_stmt type*/
                                             ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType;   
                                           }
                                           else if (((FuncSequenceNode) val_peek(2).obj).retType != ((StatementNode) val_peek(1).obj).retType){
                                             yyerror("You are returning the wrong type.1" + ((FuncSequenceNode) val_peek(2).obj).retType + ((StatementNode) val_peek(1).obj).retType );
                                           }
                                           else{
                                             ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType;   
                                           } }
break;
case 12:
//#line 120 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj);
                                           if (((StatementNode) val_peek(0).obj).retType == null) {
                                             ((FuncSequenceNode) yyval.obj).retType = ((FuncSequenceNode) val_peek(1).obj).retType;
                                           }
                                           else if(((FuncSequenceNode) val_peek(1).obj).retType == null) {
                                             ((FuncSequenceNode) yyval.obj).retType = ((FuncSequenceNode) val_peek(1).obj).retType;   
                                           }
                                           else if (((FuncSequenceNode) val_peek(1).obj).retType != ((StatementNode) val_peek(0).obj).retType){
                                             yyerror("You are returning the wrong type.2" + ((FuncSequenceNode) val_peek(1).obj).retType + ((StatementNode) val_peek(0).obj).retType );
                                           }
                                           else{
                                             ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(0).obj).retType;   
                                           } }
break;
case 14:
//#line 136 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 15:
//#line 137 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; functions.add(((FunctionNode)val_peek(0).obj).realToString()); }
break;
case 17:
//#line 141 "solver_parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 21:
//#line 147 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 25:
//#line 153 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 26:
//#line 154 "solver_parser.y"
{ yyval.obj = new ReturnNode((Expression) val_peek(0).obj); 
                                      ((ReturnNode) yyval.obj).retType = ((Expression) val_peek(0).obj).type;}
break;
case 27:
//#line 158 "solver_parser.y"
{ /*Make sure this function was previously declared*/
                                                                /*try {*/
                                                                    ID function_name = (ID) symbols.get(val_peek(3).obj.toString());
                                                                    fType functionType = (fType) function_name.type;
                                                                    /*Check attr_list against the parameter types*/
                                                                    check_type((AttrList)val_peek(1).obj, functionType.paramTypes);
                                                                    yyval.obj = new FunctionCall(function_name, (AttrList) val_peek(1).obj);
                                                                    ((Expression) yyval.obj).type = function_name.type; 
                                                                /*}*/
                                                                /*catch(Exception e) {*/
                                                                  /*  yyerror($1.obj.toString() + " not found, or not callable.");                                                                    */
                                                                /*}*/
                                                               }
break;
case 28:
//#line 174 "solver_parser.y"
{/*At this point, we know that we are going to end up inside a function body, */
            Param param = (Param) val_peek(3).obj;
            ID function_id = param.id;
            /*Check that this function name was not previously used by something else*/
            if(symbols.containsKey(function_id.toString())) {
                yyerror(function_id.toString() + " already declared. Cannot reuse as function name");
            }
            Type ret_type = param.type;
            ParamList params = (ParamList) val_peek(1).obj;
            /*Add the id to the symbol table*/
            function_id.type = new fType(ret_type.type, params);
            symbols.put(function_id.toString(), function_id);
            /*so we'll save the current symbol table and start a new on for the function*/
            old = symbols;
            /*Create a new symbol table (clone because we want access to higher scoped ids too)*/
            symbols = (Hashtable) old.clone();
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
case 29:
//#line 203 "solver_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(7).obj, (ParamList) val_peek(5).obj, (FuncSequenceNode) val_peek(1).obj); 
                                       if (!((Param) val_peek(7).obj).id.type.type.equals(((FuncSequenceNode) val_peek(1).obj).retType.type)) {
                                         yyerror("Function " + ((Param) val_peek(7).obj).id.toString() + " returns the wrong type.");
                                       } 
                                       /*Restore the old symbol table*/
                                       symbols = old; }
break;
case 30:
//#line 212 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 31:
//#line 215 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 32:
//#line 218 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 33:
//#line 221 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj);}
break;
case 34:
//#line 224 "solver_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 35:
//#line 225 "solver_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (Type) val_peek(2).obj; }
break;
case 36:
//#line 227 "solver_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 37:
//#line 232 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 38:
//#line 237 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 39:
//#line 242 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 40:
//#line 247 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 41:
//#line 252 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 42:
//#line 254 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 43:
//#line 256 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 44:
//#line 258 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 45:
//#line 263 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 46:
//#line 268 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 273 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 278 "solver_parser.y"
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
				 else if (((Expression) val_peek(2).obj).type.type.equals("String")){
				   if (val_peek(0).obj.toString().equals("length")){
				     yyval.obj = new StrDot((ID) val_peek(2).obj, val_peek(0).obj.toString());
				     ((Expression) yyval.obj).type = new pType("int");
				   }

				 }
                                 else {
                                   yyerror("Dot operator applied to invalid type: " + ((ID) val_peek(2).obj).toString() + " is of type " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 49:
//#line 315 "solver_parser.y"
{ yyval.obj = new Dot(new ID("graph"), val_peek(0).obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey(val_peek(0).obj.toString())) {
                                   ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("labels")).get(val_peek(0).obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 50:
//#line 323 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 51:
//#line 324 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 52:
//#line 325 "solver_parser.y"
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
//#line 333 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 54:
//#line 334 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 55:
//#line 335 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 56:
//#line 338 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 57:
//#line 339 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 58:
//#line 340 "solver_parser.y"
{ yyval.obj = null; }
break;
case 59:
//#line 343 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 60:
//#line 346 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 61:
//#line 348 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 62:
//#line 352 "solver_parser.y"
{ yyval.obj = new ListAccess((ID) val_peek(3).obj, (Expression) val_peek(1).obj);
                                         if (!symbols.containsKey(((ID) val_peek(3).obj).toString())) {
                                           yyerror("Undeclared list '" + ((ID) val_peek(3).obj).toString() + "'");
                                           ((Expression) yyval.obj).type = new pType("error");
                                         }
                                         else if (!((Expression) val_peek(3).obj).type.type.substring(0,4).equals("list")) {
                                           yyerror("Only Lists can be indexed. " + ((Expression) val_peek(3).obj).type.type);
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
case 63:
//#line 370 "solver_parser.y"
{ yyval.obj = new ListLit((AttrList) val_peek(1).obj);
                                              ((Expression) yyval.obj).type = new Type("list " + check_type((AttrList) val_peek(1).obj).toString()); }
break;
case 64:
//#line 384 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 65:
//#line 385 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 66:
//#line 386 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 67:
//#line 387 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 68:
//#line 390 "solver_parser.y"
{ yyval.obj = new Type("list " + val_peek(0).obj.toString()); }
break;
case 69:
//#line 393 "solver_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                         if (symbols.containsKey(val_peek(0).obj.toString())) {
                                           yyerror("Variable " + val_peek(0).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(0).obj).type = (Type) val_peek(1).obj;
                                           symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj);
                                         } }
break;
case 70:
//#line 401 "solver_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         if (symbols.containsKey(val_peek(2).obj.toString())) {
                                           yyerror("Variable " + val_peek(2).obj.toString() + " already declared");
                                         }
                                         else {
                                           System.out.println(val_peek(3).obj.toString() + " type");
                                           System.out.println(val_peek(2).obj.toString() + " id");
                                           System.out.println(val_peek(0).obj.toString() + " expr");
                                           ((Expression) val_peek(2).obj).type = check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                           symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj);
                                         } }
break;
case 71:
//#line 414 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 72:
//#line 415 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 73:
//#line 418 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 74:
//#line 426 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 75:
//#line 427 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 76:
//#line 428 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 77:
//#line 431 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 78:
//#line 433 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 79:
//#line 435 "solver_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 80:
//#line 439 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1235 "SolverParser.java"
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
