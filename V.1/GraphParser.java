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






//#line 2 "graph_parser.y"
  import java.io.*;
  import java.util.*;
  import flow.ast.*;
  import flow.structure.*;
//#line 22 "GraphParser.java"




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
    0,    1,    2,    3,    3,    3,    3,    6,    6,    6,
    6,    5,    5,    5,    8,    8,   14,   14,   17,   17,
    4,    4,    4,    4,    4,    4,    4,   20,    7,    7,
    7,    7,    7,   22,   26,   11,    9,   12,   10,   13,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   25,   25,   25,   24,   19,   19,   28,   30,
   27,   27,   27,   27,   32,   18,   18,   16,   16,   15,
   31,   31,   31,   29,   29,   29,   21,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    1,    3,    2,    2,    1,    3,
    2,    1,    1,    1,    1,    1,    3,    2,    4,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    1,
    1,    1,    2,    4,    0,    8,    7,    7,    7,    7,
    3,    4,    2,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    1,    1,    1,    1,
    1,    1,    3,    1,    0,    2,    3,    3,    4,    3,
    1,    1,    1,    1,    3,    2,    4,    1,    3,    1,
    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,   81,   82,   83,   72,   73,
    0,    0,    0,    0,   80,    0,    0,    0,    5,   12,
   13,   14,   23,    0,   24,   21,   22,   25,   26,   27,
    0,    0,    0,   71,   74,    3,    0,    0,    0,    0,
   86,   85,   84,    0,    0,    0,    0,   57,   60,    0,
    0,   61,   62,    0,    0,    7,    4,    0,    0,    0,
    0,    0,    0,    0,    0,   75,    0,    0,    0,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    6,    0,    0,
   28,    0,    0,   64,    0,    0,    0,    0,    0,    0,
   56,   41,    0,    0,   70,   55,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   52,   53,    0,   34,   69,
   35,    0,   66,    0,    0,    0,   42,    0,    0,   63,
    0,    0,    0,   37,   39,    0,    0,    0,    0,    0,
    9,   15,   16,    0,   29,   30,   31,   32,    0,    0,
    0,    0,   36,    0,   11,    8,    0,    0,    0,   10,
    0,    0,    0,    0,    0,    0,   38,   40,
};
final static short yydgoto[] = {                          2,
    3,    4,   17,   18,   19,  139,  140,  141,   20,   21,
   22,  142,  143,   23,   47,   73,   25,   26,   48,   28,
   29,   49,   74,   31,   95,  129,   32,   51,   52,   53,
   34,   35,
};
final static short yysindex[] = {                      -266,
 -267,    0,    0,  200,  -38,    0,    0,    0,    0,    0,
 -237,   -3,   -1,  161,    0, -233,  200,  -12,    0,    0,
    0,    0,    0,  124,    0,    0,    0,    0,    0,    0,
   47, -233,   53,    0,    0,    0,  243,  161,  161,   57,
    0,    0,    0,  161,  144,  161,  132,    0,    0,   70,
   53,    0,    0,  161,   39,    0,    0, -233,  161,   41,
  161,  161,  243,   55,  161,    0,    7,   21, -233,    0,
   37,   84,  -42,   70, -233,  161,  161,  161,  161,  161,
  161,  161,  161,  161,  161,  161,   83,    0,  161,  452,
    0,  146,   -7,    0,  155, -233,  161,  452,    6,   14,
    0,    0,  161,  161,    0,    0,   81,   81,  452,  452,
  452,  452,  177,  177,   30,    0,    0,   83,    0,    0,
    0,  243,    0,   70,  200,  200,    0,   70,   35,    0,
  -31,  182,  291,    0,    0,  104,  125,  161,  319,  109,
    0,    0,    0,  130,    0,    0,    0,    0, -233,  161,
  161,   70,    0,  122,    0,    0,   55,   48,   59,    0,
   63,   79,  291,  291,  337,  356,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  203,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -37,    0,    0,  149,
  -28,    0,    0,  158,    0,    0,    0,    0,    0,    0,
    0,    0,  168,   52,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  151,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  159,    0,  166,  101,
    0,    0,    0,    0,    0,    0,    0,  107,    0,    0,
    0,    0,    0,    0,    0,    0,  163,  172,   16,   29,
  112,  118,   90,   95,  -17,    0,    0,  186,    0,    0,
    0,    0,    0,  194,    0,    0,    0,  235,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  195,    0,    0,    0,    0,  196,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   54,   42,   44,   34,  -19,  108,    0,    0,
    0,    0,    0,  160,  359,  -43,    0,  156,  347,    0,
  178,  404,  589,  -51,    0,    0,  581,  408,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=747;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         59,
    1,  104,    5,   59,   59,   59,   59,   59,   58,   59,
   87,   94,   58,   58,   58,   58,   58,   92,   58,   54,
   36,   59,   59,   54,   59,   54,   54,   54,   37,   84,
   58,   58,   16,   58,   85,   83,   38,   82,   39,   86,
   15,   54,   54,   84,   54,  118,   57,   99,   85,   83,
  105,   82,   80,   86,   81,   59,   47,   84,   55,   47,
   56,  100,   85,   83,   58,   82,   80,   86,   81,   45,
  130,   85,   45,   84,   47,   54,   86,  102,   85,   83,
   80,   82,   81,   86,   84,  120,   63,   45,  161,   85,
   83,   66,   82,  134,   86,   84,   80,   88,   81,  162,
   85,   83,   69,   82,   16,   86,   84,   80,   47,   81,
   76,   85,   83,   65,   82,   97,   86,   84,   80,  154,
   81,   45,   85,   83,  103,   82,  104,   86,  125,   80,
   51,   81,   51,   51,   51,   50,  126,   50,   50,   50,
   80,   68,   81,  150,   68,  154,  154,   67,   51,   51,
   67,   51,   46,   50,   50,   46,   50,  133,   44,   68,
   68,   44,   68,   61,  151,   67,   67,  156,   67,   61,
   46,   61,   55,   55,   56,   56,   44,   75,  131,  132,
  160,   60,   51,   45,   59,  163,  119,   50,   44,  104,
   59,   78,   59,   68,   78,  121,  165,  166,  122,   67,
   45,  164,    2,   49,   46,   44,   49,   87,   65,   78,
   44,   65,   48,   84,   62,   48,   18,   17,   85,   91,
   62,   49,   62,   86,   20,    6,    7,    8,    9,   10,
   48,   11,   12,   13,   46,    0,    0,   14,   59,   59,
   59,   59,   15,   78,   19,   16,  155,   58,   58,   58,
   58,   46,   77,   33,   76,   49,    0,    0,   54,   54,
   54,   54,    0,   16,   48,    0,    0,    0,   76,   77,
   78,   79,  155,  155,    0,   79,    0,    0,   79,    0,
    0,    0,   76,   77,   78,   79,    0,    0,  145,    0,
    0,   47,   47,   79,  145,    0,   76,   77,   78,   79,
    0,    0,    0,    0,   45,   45,  135,    0,    0,    0,
  147,    0,   76,   77,   78,   79,  147,    0,  145,  145,
  145,  145,    0,   76,   77,   78,   79,   79,    0,    0,
    0,    0,    0,    0,   76,   77,   78,   79,    0,    0,
  147,  147,  147,  147,    0,   76,   77,   78,   79,    0,
   27,    0,    0,    0,    0,    0,    0,    0,   78,   79,
    0,    0,   24,   27,    0,   51,   51,   51,   51,    0,
   50,   50,   50,   50,   54,   24,   68,   68,   68,   68,
    0,    0,   67,   67,   67,   67,    0,   46,   46,    0,
   64,    0,    0,   44,   44,    0,    0,    0,   58,    0,
    6,    7,    8,    9,   10,   40,   11,   30,    0,    0,
    0,   33,    0,   41,   42,   43,   89,   15,    0,    0,
   30,    0,   40,    0,   33,    0,    0,  101,    0,    0,
   41,   42,   43,  106,   15,    0,    0,    0,    6,    7,
    8,    9,   10,  153,   11,   12,   13,    0,    0,    0,
   14,    0,    0,    0,  123,   15,    6,    7,    8,    9,
   10,  167,   11,   12,   13,    0,    0,    0,   14,    0,
    0,   27,   27,   15,    0,    0,    0,   27,   27,  146,
  168,    0,    0,   24,   24,  146,    0,    0,   84,   24,
   24,  144,    0,   85,   83,    0,   82,  144,   86,    6,
    7,    8,    9,   10,    0,   11,    0,  157,    0,  146,
  146,  146,  146,    0,    0,    0,    0,    0,    0,    0,
    0,  144,  144,  144,  144,    0,    0,    0,   30,   30,
    0,    0,   33,   33,   30,   30,  148,    0,   33,   33,
   33,    0,  148,    0,    0,    0,   33,    6,    7,    8,
    9,   10,    0,   11,  136,  137,    0,    0,  138,   14,
    0,    0,    0,    0,   15,    0,  148,  148,  148,  148,
   33,   33,   33,   33,    0,    6,    7,    8,    9,   10,
    0,   11,  136,  137,    0,    0,  138,   14,    0,    0,
    0,    0,   15,    6,    7,    8,    9,   10,    0,   11,
  136,  137,   50,    0,  138,   14,    0,    0,    0,    0,
   15,    0,    6,    7,    8,    9,   10,   66,   11,  136,
  137,    0,    0,  138,   14,   72,   67,   68,    0,   15,
    0,    0,   70,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   96,    0,    0,    0,   90,    0,    0,
   93,    0,    0,   98,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  107,  108,  109,  110,  111,  112,
  113,  114,  115,  116,  117,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  124,    0,    0,    0,    0,
    0,  127,  128,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   96,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  149,    0,    0,    0,    0,    0,  149,
    0,    0,    0,    0,    0,    0,  152,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  158,  159,
    0,    0,    0,  149,  149,  149,  149,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  267,   44,  270,   41,   42,   43,   44,   45,   37,   47,
   54,   63,   41,   42,   43,   44,   45,   61,   47,   37,
   59,   59,   60,   41,   62,   43,   44,   45,  266,   37,
   59,   60,   64,   62,   42,   43,   40,   45,   40,   47,
  274,   59,   60,   37,   62,   89,   59,   41,   42,   43,
   93,   45,   60,   47,   62,   93,   41,   37,   17,   44,
   17,   41,   42,   43,   93,   45,   60,   47,   62,   41,
  122,   42,   44,   37,   59,   93,   47,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,   59,   41,   42,
   43,   40,   45,  125,   47,   37,   60,   59,   62,   41,
   42,   43,   46,   45,   64,   47,   37,   60,   93,   62,
   59,   42,   43,   61,   45,   61,   47,   37,   60,  139,
   62,   93,   42,   43,   41,   45,   44,   47,  123,   60,
   41,   62,   43,   44,   45,   41,  123,   43,   44,   45,
   60,   41,   62,   40,   44,  165,  166,   41,   59,   60,
   44,   62,   41,   59,   60,   44,   62,  123,   41,   59,
   60,   44,   62,   40,   40,   59,   60,   59,   62,   40,
   59,   40,  131,  132,  131,  132,   59,   46,  125,  126,
   59,   58,   93,   40,   61,  123,   41,   93,   45,   44,
   61,   41,   61,   93,   44,   41,  163,  164,   44,   93,
   40,  123,    0,   41,   93,   45,   44,   59,   41,   59,
   93,   44,   41,   37,   91,   44,   59,   59,   42,   60,
   91,   59,   91,   47,   59,  257,  258,  259,  260,  261,
   59,  263,  264,  265,   91,   -1,   -1,  269,  276,  277,
  278,  279,  274,   93,   59,   64,  139,  276,  277,  278,
  279,   91,   59,   59,   59,   93,   -1,   -1,  276,  277,
  278,  279,   -1,   64,   93,   -1,   -1,   -1,  276,  277,
  278,  279,  165,  166,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,  276,  277,  278,  279,   -1,   -1,  133,   -1,
   -1,  276,  277,   59,  139,   -1,  276,  277,  278,  279,
   -1,   -1,   -1,   -1,  276,  277,  125,   -1,   -1,   -1,
  133,   -1,  276,  277,  278,  279,  139,   -1,  163,  164,
  165,  166,   -1,  276,  277,  278,  279,   93,   -1,   -1,
   -1,   -1,   -1,   -1,  276,  277,  278,  279,   -1,   -1,
  163,  164,  165,  166,   -1,  276,  277,  278,  279,   -1,
    4,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,
   -1,   -1,    4,   17,   -1,  276,  277,  278,  279,   -1,
  276,  277,  278,  279,   16,   17,  276,  277,  278,  279,
   -1,   -1,  276,  277,  278,  279,   -1,  276,  277,   -1,
   32,   -1,   -1,  276,  277,   -1,   -1,   -1,  275,   -1,
  257,  258,  259,  260,  261,  262,  263,    4,   -1,   -1,
   -1,    4,   -1,  270,  271,  272,   58,  274,   -1,   -1,
   17,   -1,  262,   -1,   17,   -1,   -1,   69,   -1,   -1,
  270,  271,  272,   75,  274,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  125,  263,  264,  265,   -1,   -1,   -1,
  269,   -1,   -1,   -1,   96,  274,  257,  258,  259,  260,
  261,  125,  263,  264,  265,   -1,   -1,   -1,  269,   -1,
   -1,  125,  126,  274,   -1,   -1,   -1,  131,  132,  133,
  125,   -1,   -1,  125,  126,  139,   -1,   -1,   37,  131,
  132,  133,   -1,   42,   43,   -1,   45,  139,   47,  257,
  258,  259,  260,  261,   -1,  263,   -1,  149,   -1,  163,
  164,  165,  166,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  163,  164,  165,  166,   -1,   -1,   -1,  125,  126,
   -1,   -1,  125,  126,  131,  132,  133,   -1,  131,  132,
  133,   -1,  139,   -1,   -1,   -1,  139,  257,  258,  259,
  260,  261,   -1,  263,  264,  265,   -1,   -1,  268,  269,
   -1,   -1,   -1,   -1,  274,   -1,  163,  164,  165,  166,
  163,  164,  165,  166,   -1,  257,  258,  259,  260,  261,
   -1,  263,  264,  265,   -1,   -1,  268,  269,   -1,   -1,
   -1,   -1,  274,  257,  258,  259,  260,  261,   -1,  263,
  264,  265,   14,   -1,  268,  269,   -1,   -1,   -1,   -1,
  274,   -1,  257,  258,  259,  260,  261,   37,  263,  264,
  265,   -1,   -1,  268,  269,   45,   38,   39,   -1,  274,
   -1,   -1,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   63,   -1,   -1,   -1,   59,   -1,   -1,
   62,   -1,   -1,   65,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   76,   77,   78,   79,   80,   81,
   82,   83,   84,   85,   86,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   97,   -1,   -1,   -1,   -1,
   -1,  103,  104,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  122,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  133,   -1,   -1,   -1,   -1,   -1,  139,
   -1,   -1,   -1,   -1,   -1,   -1,  138,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  150,  151,
   -1,   -1,   -1,  163,  164,  165,  166,
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
"valid_program : graph_decl",
"graph_decl : type_link graph_stmt_list",
"type_link : USE STR ';'",
"graph_stmt_list : graph_stmt ';'",
"graph_stmt_list : block_stmt",
"graph_stmt_list : graph_stmt_list graph_stmt ';'",
"graph_stmt_list : graph_stmt_list block_stmt",
"func_stmt_list : func_stmt ';'",
"func_stmt_list : func_block_stmt",
"func_stmt_list : func_stmt_list func_stmt ';'",
"func_stmt_list : func_stmt_list func_block_stmt",
"block_stmt : while_stmt",
"block_stmt : if_stmt",
"block_stmt : func_dec",
"func_block_stmt : func_while_stmt",
"func_block_stmt : func_if_stmt",
"node_dec : '@' id attr_list",
"node_dec : '@' id",
"arc_dec : id ARC id attr_list",
"arc_dec : id ARC id",
"graph_stmt : prim_dec",
"graph_stmt : assignment",
"graph_stmt : node_dec",
"graph_stmt : arc_dec",
"graph_stmt : label_app",
"graph_stmt : print_stmt",
"graph_stmt : func_call",
"label_app : id ':' node_dec",
"func_stmt : prim_dec",
"func_stmt : assignment",
"func_stmt : print_stmt",
"func_stmt : func_call",
"func_stmt : RET expr",
"func_call : id '(' attr_list ')'",
"$$1 :",
"func_dec : param '(' param_list ')' $$1 '{' func_stmt_list '}'",
"while_stmt : WHILE '(' expr ')' '{' graph_stmt_list '}'",
"func_while_stmt : WHILE '(' expr ')' '{' func_stmt_list '}'",
"if_stmt : IF '(' expr ')' '{' graph_stmt_list '}'",
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

//#line 454 "graph_parser.y"

  private GraphLexer lexer;
  private Hashtable symbols;
  //We need another Hashtable for temporary storage
  private Hashtable old;
  private ArrayList<String> labels;
  private ArrayList<String> functions;
  private int errors; //Keeps track of syntax errors

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new GraphParserVal(0);
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
      System.out.println(attrs.get(0));
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

  public GraphParser(Reader r) {
    lexer = new GraphLexer(r, this);
  }

  public GraphParser(Reader r, Hashtable symbols)
  {
    errors = 0;
    lexer = new GraphLexer(r, this);
    this.symbols = symbols;
  }
//#line 588 "GraphParser.java"
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
//#line 54 "graph_parser.y"
{ yyval.sval = "import flow.structure.*;\nimport java.util.*;\npublic class Graph {\npublic Graph() {\nnodes = new FlowList<Node>();\narcs = new FlowList<Arc>();\n" + val_peek(0).obj.toString() + "\n}\nprivate FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }\n private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } \n public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}\n";

                                         for (String label : labels)
                                         {
                                           yyval.sval += "private Node " + label + ";\npublic Node get" + label + "() {\n  return " + label + ";\n}\n";
                                         }

                                         yyval.sval += "\n}\n";
                                         if (errors == 0) { /*only create output java file if there are no syntax errors*/
                                         try {
                                           FileWriter graph_file = new FileWriter(new File("Graph.java"));
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
//#line 77 "graph_parser.y"
{ /* process the typedef file */ 
                             labels = new ArrayList<String>();
                             try {
                               String filepath = symbols.get("filepath") + val_peek(1).sval;
                               TypeParser tparser = new TypeParser(new FileReader(filepath), symbols);
                               tparser.yyparse();
                              }
                              catch(IOException e) {
                                yyerror("Could not open typedef file.");
                              } }
break;
case 4:
//#line 89 "graph_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 5:
//#line 90 "graph_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(0).obj); }
break;
case 6:
//#line 91 "graph_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 7:
//#line 92 "graph_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(1).obj, (StatementNode) val_peek(0).obj); }
break;
case 8:
//#line 97 "graph_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(1).obj);
                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType; }
break;
case 9:
//#line 100 "graph_parser.y"
{ yyval.obj = new FuncSequenceNode(null, (StatementNode) val_peek(0).obj);


                                           ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(0).obj).retType; }
break;
case 10:
//#line 104 "graph_parser.y"
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
                                             yyerror("You are returning the wrong type" + ((FuncSequenceNode) val_peek(2).obj).retType + ((StatementNode) val_peek(1).obj).retType );
                                           }
                                           else{
                                             ((FuncSequenceNode) yyval.obj).retType = ((StatementNode) val_peek(1).obj).retType;   
                                           } }
