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
    4,    5,    6,    6,    7,    7,    8,    8,   13,    9,
   12,   12,   16,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   18,   17,   17,   11,   11,   14,   14,   14,
   14,   14,   15,   15,   15,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    2,    4,    3,    4,    8,    1,    4,
    1,    3,    1,    3,    4,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    1,    1,
    1,    1,    4,    3,    3,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,   48,   49,   50,   51,   52,
    0,   55,   54,   53,   46,   47,    0,    0,    0,    0,
    0,    6,    7,    8,    9,   10,    0,    0,    0,   42,
   39,    0,    3,    0,   26,    0,    0,    0,    0,   19,
    0,    4,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   24,    0,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   35,   36,    0,    0,   12,
    0,   38,    0,    0,    0,    0,    0,   25,    0,   43,
   20,    0,    0,    0,    0,   18,
};
final static short yydgoto[] = {                          2,
    3,    4,   20,   21,   22,   23,   24,   25,   26,   62,
   36,   63,   39,   29,   30,    0,   31,   32,
};
final static short yysindex[] = {                      -266,
 -258,    0,    0,   52,  -47,    0,    0,    0,    0,    0,
 -237,    0,    0,    0,    0,    0,   82, -240,   75,   52,
   12,    0,    0,    0,    0,    0,   42,  114, -240,    0,
    0,   -2,    0,  -30,    0,  135,   82,    7,   32,    0,
   15,    0,   82,   82,   82,   82,   82,   82,   82,   82,
   82,   82,   82, -240,   82,   14,   82, -240,   16,   82,
 -232,   42,   38,    0,   82,    0,   51,   51,  161,  161,
  161,  161,  153,  153,   28,    0,    0,   82,  161,    0,
   -7,    0, -170,  161,    0,   29,   82,    0,   38,    0,
    0,   19,   42,   82,  -42,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   91,
    0,    0,    0,    0,    0,    0,   44,   21,    0,    0,
    0,  -37,    0,    0,    0,  -28,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,   55,    0,    0,    0,  125,  129,  100,  104,
  109,  120,   64,   87,  -17,    0,    0,   58,   92,    0,
    0,    0,    0,   96,    1,    0,    0,    0,   59,    0,
    0,    0,   13,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   99,    0,   65,    0,    0,    0,  373,
  158,  -57,   95,  -16,   56,    0,    0,    0,
};
final static int YYTABLESIZE=460;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
    1,   87,   40,   40,   40,   40,   40,   40,   41,   40,
    5,   33,   41,   41,   41,   41,   41,   40,   41,   37,
   89,   40,   40,   37,   40,   37,   37,   37,   34,   51,
   41,   41,   15,   41,   52,   50,   95,   49,   16,   53,
   85,   37,   37,   51,   37,   21,   16,   64,   52,   50,
   96,   49,   47,   53,   48,   40,   22,   41,   60,   17,
   21,   46,   41,   41,   41,   41,   47,   41,   48,   52,
   42,   22,   65,   66,   53,   37,   83,   18,   51,   41,
   41,   87,   41,   52,   50,   90,   49,   51,   53,   92,
    2,   19,   52,   50,   21,   49,   17,   53,   12,   13,
   14,   47,   11,   48,   34,   22,   34,   34,   34,   94,
   47,   14,   48,   13,   19,   18,   16,   15,   41,   17,
   80,   19,   34,   34,    0,   34,   17,   33,   61,   33,
   33,   33,   45,    0,    0,   45,   44,    0,   91,   44,
   30,    0,    0,   30,   28,   33,   33,   28,   33,   29,
   45,   45,   29,   45,   44,   44,   34,   44,   30,   58,
   27,   28,   28,   27,    0,   32,    0,   29,   32,   31,
    0,   56,   31,    0,   55,   37,    0,   28,   27,   33,
   58,    0,    0,   32,   45,    0,   59,   31,   44,   51,
    0,    0,   30,    0,   52,   55,   28,   51,    0,   53,
    0,   29,   52,   50,   57,   49,    0,   53,    0,    0,
    0,   78,   27,    0,    0,   82,    0,   32,   86,    0,
    0,   31,    0,    0,    0,   57,    6,    7,    8,    9,
   10,    0,    0,    0,    0,    0,    0,   40,   40,   40,
   40,    0,    0,    0,    0,    0,   41,   41,   41,   41,
    0,    0,    0,    0,    0,    0,    0,   37,   37,   37,
   37,    0,    0,    0,    0,    0,    0,   43,   44,   45,
   46,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   43,   44,   45,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   41,   41,   41,   41,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    6,    7,
    8,    9,   10,    0,   11,    0,   43,   44,   45,   46,
   12,   13,   14,    0,   15,    0,    0,   45,   46,    0,
   16,    6,    7,    8,    9,   10,    0,    0,   34,   34,
   34,   34,    0,   12,   13,   14,    0,   15,    0,    0,
   12,   13,   14,   16,   15,    0,    0,    0,    0,    0,
   16,   33,   33,   33,   33,    0,   45,   45,   45,   45,
   44,   44,   44,   44,   30,   30,   27,    0,   28,   28,
    0,    0,    0,   29,   29,    0,    0,   54,    0,   35,
    0,   38,   27,    0,   27,   27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   67,   68,   69,   70,   71,
   72,   73,   74,   75,   76,   77,    0,   79,    0,   81,
    0,    0,   84,    0,    0,    0,    0,   88,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   93,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
  267,   44,   19,   41,   42,   43,   44,   45,   37,   47,
  269,   59,   41,   42,   43,   44,   45,   34,   47,   37,
   78,   59,   60,   41,   62,   43,   44,   45,  266,   37,
   59,   60,  273,   62,   42,   43,   94,   45,  279,   47,
  273,   59,   60,   37,   62,   44,  279,   41,   42,   43,
   93,   45,   60,   47,   62,   93,   44,   37,   61,   59,
   59,   61,   42,   43,   93,   45,   60,   47,   62,   42,
   59,   59,   41,   59,   47,   93,   61,   64,   37,   59,
   60,   44,   62,   42,   43,   93,   45,   37,   47,   61,
    0,   40,   42,   43,   93,   45,   45,   47,  269,  270,
  271,   60,   59,   62,   41,   93,   43,   44,   45,   91,
   60,   59,   62,   59,   40,   64,   59,   59,   20,   45,
   56,   40,   59,   60,   -1,   62,   45,   41,   34,   43,
   44,   45,   41,   -1,   -1,   44,   41,   -1,   83,   44,
   41,   -1,   -1,   44,   41,   59,   60,   44,   62,   41,
   59,   60,   44,   62,   59,   60,   93,   62,   59,   46,
   41,    4,   59,   44,   -1,   41,   -1,   59,   44,   41,
   -1,   58,   44,   -1,   61,   18,   -1,   20,   59,   93,
   46,   -1,   -1,   59,   93,   -1,   29,   59,   93,   37,
   -1,   -1,   93,   -1,   42,   61,   93,   37,   -1,   47,
   -1,   93,   42,   43,   91,   45,   -1,   47,   -1,   -1,
   -1,   54,   93,   -1,   -1,   58,   -1,   93,   61,   -1,
   -1,   93,   -1,   -1,   -1,   91,  257,  258,  259,  260,
  261,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,   -1,  263,   -1,  275,  276,  277,  278,
  269,  270,  271,   -1,  273,   -1,   -1,  277,  278,   -1,
  279,  257,  258,  259,  260,  261,   -1,   -1,  275,  276,
  277,  278,   -1,  269,  270,  271,   -1,  273,   -1,   -1,
  269,  270,  271,  279,  273,   -1,   -1,   -1,   -1,   -1,
  279,  275,  276,  277,  278,   -1,  275,  276,  277,  278,
  275,  276,  277,  278,  275,  276,    4,   -1,  275,  276,
   -1,   -1,   -1,  275,  276,   -1,   -1,  274,   -1,   17,
   -1,   19,   20,   -1,  275,  276,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   43,   44,   45,   46,   47,
   48,   49,   50,   51,   52,   53,   -1,   55,   -1,   57,
   -1,   -1,   60,   -1,   -1,   -1,   -1,   65,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   87,
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
"graph_stmt : expr",
"label_app : id ':' node_dec",
"node_dec : '@' id attr_list",
"node_dec : '@' id",
"arc_dec : id ARC id attr_list",
"arc_dec : id ARC id",
"list_dec : LIST_T OF type ID",
"list_dec : LIST_T OF type id '=' '[' attr_list ']'",
"type : ptype",
"prim_dec : ptype id '=' pvalue",
"attr_list : expr",
"attr_list : attr_list ',' expr",
"attr : pvalue",
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
"expr : pvalue",
"access : id '[' expr ']'",
"assignment : access '=' expr",
"assignment : id '=' expr",
"id : ID",
"id : UNK",
"ptype : INT_T",
"ptype : FLT_T",
"ptype : STR_T",
"ptype : NODE_T",
"ptype : ARC_T",
"pvalue : INT",
"pvalue : FLT",
"pvalue : STR",
};

