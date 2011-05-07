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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    4,    4,    4,    4,    4,
    4,    4,    4,    5,    6,    6,    7,    7,    8,    8,
   15,    9,    9,   11,   10,   14,   14,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   18,   17,   17,
   13,   16,   16,   16,   16,   16,   19,   19,   19,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    3,    2,    4,    3,    4,    8,
    1,    2,    4,    7,    7,    1,    3,    3,    4,    2,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    1,    1,    1,    4,    3,    3,
    1,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,   52,   53,   54,   55,   56,
    0,    0,    0,    0,   59,   58,   57,   51,    0,    0,
    0,    0,    0,    6,    7,    8,    9,   10,   11,   12,
    0,    0,    0,   21,   44,    0,   47,    3,    0,    0,
    0,    0,   30,    0,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,    0,    0,
    0,    0,    0,   28,    0,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   39,   40,    0,    0,   14,
    0,   42,    0,    0,    0,    0,    0,    0,   29,    0,
   48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   24,   25,   20,
};
final static short yydgoto[] = {                          2,
    3,    4,   22,   23,   24,   25,   26,   27,   28,   29,
   30,   31,   44,   73,   33,   34,   35,   36,   37,
};
final static short yysindex[] = {                      -266,
 -258,    0,    0,  171,  -56,    0,    0,    0,    0,    0,
  -25, -237,   19,   33,    0,    0,    0,    0,   60, -240,
   47,  171,  -12,    0,    0,    0,    0,    0,    0,    0,
   59,  118, -240,    0,    0,  -22,    0,    0, -240,  -54,
   60,   60,    0,  132,   60,    7,   36,   35,    0,   60,
   60,   60,   60,   60,   60,   60,   60,   60,   60,   60,
 -240,   60,   34,   60, -240,   42,   60,    0, -240,   21,
   37,   59,   72,    0,   60,    0,   75,   75,  153,  153,
  153,  153,  120,  120,   -1,    0,    0,   60,  153,    0,
   -7,    0,   60,  153,   63,   11,   15,   60,    0,   72,
    0,   59,   54,  171,  171,   59,   60,  137,  154,  -32,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  139,    0,    0,    0,    0,    0,    0,    0,    0,
   89,   48,    0,    0,    0,  -37,    0,    0,    0,    0,
    0,    0,    0,  -28,   96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  101,    0,    0,    0,    0,
    0,  122,  102,    0,    0,    0,  128,  129,   16,   99,
  109,  115,   70,   82,  -17,    0,    0,  106,   87,    0,
    0,    0,    0,   92,  124,    0,    0,    0,    0,  125,
    0,  130,    0,    0,    0,  127,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  -33,  -20,    0,  134,    0,    0,    0,    0,
    0,  439,  417,  -70,   30,    0,    0,    0,    0,
};
final static int YYTABLESIZE=546;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         45,
    1,   48,   38,   45,   45,   45,   45,   45,   46,   45,
    5,   98,   46,   46,   46,   46,   46,  100,   46,   41,
   39,   45,   45,   41,   45,   41,   41,   41,   40,   58,
   46,   46,   18,   46,   59,   57,  110,   56,   67,   60,
   59,   41,   41,   58,   41,   60,   49,   74,   59,   57,
   47,   56,   54,   60,   55,   45,   34,   58,   41,   34,
  113,   96,   59,   57,   46,   56,   54,   60,   55,   69,
  108,  109,   42,   58,   34,   41,   75,   97,   59,   57,
   54,   56,   55,   60,   46,  101,   21,   48,   48,   46,
   46,   19,   46,   76,   46,   58,   54,   20,   55,   21,
   59,   57,   93,   56,   19,   60,   46,   46,   34,   46,
   38,   58,   38,   38,   38,   98,   59,   57,   54,   56,
   55,   60,   37,  103,   37,   37,   37,   50,   38,   38,
   50,   38,   49,  104,   54,   49,   55,  105,    2,   32,
   37,   37,   32,   37,  107,   50,   50,   13,   50,   33,
   49,   49,   33,   49,   16,   31,   58,   32,   31,   22,
   15,   59,   38,   65,   18,   26,   60,   33,   36,   35,
   27,   36,   35,   31,   37,   63,   21,   65,   62,   50,
   26,   19,   19,   17,   49,   27,   36,   35,   23,   58,
    0,   32,   62,   21,   59,   57,   90,   56,   19,   60,
   20,   33,    6,    7,    8,    9,   10,   31,   64,    0,
   21,    0,    0,    0,   26,   19,    0,   20,    0,   27,
   36,   35,   64,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   20,    0,    0,   45,   45,   45,
   45,    0,    0,    0,    0,    0,   46,   46,   46,   46,
    0,    0,    0,    0,    0,    0,    0,   41,   41,   41,
   41,  111,    0,    0,    0,    0,    0,   50,   51,   52,
   53,    0,    0,    0,    0,    0,    0,    0,  112,    0,
    0,   50,   51,   52,   53,    0,    0,    0,    0,    0,
   34,   34,    0,    0,    0,   50,   51,   52,   53,    0,
    0,    0,    0,    6,    7,    8,    9,   10,   11,    0,
    0,   50,   51,   52,   53,   15,   16,   17,    0,   18,
    0,   11,   46,   46,   46,   46,    0,    0,   15,   16,
   17,    0,   18,   50,   51,   52,   53,    0,    0,    0,
    0,    0,    0,    0,   38,   38,   38,   38,    0,    0,
    0,   52,   53,    0,    0,    0,   37,   37,   37,   37,
    0,   50,   50,   50,   50,    0,   49,   49,   49,   49,
    0,    0,    0,   32,   32,    0,    0,    0,    0,    0,
    0,    0,    0,   33,   33,    0,    0,    0,    0,   31,
   31,   61,    0,    6,    7,    8,    9,   10,   11,   12,
   13,   14,    0,    0,    0,   15,   16,   17,    0,   18,
    6,    7,    8,    9,   10,   11,   12,   13,   14,    0,
   32,    0,   15,   16,   17,    0,   18,    6,    7,    8,
    9,   10,   11,   12,   13,   14,   45,    0,   32,   15,
   16,   17,    0,   18,    0,    0,    0,    0,    0,   66,
    0,    0,    0,    0,    0,   68,    0,   43,    0,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   88,    0,   70,
   71,   92,    0,   72,    0,   95,    0,    0,   77,   78,
   79,   80,   81,   82,   83,   84,   85,   86,   87,    0,
   89,    0,   91,    0,    0,   94,    0,    0,    0,    0,
    0,    0,    0,   99,    0,    0,    0,    0,    0,    0,
   32,   32,    0,    0,   32,   32,   72,    0,    0,    0,
    0,  102,    0,    0,    0,    0,  106,    0,    0,    0,
    0,    0,    0,    0,    0,   72,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  267,   22,   59,   41,   42,   43,   44,   45,   37,   47,
  269,   44,   41,   42,   43,   44,   45,   88,   47,   37,
   46,   59,   60,   41,   62,   43,   44,   45,  266,   37,
   59,   60,  273,   62,   42,   43,  107,   45,   61,   47,
   42,   59,   60,   37,   62,   47,   59,   41,   42,   43,
   21,   45,   60,   47,   62,   93,   41,   37,   40,   44,
   93,   41,   42,   43,   93,   45,   60,   47,   62,   40,
  104,  105,   40,   37,   59,   93,   41,   41,   42,   43,
   60,   45,   62,   47,   37,   93,   40,  108,  109,   42,
   43,   45,   45,   59,   47,   37,   60,   64,   62,   40,
   42,   43,   61,   45,   45,   47,   59,   60,   93,   62,
   41,   37,   43,   44,   45,   44,   42,   43,   60,   45,
   62,   47,   41,   61,   43,   44,   45,   41,   59,   60,
   44,   62,   41,  123,   60,   44,   62,  123,    0,   41,
   59,   60,   44,   62,   91,   59,   60,   59,   62,   41,
   59,   60,   44,   62,   59,   41,   37,   59,   44,   59,
   59,   42,   93,   46,   59,   44,   47,   59,   41,   41,
   44,   44,   44,   59,   93,   58,   40,   46,   61,   93,
   59,   45,   59,   59,   93,   59,   59,   59,   59,   37,
   -1,   93,   61,   40,   42,   43,   63,   45,   45,   47,
   64,   93,  257,  258,  259,  260,  261,   93,   91,   -1,
   40,   -1,   -1,   -1,   93,   45,   -1,   64,   -1,   93,
   93,   93,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   64,   -1,   -1,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,  125,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,
   -1,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,
  275,  276,   -1,   -1,   -1,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,  275,  276,  277,  278,  269,  270,  271,   -1,  273,
   -1,  262,  275,  276,  277,  278,   -1,   -1,  269,  270,
  271,   -1,  273,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  275,  276,  277,  278,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,  275,  276,  277,  278,
   -1,  275,  276,  277,  278,   -1,  275,  276,  277,  278,
   -1,   -1,   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  275,  276,   -1,   -1,   -1,   -1,  275,
  276,  274,   -1,  257,  258,  259,  260,  261,  262,  263,
  264,  265,   -1,   -1,   -1,  269,  270,  271,   -1,  273,
  257,  258,  259,  260,  261,  262,  263,  264,  265,   -1,
    4,   -1,  269,  270,  271,   -1,  273,  257,  258,  259,
  260,  261,  262,  263,  264,  265,   20,   -1,   22,  269,
  270,  271,   -1,  273,   -1,   -1,   -1,   -1,   -1,   33,
   -1,   -1,   -1,   -1,   -1,   39,   -1,   19,   -1,   21,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   61,   -1,   41,
   42,   65,   -1,   45,   -1,   69,   -1,   -1,   50,   51,
   52,   53,   54,   55,   56,   57,   58,   59,   60,   -1,
   62,   -1,   64,   -1,   -1,   67,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   75,   -1,   -1,   -1,   -1,   -1,   -1,
  104,  105,   -1,   -1,  108,  109,   88,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   98,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  107,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=281;
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
"ARC_T","GRAPH","LIST_T","WHILE","IF","OF","USE","PRINT","STR","FLT","INT",
"LIST","ID","ARC","EQ","NEQ","LTE","GTE","UNK","CAST","NEG",
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
"graph_stmt : if_stmt",
"graph_stmt : while_stmt",
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
"while_stmt : WHILE '(' expr ')' '{' graph_stmt_list '}'",
"if_stmt : IF '(' expr ')' '{' graph_stmt_list '}'",
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

