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
public final static short NEG=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    4,    4,    4,    4,    4,
    4,    5,    6,    6,    7,    7,    8,    8,   13,    9,
   12,   12,   16,   10,   10,   17,   11,   11,   14,   14,
   14,   15,
};
final static short yylen[] = {                            2,
    1,    2,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    3,    3,    2,    4,    3,    4,    8,    1,    4,
    1,    3,    1,    3,    4,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,   29,   30,   31,    0,   27,
   28,    0,    0,    0,    6,    7,    8,    9,   10,   11,
    0,    0,    3,    0,    0,    0,    4,    0,    0,   26,
    0,    0,    0,    0,   19,   32,    0,   23,   21,    5,
    0,   12,    0,    0,   24,    0,    0,    0,    0,    0,
   25,   20,    0,   22,    0,    0,   18,
};
final static short yydgoto[] = {                          2,
    3,    4,   13,   14,   15,   16,   17,   18,   19,   20,
   21,   37,   34,   22,   38,   39,   32,
};
final static short yysindex[] = {                      -255,
 -253,    0,    0,  -62,  -41,    0,    0,    0, -240,    0,
    0, -266,  -62,  -31,    0,    0,    0,    0,    0,    0,
  -58, -266,    0, -248, -242,  -29,    0, -266,  -33,    0,
 -266, -266,  -26, -265,    0,    0,  -12,    0,    0,    0,
 -242,    0,  -56,  -57,    0, -242,    0,  -25, -242,  -12,
    0,    0,  -53,    0, -242,  -43,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -19,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -18,    0,    0,    0,
  -17,    0,    0,    0,    0,    0,  -42,    0,    0,  -16,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   31,    0,   16,    0,    0,    0,   -9,
   -7,  -35,    0,   22,    1,   -1,    0,
};
final static int YYTABLESIZE=217;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   49,   12,   30,   30,   25,   50,   10,   47,    6,    7,
    8,    1,   11,   11,   33,    5,   17,   23,   27,   56,
   41,   43,   45,   44,   44,   24,   48,   27,   36,   40,
   12,   49,   31,   31,   46,   53,   51,   55,    2,   14,
   13,   16,   15,   26,   42,   35,   52,   54,    0,   57,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    6,    7,    8,    0,    0,    0,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,    0,   28,   11,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         58,
   44,   64,   61,   61,   12,   41,  273,  273,  257,  258,
  259,  267,  279,  279,   22,  269,   59,   59,   61,   55,
   28,   31,   32,   31,   32,  266,   34,   59,  271,   59,
   64,   44,   91,   91,   61,   61,   93,   91,    0,   59,
   59,   59,   59,   13,   29,   24,   46,   49,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,   -1,   -1,   -1,
  263,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,   -1,   -1,   -1,   -1,  274,  279,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"'%'",null,null,null,null,"'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"INT_T","FLT_T","STR_T","NODE_T","ARC_T",
"GRAPH","LIST_T","WHILE","IF","OF","USE","PRINT","STR","FLT","INT","LIST","ID",
"ARC","EQ","NEQ","LTE","GTE","UNK","NEG",
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
"attr_list : attr",
"attr_list : attr_list ',' attr",
"attr : pvalue",
"expr : id assignop expr",
"expr : id '[' expr ']'",
"assignop : '='",
"id : ID",
"id : UNK",
"ptype : INT_T",
"ptype : FLT_T",
"ptype : STR_T",
"pvalue : INT",
};

//#line 152 "graph_parser.y"

  private GraphLexer lexer;
  private Hashtable symbols;
  private ArrayList<String> labels;

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

  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }

  public GraphParser(Reader r) {
    lexer = new GraphLexer(r, this);
  }

  public GraphParser(Reader r, Hashtable symbols)
  {
    lexer = new GraphLexer(r, this);
    this.symbols = symbols;
  }
