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
    4,    4,    7,    7,    7,    7,    7,    7,   18,   24,
   11,    9,   12,   10,   13,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   19,   19,   19,   19,
   19,   19,   19,   19,   19,   19,   23,   23,   23,   22,
   16,   16,   26,   14,   14,   25,   25,   25,   15,   15,
   21,   21,   20,   28,   28,   28,   27,   27,   27,   17,
};
final static short yylen[] = {                            2,
    1,    2,    1,    3,    2,    1,    3,    2,    2,    1,
    3,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    2,    4,    0,
    8,    7,    7,    7,    7,    3,    4,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    1,    1,    1,    1,    1,    3,    1,    0,    2,
    3,    3,    4,    4,    8,    1,    1,    1,    2,    4,
    1,    3,    1,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
   74,   75,   76,   67,   68,    0,    0,    0,    0,    0,
   73,    0,    1,    0,    0,    0,    6,   13,   14,   15,
   18,   19,   20,   21,   22,    0,    0,    0,    0,   66,
    0,    0,    0,    0,    0,   79,   78,   77,    0,    0,
   52,   55,    0,    0,    0,   56,    0,    0,    8,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    4,
    0,   38,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    0,    0,
    0,   58,    0,    0,    0,    0,    0,    0,    0,   51,
   36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   48,   50,   29,    0,   63,   30,    0,   60,
    0,    0,    0,    0,   37,    0,    0,   57,    0,    0,
    0,    0,    0,   32,   34,    0,    0,    0,    0,    0,
   10,   16,   17,   23,   24,   25,   26,   27,    0,   65,
    0,    0,    0,   31,    0,   12,    9,    0,    0,    0,
   11,    0,    0,    0,    0,    0,    0,   33,   35,
};
final static short yydgoto[] = {                         12,
   13,   14,   15,   16,   17,  129,  130,  131,   18,   19,
   20,  132,  133,   21,   22,   41,   24,   42,   79,   44,
   80,   27,   83,  117,   28,   45,   46,   30,
};
final static short yysindex[] = {                       366,
    0,    0,    0,    0,    0, -265,  -29,  -22, -267,   47,
    0,    0,    0,  419,  419,  -30,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  153,   -1, -241,  -24,    0,
   -3,   47,   47,  -18,    1,    0,    0,    0,   47,    6,
    0,    0,   70,  134,  -24,    0,  419,   12,    0,    0,
   47,   47,   47,   -3,   -2,   47, -241,    7,   21,    0,
 -241,    0,   37,   36,   47,   47,   47,   47,   47,   47,
   47,   47,   47,   47,   47, -241,    0,  457,   70,  141,
   -7,    0,  145, -241,   47,  457,   42,    2,   14,    0,
    0,   47,   81,   81,  457,  457,  457,  457,  159,  159,
   69,    0,    0,    0,    0,   47,    0,    0,   -3,    0,
   70,   38,  419,  419,    0,   70,   53,    0,   47,  321,
  343,  400,  -32,    0,    0,   65,   80,   47,  -31,   85,
    0,    0,    0,    0,    0,    0,    0,    0, -241,    0,
   47,   47,   70,    0,  119,    0,    0,   -2,   48,   59,
    0,   76,   87,  400,  400,  206,  299,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  158,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  144,  -37,  -28,    0,  215,    0,    0,    0,
    0,    0,    0,  163,  -38,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  101,  124,    0,
    0,    0,    0,    0,    0,  107,  157,    0,    0,    0,
    0,    0,  131,  143,   16,   29,  112,  118,   90,   95,
  -17,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  162,    0,    0,    0,    0,  129,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  164,    0,    0,    0,    0,  172,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   84,   99,  132,   58,   35,   52,    0,    0,
    0,    0,    0,  200,  327,  287,  384,  398,  581,  376,
  116,   18,    0,    0,  394,  415,    0,    0,
};
final static int YYTABLESIZE=723;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
   31,   60,   34,   54,   54,   54,   54,   54,   53,   54,
   32,  106,   53,   53,   53,   53,   53,   33,   53,   49,
   69,   54,   54,   49,   54,   49,   49,   49,   50,   73,
   53,   53,   11,   53,   74,   72,   56,   71,   54,   75,
   60,   49,   49,   73,   49,   40,   61,   88,   74,   72,
   39,   71,   69,   75,   70,   54,   42,   73,   85,   42,
  140,   89,   74,   72,   53,   71,   69,   75,   70,   40,
   77,   82,   40,   73,   42,   49,   92,   91,   74,   72,
   69,   71,   70,   75,   73,  107,   40,   40,  152,   74,
   72,   39,   71,  144,   75,   73,   69,   47,   70,  153,
   74,   72,  112,   71,  141,   75,   73,   69,   42,   70,
   74,   74,   72,   48,   71,   75,   75,   73,   69,  142,
   70,   40,   74,   72,  113,   71,  118,   75,  119,   69,
   46,   70,   46,   46,   46,   45,  114,   45,   45,   45,
   69,   62,   70,  147,   62,   48,   49,   61,   46,   46,
   61,   46,   41,   45,   45,   41,   45,    3,   39,   62,
   62,   39,   62,  145,   71,   61,   61,   71,   61,   72,
   41,   44,   72,   52,   44,  122,   39,  151,   49,   76,
  146,  105,   46,   43,  106,  108,   43,   45,  109,   44,
  145,  145,   52,   62,   51,   73,  120,  121,  154,   61,
   74,   43,   80,   59,   41,   75,   59,  146,  146,  155,
   39,  156,  157,   51,    2,   64,   71,    0,   48,   48,
   70,   72,   28,   44,   53,    1,    2,    3,    4,    5,
   69,    6,  126,  127,  123,   43,  128,   10,   54,   54,
   54,   54,   11,   53,    0,    0,    0,   53,   53,   53,
   53,   49,   49,    1,    2,    3,    4,    5,   49,   49,
   49,   49,    1,    2,    3,    4,    5,   35,   65,   66,
   67,   68,    0,    0,    0,   36,   37,   38,    0,   11,
    0,    0,   65,   66,   67,   68,   23,    0,    0,    0,
    0,   42,   42,    0,    0,    0,   65,   66,   67,   68,
   23,   23,    0,    0,   40,   40,    0,    0,   35,    0,
    0,    0,   65,   66,   67,   68,   36,   37,   38,    0,
   11,  134,    0,   65,   66,   67,   68,    0,  134,    0,
  158,    0,    0,   23,   65,   66,   67,   68,    0,    0,
    0,    0,    0,    0,    0,   65,   66,   67,   68,    0,
    0,    0,    0,  134,  134,  134,  134,    0,   67,   68,
    0,    0,    0,    0,    0,   46,   46,   46,   46,    0,
   45,   45,   45,   45,    0,   26,   62,   62,   62,   62,
    0,    0,   61,   61,   61,   61,    0,   41,   41,   26,
   26,    0,    0,   39,   39,    0,    0,   25,    0,   23,
   23,    0,    0,   55,    0,    0,   23,   23,  136,    0,
    0,   25,   25,    0,   29,  136,    0,    0,    0,    0,
    0,    0,   26,  159,   57,    0,    0,    0,   29,   29,
    0,    0,   87,   64,    0,    0,   90,    0,    0,    0,
  136,  136,  136,  136,   25,  124,    0,   84,  135,    0,
    0,  104,    0,    0,    0,  135,    0,    0,    0,  110,
    0,   29,    1,    2,    3,    4,    5,  125,    6,  126,
  127,    0,    0,  128,   10,    0,    0,    0,    0,   11,
  135,  135,  135,  135,    0,    0,    0,    0,   26,   26,
    0,    0,    0,   73,    0,   26,   26,   26,   74,   72,
    0,   71,   84,   75,   26,  137,    0,    0,    0,    0,
   25,   25,  137,    0,  148,  139,    0,   25,   25,  138,
    0,    0,  139,    0,    0,    0,  138,   29,   29,   26,
   26,   26,   26,    0,   29,   29,   29,  137,  137,  137,
  137,    0,    0,   29,    0,    0,    0,  139,  139,  139,
  139,  138,  138,  138,  138,    1,    2,    3,    4,    5,
    0,    6,  126,  127,    0,    0,  128,   10,   29,   29,
   29,   29,   11,    0,    0,    0,    0,    1,    2,    3,
    4,    5,    0,    6,    7,    8,    0,    0,    0,   10,
   43,    0,    0,    0,   11,    0,    0,    0,    0,    1,
    2,    3,    4,    5,    0,    6,    7,    8,    0,    0,
    0,   10,   58,   59,    0,    0,   11,    0,    0,   62,
   63,    0,    1,    2,    3,    4,    5,    0,    6,    7,
    8,   78,    9,   81,   10,    0,   86,    0,    0,   11,
    0,    0,    0,    0,    0,   93,   94,   95,   96,   97,
   98,   99,  100,  101,  102,  103,    1,    2,    3,    4,
    5,    0,    6,  126,  127,  111,    0,  128,   10,    0,
    0,    0,  115,   11,    0,    1,    2,    3,    4,    5,
    0,    6,    7,    8,    0,    0,  116,   10,    0,    0,
    0,    0,   11,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  143,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  149,  150,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  266,   40,  270,   41,   42,   43,   44,   45,   37,   47,
   40,   44,   41,   42,   43,   44,   45,   40,   47,   37,
   59,   59,   60,   41,   62,   43,   44,   45,   59,   37,
   59,   60,  274,   62,   42,   43,   61,   45,   40,   47,
   59,   59,   60,   37,   62,   40,   46,   41,   42,   43,
   45,   45,   60,   47,   62,   93,   41,   37,   61,   44,
   93,   41,   42,   43,   93,   45,   60,   47,   62,   41,
   59,   54,   44,   37,   59,   93,   41,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,   41,   42,
   43,   45,   45,  125,   47,   37,   60,   14,   62,   41,
   42,   43,   61,   45,   40,   47,   37,   60,   93,   62,
   42,   42,   43,   15,   45,   47,   47,   37,   60,   40,
   62,   93,   42,   43,  123,   45,  109,   47,   91,   60,
   41,   62,   43,   44,   45,   41,  123,   43,   44,   45,
   60,   41,   62,   59,   44,   47,   15,   41,   59,   60,
   44,   62,   41,   59,   60,   44,   62,    0,   41,   59,
   60,   44,   62,  129,   41,   59,   60,   44,   62,   41,
   59,   41,   44,   40,   44,  123,   59,   59,   47,   46,
  129,   41,   93,   41,   44,   41,   44,   93,   44,   59,
  156,  157,   40,   93,   61,   37,  113,  114,  123,   93,
   42,   59,   59,   41,   93,   47,   44,  156,  157,  123,
   93,  154,  155,   61,    0,   59,   93,   -1,  120,  121,
   59,   93,   59,   93,   91,  257,  258,  259,  260,  261,
   59,  263,  264,  265,  119,   93,  268,  269,  276,  277,
  278,  279,  274,   91,   -1,   -1,   -1,  276,  277,  278,
  279,  120,  121,  257,  258,  259,  260,  261,  276,  277,
  278,  279,  257,  258,  259,  260,  261,  262,  276,  277,
  278,  279,   -1,   -1,   -1,  270,  271,  272,   -1,  274,
   -1,   -1,  276,  277,  278,  279,    0,   -1,   -1,   -1,
   -1,  276,  277,   -1,   -1,   -1,  276,  277,  278,  279,
   14,   15,   -1,   -1,  276,  277,   -1,   -1,  262,   -1,
   -1,   -1,  276,  277,  278,  279,  270,  271,  272,   -1,
  274,  122,   -1,  276,  277,  278,  279,   -1,  129,   -1,
  125,   -1,   -1,   47,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,   -1,
   -1,   -1,   -1,  154,  155,  156,  157,   -1,  278,  279,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,   -1,
  276,  277,  278,  279,   -1,    0,  276,  277,  278,  279,
   -1,   -1,  276,  277,  278,  279,   -1,  276,  277,   14,
   15,   -1,   -1,  276,  277,   -1,   -1,    0,   -1,  113,
  114,   -1,   -1,   28,   -1,   -1,  120,  121,  122,   -1,
   -1,   14,   15,   -1,    0,  129,   -1,   -1,   -1,   -1,
   -1,   -1,   47,  125,   31,   -1,   -1,   -1,   14,   15,
   -1,   -1,   57,   40,   -1,   -1,   61,   -1,   -1,   -1,
  154,  155,  156,  157,   47,  125,   -1,   54,  122,   -1,
   -1,   76,   -1,   -1,   -1,  129,   -1,   -1,   -1,   84,
   -1,   47,  257,  258,  259,  260,  261,  125,  263,  264,
  265,   -1,   -1,  268,  269,   -1,   -1,   -1,   -1,  274,
  154,  155,  156,  157,   -1,   -1,   -1,   -1,  113,  114,
   -1,   -1,   -1,   37,   -1,  120,  121,  122,   42,   43,
   -1,   45,  109,   47,  129,  122,   -1,   -1,   -1,   -1,
  113,  114,  129,   -1,  139,  122,   -1,  120,  121,  122,
   -1,   -1,  129,   -1,   -1,   -1,  129,  113,  114,  154,
  155,  156,  157,   -1,  120,  121,  122,  154,  155,  156,
  157,   -1,   -1,  129,   -1,   -1,   -1,  154,  155,  156,
  157,  154,  155,  156,  157,  257,  258,  259,  260,  261,
   -1,  263,  264,  265,   -1,   -1,  268,  269,  154,  155,
  156,  157,  274,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,   -1,  263,  264,  265,   -1,   -1,   -1,  269,
   10,   -1,   -1,   -1,  274,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,   -1,  263,  264,  265,   -1,   -1,
   -1,  269,   32,   33,   -1,   -1,  274,   -1,   -1,   39,
   40,   -1,  257,  258,  259,  260,  261,   -1,  263,  264,
  265,   51,  267,   53,  269,   -1,   56,   -1,   -1,  274,
   -1,   -1,   -1,   -1,   -1,   65,   66,   67,   68,   69,
   70,   71,   72,   73,   74,   75,  257,  258,  259,  260,
  261,   -1,  263,  264,  265,   85,   -1,  268,  269,   -1,
   -1,   -1,   92,  274,   -1,  257,  258,  259,  260,  261,
   -1,  263,  264,  265,   -1,   -1,  106,  269,   -1,   -1,
   -1,   -1,  274,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  128,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  141,  142,
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
"param_list : param_list ',' param",
"param_list : param",
"param_list :",
"param : type id",
"assignment : access '=' expr",
"assignment : id '=' expr",
"access : id '[' expr ']'",
"list_dec : LIST_T OF type id",
"list_dec : LIST_T OF type id '=' '[' attr_list ']'",
"type : ptype",
"type : NODE_T",
"type : ARC_T",
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