//#line 319 "graph_parser.y"


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
//#line 457 "GraphParser.java"
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
case 13:
//#line 104 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 14:
//#line 107 "graph_parser.y"
{ yyval.obj = new LabelNode((ID) val_peek(2).obj, (NodeDec) val_peek(0).obj);
                                         labels.add(val_peek(2).sval); }
break;
case 15:
//#line 111 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(1).obj, (AttrList) val_peek(0).obj); symbols.put(((ID)val_peek(1).obj).toString(), (ID)val_peek(1).obj);}
break;
case 16:
//#line 112 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(0).obj, null); symbols.put(((ID)val_peek(0).obj).toString(), (ID)val_peek(0).obj);}
break;
case 17:
//#line 115 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(3).obj, (ID) val_peek(1).obj, (AttrList) val_peek(0).obj);}
break;
case 18:
//#line 116 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(2).obj, (ID) val_peek(0).obj, null); }
break;
case 19:
//#line 119 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null); symbols.put(((ID)val_peek(0).obj).toString(), (ID)val_peek(0).obj); System.out.println("3");}
break;
case 20:
//#line 120 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj); symbols.put(((ID)val_peek(4).obj).toString(), (ID)val_peek(4).obj);}
break;
case 21:
//#line 123 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 22:
//#line 126 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null);
                                         if (symbols.containsKey(val_peek(0).obj.toString())) {
                                           yyerror("Variable " + val_peek(0).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(0).obj).type = (Type) val_peek(1).obj;
                                           symbols.put(((ID) val_peek(0).obj).toString(), val_peek(0).obj);
                                         } }