break;
case 11:
//#line 120 "graph_parser.y"
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
case 13:
//#line 136 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 14:
//#line 137 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; functions.add(((FunctionNode)val_peek(0).obj).realToString()); }
break;
case 16:
//#line 141 "graph_parser.y"
{yyval.obj = val_peek(0).obj;}
break;
case 17:
//#line 144 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(1).obj, (AttrList) val_peek(0).obj);
   symbols.put(((ID)val_peek(1).obj).toString(), (ID)val_peek(1).obj); ((ID)val_peek(1).obj).type = new pType("Node");}
break;
case 18:
//#line 146 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(0).obj, null);
                                         symbols.put(((ID)val_peek(0).obj).toString(), (ID)val_peek(0).obj); ((ID)val_peek(0).obj).type = new pType("Node");}
break;
case 19:
//#line 150 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(3).obj, (ID) val_peek(1).obj, (AttrList) val_peek(0).obj);}
break;
case 20:
//#line 151 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(2).obj, (ID) val_peek(0).obj, null); }
break;
case 27:
//#line 160 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 28:
//#line 163 "graph_parser.y"
{ yyval.obj = new LabelNode((ID) val_peek(2).obj, (NodeDec) val_peek(0).obj);
                                         labels.add(val_peek(2).sval); }