//#line 444 "solver_parser.y"

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
        if(ret.type == "error") {
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
    functions = new ArrayList<String>();
  }
//#line 552 "SolverParser.java"
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
{ System.out.println("Reducing func_stmt to: func_stmt_list");
                                            yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(1).obj);
                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType;
                                           System.out.println("it is: " + yyval.obj);}
break;
case 10:
//#line 102 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(0).obj);


                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(0).obj).retType; }
break;
case 11:
//#line 106 "solver_parser.y"
{ System.out.println("Reducing func_stmt_list func_stmt to: func_stmt_list");
                                           System.out.println("" + val_peek(2).obj + val_peek(1).obj);
                                            yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj);
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
//#line 124 "solver_parser.y"
{ yyval.obj = new FuncSequenceNode((FuncSequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj);  System.out.println(val_peek(1).obj);
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
//#line 140 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 15:
//#line 141 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; functions.add(((FunctionNode)val_peek(0).obj).realToString()); }
break;
case 17:
//#line 145 "solver_parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 22:
//#line 152 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 27:
//#line 159 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 28:
//#line 160 "solver_parser.y"
{ yyval.obj = new ReturnNode((Expression) val_peek(0).obj); 
                                      ((ReturnNode) yyval.obj).retType = ((Expression) val_peek(0).obj).type;}
break;
case 29:
//#line 164 "solver_parser.y"
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
case 30:
//#line 180 "solver_parser.y"
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
case 31:
//#line 209 "solver_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(7).obj, (ParamList) val_peek(5).obj, (FuncSequenceNode) val_peek(1).obj); 
                                       
                                       System.out.println((((FuncSequenceNode) val_peek(1).obj).retType == null));
                                        if (!((Param) val_peek(7).obj).id.type.type.equals(((FuncSequenceNode) val_peek(1).obj).retType.type)) { 

                                         yyerror("Function " + ((Param) val_peek(7).obj).id.toString() + " returns the wrong type.");
                                       } 
                                       /*Restore the old symbol table*/
                                       symbols = old; }
break;
case 32:
//#line 221 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 33:
//#line 224 "solver_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 34:
//#line 227 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 35:
//#line 230 "solver_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj);}
break;
case 36:
//#line 233 "solver_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 37:
//#line 234 "solver_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (Type) val_peek(2).obj; }
break;
case 38:
//#line 236 "solver_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 39:
//#line 241 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 40:
//#line 246 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 41:
//#line 251 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 42:
//#line 256 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 43:
//#line 261 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 44:
//#line 263 "solver_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 45:
//#line 265 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 46:
//#line 267 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 272 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 277 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 49:
//#line 282 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 50:
//#line 287 "solver_parser.y"
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
case 51:
//#line 324 "solver_parser.y"
{ yyval.obj = new Dot(new ID("graph"), val_peek(0).obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey(val_peek(0).obj.toString())) {
                                   ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("labels")).get(val_peek(0).obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 52:
//#line 332 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 53:
//#line 333 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 54:
//#line 334 "solver_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 55:
//#line 342 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 56:
//#line 343 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 57:
//#line 347 "solver_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 58:
//#line 348 "solver_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 59:
//#line 349 "solver_parser.y"
{ yyval.obj = null; }
break;
case 60:
//#line 352 "solver_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 61:
//#line 355 "solver_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 62:
//#line 357 "solver_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 63:
//#line 361 "solver_parser.y"
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
case 64:
//#line 379 "solver_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                              /*added space, was new Type("list" ...) -> new Type("list " ...)*/
                                              ((ID) val_peek(0).obj).type = new Type("list " + val_peek(1).obj);
                                              symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj); }
break;
case 65:
//#line 384 "solver_parser.y"
{ /*Do typechecking*/
					      check_type((Type) val_peek(5).obj, (AttrList) val_peek(1).obj);
                                              yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj);
                                              ((ID) val_peek(4).obj).type = new Type("list " + val_peek(5).obj.toString());
                                              symbols.put(((ID) val_peek(4).obj).toString(), val_peek(4).obj); }
break;
case 66:
//#line 391 "solver_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 67:
//#line 392 "solver_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 68:
//#line 393 "solver_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 69:
//#line 397 "solver_parser.y"
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
//#line 405 "solver_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         if (symbols.containsKey(val_peek(2).obj.toString())) {
                                           yyerror("Variable " + val_peek(2).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(2).obj).type = check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                           symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj);
                                         } }
break;
case 71:
//#line 415 "solver_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 72:
//#line 416 "solver_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 73:
//#line 419 "solver_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 74:
//#line 427 "solver_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 75:
//#line 428 "solver_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 76:
//#line 429 "solver_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 77:
//#line 432 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 78:
//#line 434 "solver_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 79:
//#line 436 "solver_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 80:
//#line 440 "solver_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1223 "SolverParser.java"
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