break;
case 23:
//#line 134 "graph_parser.y"
{ yyval.obj = new PrimDec((Type) val_peek(3).obj, (ID) val_peek(2).obj, (Expression) val_peek(0).obj);
                                         if (symbols.containsKey(val_peek(2).obj.toString())) {
                                           yyerror("Variable " + val_peek(2).obj.toString() + " already declared");
                                         }
                                         else {
                                           ((Expression) val_peek(2).obj).type = check_type((Type) val_peek(3).obj, (Expression) val_peek(0).obj);
                                           symbols.put(((ID) val_peek(2).obj).toString(), val_peek(2).obj);
                                         } }
break;
case 24:
//#line 144 "graph_parser.y"
{ yyval.obj = new WhileNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 25:
//#line 147 "graph_parser.y"
{ yyval.obj = new IfNode((Expression) val_peek(4).obj, (SequenceNode) val_peek(1).obj); }
break;
case 26:
//#line 150 "graph_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 27:
//#line 151 "graph_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 154 "graph_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 29:
//#line 155 "graph_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (Type) val_peek(2).obj; }
break;
case 30:
//#line 157 "graph_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 31:
//#line 162 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 167 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 172 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 177 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 182 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 184 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 37:
//#line 186 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 38:
//#line 188 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 39:
//#line 193 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 40:
//#line 198 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 41:
//#line 203 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 42:
//#line 208 "graph_parser.y"
{ System.out.println("1"); yyval.obj = new Dot((ID) val_peek(2).obj, val_peek(0).obj.toString()); System.out.println("2");
   if (((Expression) val_peek(2).obj).type.type.equals("Node")) 
     {System.out.println("3");
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) val_peek(0).obj).toString())){
				     System.out.println("4");
                                     ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("node_attributes")).get(val_peek(0).obj.toString()).toString());
				   }
                                   else {
				     System.out.println("5");
                                     yyerror("Node attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Arc")) {
				   System.out.println("6");
                                   if (((Hashtable) symbols.get("arc_attributes")).containsKey(((ID) val_peek(0).obj).toString())){
				     System.out.println("7");
                                     ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("arc_attributes")).get(val_peek(0).obj.toString()).toString());
				   }
                                   else {
				     System.out.println("8");
                                     yyerror("Arc attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.length() > 4 && ((Expression) val_peek(2).obj).type.type.substring(0,4).equals("list")) {
				   System.out.println("9");
                                   if (val_peek(0).obj.toString().equals("length")) {
                                     ((Expression) yyval.obj).type = new pType("int");
                                   }
                                   else {
                                     yyerror("List attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                     ((Expression) yyval.obj).type = new pType("error");
                                   }
                                 }
                                 else {
				   {System.out.println("10!");
                                   yyerror("Dot operator applied to invalid type: " + ((ID) val_peek(2).obj).toString() + " is of type " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
  }
break;
case 43:
//#line 249 "graph_parser.y"
{ yyval.obj = new Dot(new ID("graph"), val_peek(0).obj.toString());
                                 if (((Hashtable) symbols.get("labels")).containsKey(val_peek(0).obj.toString())) {
                                   ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("labels")).get(val_peek(0).obj.toString()).toString());
                                 }
                                 else {
                                   yyerror("Graph attribute '" + val_peek(0).obj.toString() + "' is not defined");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 44:
//#line 257 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 45:
//#line 258 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 46:
//#line 259 "graph_parser.y"
{ yyval.obj = val_peek(0).obj;
                                 if (!symbols.containsKey(((ID) val_peek(0).obj).toString())) {
                                   yyerror("Undeclared variable '" + ((ID) val_peek(0).obj).toString() + "'");
                                   ((Expression) yyval.obj).type = new pType("error");
                                 }
                                 else {
                                   ((Expression) yyval.obj).type = ((ID) val_peek(0).obj).type;
                                 } }
break;
case 47:
//#line 267 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 48:
//#line 271 "graph_parser.y"
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
case 49:
//#line 289 "graph_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 50:
//#line 291 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 51:
//#line 295 "graph_parser.y"
{ if (symbols.containsKey(val_peek(0).sval)) {
                                           yyval.obj = symbols.get(val_peek(0).sval);
                                         }
                                         else {
                                           yyval.obj = new ID(val_peek(0).sval);
                                         } }
break;
case 52:
//#line 303 "graph_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 53:
//#line 304 "graph_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 54:
//#line 305 "graph_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 55:
//#line 306 "graph_parser.y"
{ yyval.obj = new pType("Node"); }
break;
case 56:
//#line 307 "graph_parser.y"
{ yyval.obj = new pType("Arc"); }
break;
case 57:
//#line 310 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 58:
//#line 312 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 59:
//#line 314 "graph_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
//#line 975 "GraphParser.java"
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