//#line 274 "graph_parser.y"


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
//#line 429 "GraphParser.java"
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

                                         try
                                         {
                                           FileWriter graph_file = new FileWriter(new File("Graph.java"));
                                           graph_file.write(yyval.sval);
                                           graph_file.flush();
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not create Graph file.");
                                         } }
break;
case 3:
//#line 77 "graph_parser.y"
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
//#line 91 "graph_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 5:
//#line 92 "graph_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 11:
//#line 100 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 12:
//#line 103 "graph_parser.y"
{ yyval.obj = new LabelNode((ID) val_peek(2).obj, (NodeDec) val_peek(0).obj);
                                         labels.add(val_peek(2).sval); }
break;
case 13:
//#line 107 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(1).obj, (AttrList) val_peek(0).obj); }
break;
case 14:
//#line 108 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(0).obj, null); }
break;
case 15:
//#line 111 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(3).obj, (ID) val_peek(1).obj, (AttrList) val_peek(0).obj); }
break;
case 16:
//#line 112 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(2).obj, (ID) val_peek(0).obj, null); }
break;
case 17:
//#line 115 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null); }
break;
case 18:
//#line 116 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj); }
break;
case 19:
//#line 119 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 20:
//#line 122 "graph_parser.y"
{ yyval.obj = new PrimDec((pType) val_peek(3).obj, (ID) val_peek(2).obj, (pValue) val_peek(0).obj); }
break;
case 21:
//#line 125 "graph_parser.y"
{ yyval.obj = new AttrList(null, (Expression) val_peek(0).obj); }
break;
case 22:
//#line 126 "graph_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (pValue) val_peek(0).obj); }
break;
case 23:
//#line 129 "graph_parser.y"
{ yyval.obj = yyval.obj; }
break;
case 24:
//#line 133 "graph_parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 25:
//#line 134 "graph_parser.y"
{ yyval.obj = new Cast(val_peek(2).sval,(Expression) val_peek(0).obj);
                                 ((Expression) yyval.obj).type = (pType) val_peek(2).obj; }