break;
case 32:
//#line 170 "graph_parser.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 33:
//#line 171 "graph_parser.y"
{ yyval.obj = new ReturnNode((Expression) val_peek(0).obj); 
                                      ((ReturnNode) yyval.obj).retType = ((Expression) val_peek(0).obj).type;}
break;
case 34:
//#line 175 "graph_parser.y"
{ 
                                                                    ID function_name = (ID) symbols.get(val_peek(3).obj.toString());
                                                                    fType functionType = (fType) function_name.type;
                                                                    /*Check attr_list against the parameter types*/
                                                                    check_type((AttrList)val_peek(1).obj, functionType.paramTypes);
                                                                    yyval.obj = new FunctionCall(function_name, (AttrList) val_peek(1).obj);
                                                                    ((Expression) yyval.obj).type = function_name.type; 
                                                               }
break;
case 35:
//#line 186 "graph_parser.y"
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
case 36:
//#line 215 "graph_parser.y"
{ yyval.obj = new FunctionNode((Param) val_peek(7).obj, (ParamList) val_peek(5).obj, (FuncSequenceNode) val_peek(1).obj); 
                                       if (!((Param) val_peek(7).obj).id.type.type.equals(((FuncSequenceNode) val_peek(1).obj).retType.type)) {
                                         yyerror("Function " + ((Param) val_peek(7).obj).id.toString() + " returns the wrong type.");
                                       } 
                                       /*Restore the old symbol table*/
                                       symbols = old; }