//#line 307 "GraphParser.java"
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
{ yyval.sval = "public class Graph extends flow.structure.SuperGraph\n{\npublic Graph() {\nsuper();\n" + val_peek(0).obj.toString() + "\n}\n";

                                         for (String label : labels)
                                         {
                                           yyval.sval += "private Node " + label + ";\npublic Node " + label + "() {\n  return " + label + ";\n}\n";
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
//#line 75 "graph_parser.y"
{ /* process the typedef file */ 
                                         labels = new ArrayList<String>();
                                         try
                                         {
                                           String filepath = symbols.get("filepath") + val_peek(1).sval;
                                           System.out.println("\nTrying to open " + filepath + "\n");
                                           TypeParser tparser = new TypeParser(new FileReader(filepath), new Hashtable());
                                           tparser.yyparse();
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not open typdef file.");
                                         } }
break;
case 4:
//#line 90 "graph_parser.y"
{ yyval.obj = new SequenceNode(null, (StatementNode) val_peek(1).obj); }
break;
case 5:
//#line 91 "graph_parser.y"
{ yyval.obj = new SequenceNode((SequenceNode) val_peek(2).obj, (StatementNode) val_peek(1).obj); }
break;
case 11:
//#line 99 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 12:
//#line 102 "graph_parser.y"
{ yyval.obj = new LabelNode((ID) val_peek(2).obj, (NodeDec) val_peek(0).obj);
                                         labels.add(val_peek(2).sval); }
break;
case 13:
//#line 106 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(1).obj, (AttrList) val_peek(0).obj); }
break;
case 14:
//#line 107 "graph_parser.y"
{ yyval.obj = new NodeDec((ID) val_peek(0).obj, null); }
break;
case 15:
//#line 110 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(3).obj, (ID) val_peek(1).obj, (AttrList) val_peek(0).obj); }
break;
case 16:
//#line 111 "graph_parser.y"
{ yyval.obj = new ArcDec((ID) val_peek(2).obj, (ID) val_peek(0).obj, null); }
break;
case 17:
//#line 114 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(1).obj, (ID) val_peek(0).obj, null); }
break;
case 18:
//#line 115 "graph_parser.y"
{ yyval.obj = new ListDec((Type) val_peek(5).obj, (ID) val_peek(4).obj, (AttrList) val_peek(1).obj); }
break;
case 19:
//#line 118 "graph_parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 20:
//#line 121 "graph_parser.y"
{ yyval.obj = new PrimDec((pType) val_peek(3).obj, (ID) val_peek(2).obj, (pValue) val_peek(0).obj); }
break;
case 21:
//#line 124 "graph_parser.y"
{ yyval.obj = new AttrList(null, (Attr) val_peek(0).obj); }
break;
case 22:
//#line 125 "graph_parser.y"
{ yyval.obj = new AttrList((AttrList) val_peek(2).obj, (pValue) val_peek(0).obj); }
break;
case 23:
//#line 128 "graph_parser.y"
{ yyval.obj = yyval.obj; }
break;
case 24:
//#line 131 "graph_parser.y"
{ yyval.obj = new Arithmetic((Expression) val_peek(2).obj, (Expression) val_peek(0).obj, (String) val_peek(1).sval); }
break;
case 25:
//#line 132 "graph_parser.y"
{ yyval.obj = new ListAccess((ID) val_peek(3).obj, (Expression) val_peek(1).obj); }
break;
case 26:
//#line 135 "graph_parser.y"
{ yyval.sval = "="; }
break;
case 27:
//#line 138 "graph_parser.y"
{ yyval.obj = new ID(val_peek(0).sval); }
break;
case 28:
//#line 139 "graph_parser.y"
{ yyerror("Invalid identifier on line " + lexer.getLine());
                                         yyval.obj = new ID(val_peek(0).sval); }
break;
case 29:
//#line 143 "graph_parser.y"
{ yyval.obj = new pType("int"); }
break;
case 30:
//#line 144 "graph_parser.y"
{ yyval.obj = new pType("double"); }
break;
case 31:
//#line 145 "graph_parser.y"
{ yyval.obj = new pType("String"); }
break;
case 32:
//#line 148 "graph_parser.y"
{ yyval.obj = new pValue(val_peek(0).ival); }
break;
//#line 592 "GraphParser.java"
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