break;
case 26:
//#line 136 "graph_parser.y"
{ yyval.obj = new Unary((Expression) val_peek(0).obj, val_peek(1).sval);
                                 if (((Expression) val_peek(1).obj).type.type.equals("String")){
                                   yyerror("NEG is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type; }
break;
case 27:
//#line 141 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("> is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 28:
//#line 146 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, ">=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror(">= is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 29:
//#line 151 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("< is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 30:
//#line 156 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "<=");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("LTE is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 31:
//#line 161 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "!=");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 32:
//#line 163 "graph_parser.y"
{ yyval.obj = new Comparison((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "==");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 33:
//#line 165 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "+");
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 34:
//#line 167 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "-");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Subtraction is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 35:
//#line 172 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "*");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Multiplication is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 36:
//#line 177 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "/");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Division is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 37:
//#line 182 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "%");
                                 if (((Expression) val_peek(2).obj).type.type.equals("String") || ((Expression) val_peek(0).obj).type.type.equals("String")){
                                   yyerror("Modulus is not a string operation.");
                                 }
                                 ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 38:
//#line 187 "graph_parser.y"
{ yyval.obj = new Dot((ID) val_peek(2).obj, val_peek(0).obj.toString());
                                 if (((Expression) val_peek(2).obj).type.type.equals("List")) {
                                   /* Some kind of magic needed here */
                                   ((Expression) yyval.obj).type = ((Expression) val_peek(0).obj).type;
                                 }
                                 else if (((Expression) val_peek(2).obj).type.type.equals("Node")) {
                                   if (((Hashtable) symbols.get("node_attributes")).containsKey(((ID) val_peek(0).obj).toString()))
                                     ((Expression) yyval.obj).type = new Type(((Hashtable) symbols.get("node_attributes")).get(val_peek(0).obj.toString()).toString());
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
                                   yyerror("Dot operator applied to invalid type: " + ((ID) val_peek(2).obj).toString() + " is of type " + ((Expression) val_peek(2).obj).type.type);
                                   ((Expression) yyval.obj).type = new pType("error");
                                 } }
break;
case 39:
//#line 212 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 40:
//#line 213 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 41:
//#line 214 "graph_parser.y"
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
//#line 222 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 43:
//#line 226 "graph_parser.y"
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
case 44:
//#line 244 "graph_parser.y"
{ yyval.obj = ((ListAccess) val_peek(2).obj).makeLVal((Expression) val_peek(0).obj);
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 45:
//#line 246 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, "=");
                                         ((Expression) yyval.obj).type = check_type((Expression) val_peek(2).obj, (Expression) val_peek(0).obj); }
break;
case 46:
//#line 253 "graph_parser.y"
{ yyval.obj = new ID(val_peek(0).sval); }
break;
case 47:
//#line 254 "graph_parser.y"
{ yyerror("Invalid identifier on line " + lexer.getLine());
                                         yyval.obj = new ID(val_peek(0).sval); }
break;
case 48:
//#line 258 "graph_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 49:
//#line 259 "graph_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 50:
//#line 260 "graph_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 51:
//#line 261 "graph_parser.y"
{ yyval.obj = new pType("Node"); }
break;
case 52:
//#line 262 "graph_parser.y"
{ yyval.obj = new pType("Arc"); }
break;
case 53:
//#line 265 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival);
                                         ((Expression) yyval.obj).type = new pType("int"); }
break;
case 54:
//#line 267 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).dval);
                                         ((Expression) yyval.obj).type = new pType("double"); }
break;
case 55:
//#line 269 "graph_parser.y"
{ yyval.obj = new pValue("\"" + val_peek(0).sval + "\"");
                                         ((Expression) yyval.obj).type = new pType("String"); }
break;
//#line 896 "GraphParser.java"
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