break;
case 37:
//#line 224 "graph_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 38:
//#line 227 "graph_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 39:
//#line 230 "graph_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 40:
//#line 233 "graph_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj);}
break;
case 41:
//#line 236 "graph_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 42:
//#line 237 "graph_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (Type) val_peek(2).obj; }
break;
case 43:
//#line 239 "graph_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 44:
//#line 244 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 45:
//#line 249 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 46:
//#line 254 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 47:
//#line 259 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 48:
//#line 264 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 49:
//#line 266 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 50:
//#line 268 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 51:
//#line 270 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 52:
//#line 275 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 53:
//#line 280 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 54:
//#line 285 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 55:
//#line 290 "graph_parser.y"
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
case 56:
//#line 327 "graph_parser.y"
{ yyval.obj = new Dot(new ID("graph"), val_peek(0).obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey(val_peek(0).obj.toString())) {
                                   ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("labels")).get(val_peek(0).obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 57:
//#line 335 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 58:
//#line 336 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 59:
//#line 337 "graph_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 60:
//#line 345 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 61:
//#line 346 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 62:
//#line 347 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 63:
//#line 350 "graph_parser.y"
{ yyval.obj = new ParamList((ParamList)val_peek(2).obj, (Param)val_peek(0).obj); }
break;
case 64:
//#line 351 "graph_parser.y"
{ yyval.obj = new ParamList(null, (Param)val_peek(0).obj); }
break;
case 65:
//#line 352 "graph_parser.y"
{ yyval.obj = null; }
break;
case 66:
//#line 355 "graph_parser.y"
{ yyval.obj = new Param((Type) val_peek(1).obj, (ID) val_peek(0).obj); }
break;
case 67:
//#line 358 "graph_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 68:
//#line 360 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 69:
//#line 364 "graph_parser.y"
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
case 70:
//#line 382 "graph_parser.y"
{ System.out.println("list_list"); yyval.obj = new ListLit((AttrList) val_peek(1).obj);
	      ((Expression) yyval.obj).type = new Type("list " + check_type((AttrList) val_peek(1).obj).toString()); System.out.println("Parsed a list literal.");}
break;
case 71:
//#line 396 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 72:
//#line 397 "graph_parser.y"
{ yyval.obj = new Type("Node"); }
break;
case 73:
//#line 398 "graph_parser.y"
{ yyval.obj = new Type("Arc"); }
break;
case 74:
//#line 399 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 75:
//#line 402 "graph_parser.y"
{ yyval.obj = new Type("list " + val_peek(0).obj.toString()); }
break;
case 76:
//#line 405 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                         if (symbols.containsKey(val_peek(0).obj.toString())) {
                                           yyerror("Variable " + val_peek(0).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(0).obj).type = (Type) val_peek(1).obj;
                                           symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj);
                                         } }
break;
case 77:
//#line 413 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         if (symbols.containsKey(val_peek(2).obj.toString())) {
                                           yyerror("Variable " + val_peek(2).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(2).obj).type = check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                           symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj);
					   System.out.println("Declared " + val_peek(2).obj);
                                         } }
break;
case 78:
//#line 424 "graph_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 79:
//#line 425 "graph_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 80:
//#line 428 "graph_parser.y"
{ System.out.println("Looking at " + val_peek(0).sval); if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 81:
//#line 436 "graph_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 82:
//#line 437 "graph_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 83:
//#line 438 "graph_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 84:
//#line 442 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 85:
//#line 444 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 86:
//#line 446 "graph_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
case 87:
//#line 450 "graph_parser.y"
{ yyval.obj = new Print((Expression) val_peek(0).obj); }
break;
//#line 1270 "GraphParser.java"
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
