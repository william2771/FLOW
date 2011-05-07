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






//#line 2 "type_parser.y"
  import java.io.*;
  import java.util.*;
//#line 20 "TypeParser.java"




public class TypeParser
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
//public class TypeParserVal is defined in TypeParserVal.java


String   yytext;//user variable to return contextual strings
TypeParserVal yyval; //used to return semantic vals from action routines
TypeParserVal yylval;//the 'lval' (result) I got from yylex()
TypeParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new TypeParserVal[YYSTACKSIZE];
  yyval=new TypeParserVal();
  yylval=new TypeParserVal();
  valptr=-1;
}
void val_push(TypeParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
TypeParserVal val_pop()
{
  if (valptr<0)
    return new TypeParserVal();
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
TypeParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new TypeParserVal();
  return valstk[ptr];
}
final TypeParserVal dup_yyval(TypeParserVal val)
{
  TypeParserVal dup = new TypeParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NODE_T=257;
public final static short ARC_T=258;
public final static short INT_T=259;
public final static short FLT_T=260;
public final static short STR_T=261;
public final static short ID=262;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    3,    5,    6,    6,    6,
    4,    4,    4,
};
final static short yylen[] = {                            2,
    2,    7,    6,    3,    1,    0,    2,    1,    1,    1,
    3,    1,    0,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    1,    0,    0,    8,    9,
   10,    0,    5,    0,    0,    0,    0,    7,    0,   12,
    0,    4,    0,    2,    0,    3,   11,
};
final static short yydgoto[] = {                          2,
    3,    6,   12,   21,   13,   14,
};
final static short yysindex[] = {                      -246,
 -250,    0, -245,  -26, -244,    0, -251,  -23,    0,    0,
    0,  -39,    0, -243, -251, -242, -251,    0,  -38,    0,
  -44,    0,  -36,    0, -241,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,  -37,    0,    0,    0,
    0,    0,    0,    0,  -37,  -43,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    7,    0,    8,    0,
};
final static int YYTABLESIZE=25;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   13,   16,   23,    6,   17,   17,    6,    9,   10,   11,
    1,    4,    5,    7,   24,   13,   15,    8,   18,   20,
   27,   19,   26,    0,   22,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
   44,   41,   41,   41,   44,   44,   44,  259,  260,  261,
  257,  262,  258,   40,   59,   59,   40,  262,  262,  262,
  262,   15,   59,   -1,   17,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=262;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"NODE_T","ARC_T","INT_T","FLT_T","STR_T","ID",
};
final static String yyrule[] = {
"$accept : type_def",
"type_def : node_type_def arc_type_def",
"node_type_def : NODE_T ID '(' param_list ')' label_list ';'",
"arc_type_def : ARC_T ID '(' param_list ')' ';'",
"param_list : param_list ',' param",
"param_list : param",
"param_list :",
"param : type ID",
"type : INT_T",
"type : FLT_T",
"type : STR_T",
"label_list : label_list ',' ID",
"label_list : ID",
"label_list :",
};

//#line 106 "type_parser.y"

  private TypeLexer lexer;
  private Hashtable symbols;
  private Hashtable inter;

  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new TypeParserVal(0);
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

  public TypeParser(Reader r) {
    lexer = new TypeLexer(r, this);
  }

  public TypeParser(Reader r, Hashtable symbols)
  {
    lexer = new TypeLexer(r, this);
    this.symbols = symbols;

    //A new hashtable to hold node attributes
    Hashtable node_attributes = new Hashtable();
    //These attributes are automatic
    node_attributes.put("degree", "int");
    node_attributes.put("inDegree", "int");
    node_attributes.put("outDegree", "int");
    node_attributes.put("arcs", "list Arc");
    node_attributes.put("arcsIn", "list Arc");
    node_attributes.put("arcsOut", "list Arc");
    symbols.put("node_attributes", node_attributes);

    //A new hashtable to hold arc attributes
    Hashtable arc_attributes = new Hashtable();
    //These attributes are automatic
    arc_attributes.put("nodes", "list Node");
    arc_attributes.put("from", "Node");
    arc_attributes.put("to", "Node");
    symbols.put("arc_attributes", arc_attributes);

    //A new hashtable to store all the graph attributes and labels
    Hashtable labels = new Hashtable();
    //These attributes are automatic
    labels.put("arcs", "list Arc");
    labels.put("nodes", "list Node");
    labels.put("numArcs", "int");
    labels.put("numNodes", "int");
    symbols.put("labels", labels);

    inter = new Hashtable();
  }
//#line 243 "TypeParser.java"
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
case 1:
//#line 15 "type_parser.y"
{ try
                                         {
                                           FileWriter writer = new FileWriter(new File("Node.java"));
                                           writer.write(val_peek(1).sval);
                                           writer.flush();
                                           writer = new FileWriter(new File("Arc.java"));
                                           writer.write(val_peek(0).sval);
                                           writer.flush(); 
                                         }
                                         catch(IOException e)
                                         {
                                           yyerror("Could not produce type files.");
                                         } }
break;
case 2:
//#line 31 "type_parser.y"
{ symbols.put("node_type", val_peek(5).sval);
                                         yyval.sval = "import flow.structure.*; public class Node {\n  public Node(";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) val_peek(3).obj)
					   {
					     if (comma) yyval.sval += ", ";
                                           yyval.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("node_attributes")).put(s, inter.get(s));
                                           comma = true;
					   }
                                         yyval.sval += ") {\n arcsIn = new FlowList<Arc>(); arcsOut = new FlowList<Arc>(); arcs = new FlowList<Arc>(); degree = inDegree = outDegree = 0;";
                                         for (String s : (ArrayList<String>) val_peek(3).obj)
					   {
					     yyval.sval += "    this." + s + " = " + s + ";\n";
					   }
                                         yyval.sval += "  }\n void addInArc(Arc in)\n   {\n      arcsIn.add(in);\n      arcs.add(in);\n      inDegree++;\n      degree++;\n   }\n\n   void addOutArc(Arc out)\n   {\n      arcsOut.add(out);\n      arcs.add(out);\n      outDegree++;\n      degree++;\n   }\n\n   int getdegree() { return degree; }\n   int getdnDegree() { return inDegree; }\n   int getoutDegree() { return outDegree; }\n\n   FlowList<Arc> getarcs() { return arcs; }\n   FlowList<Arc> getarcsIn() { return arcsOut; }\n   FlowList<Arc> getarcsOut() { return arcsIn; }\n\n   int inDegree;\n   int outDegree;\n   int degree;\n\n   FlowList<Arc> arcsIn;\n   FlowList<Arc> arcsOut;\n   FlowList<Arc> arcs;";
                                         for (String s : (ArrayList<String>) val_peek(3).obj)
                                         {
                                           yyval.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         yyval.sval += "}"; }
break;
case 3:
//#line 58 "type_parser.y"
{ symbols.put("arc_type", val_peek(4).sval);
                                         yyval.sval = "import flow.structure.*; public class Arc {\n  private Node fromNode;\n\t\n\tprivate Node toNode; public Arc(Node source, Node dest, ";
                                         boolean comma = false;
                                         for (String s : (ArrayList<String>) val_peek(2).obj)
                                         {
                                           if (comma) yyval.sval += ", ";
                                           yyval.sval += inter.get(s) + " " + s;
                                           ((Hashtable) symbols.get("arc_attributes")).put(s, inter.get(s));
                                           comma = true;
                                         }
                                         yyval.sval += ") {\n    fromNode = source; toNode = dest;\n source.addOutArc(this);  dest.addInArc(this); ";
                                         for (String s : (ArrayList<String>) val_peek(2).obj)
                                         {
                                           yyval.sval += "    this." + s + " = " + s + ";\n";
                                         }
                                         yyval.sval += "  }\n";
                                         for (String s : (ArrayList<String>) val_peek(2).obj)
                                         {
                                           yyval.sval += "  private " + inter.get(s) + " " + s + ";\n  public " + inter.get(s) + " get" + s + "()\n  { return " + s + "; }\n";
                                         }
                                         yyval.sval += "public Node getto(){\n\t\treturn toNode;\n\t}\n\t\n\tpublic Node getfrom(){\n\t\treturn fromNode;\n\t}\n\n\tpublic boolean setto(Node to){\n\t\ttoNode = to;\n\t\treturn true;\n\t}\n\t\n\tpublic boolean setfrom(Node from){\n\t\tfromNode = from;\n\t\treturn true;\n\t}\n\t\n\tpublic boolean setNodes(Node from, Node to){\n\t\tfromNode = from;\n\t\ttoNode = to;\n\t\treturn true;\n\t}\n\t\n\n\tpublic FlowList<Node> getNodes(Node from, Node to){\n\t\tfromNode = from;\n\t\ttoNode = to;\n\t\tFlowList<Node> twoNodes = new FlowList<Node>();\n\t\ttwoNodes.add(from);\n\t\ttwoNodes.add(to);\n\t\treturn twoNodes;\n\t}} "; }
break;
case 4:
//#line 81 "type_parser.y"
{ yyval.obj = val_peek(2).obj;
                                         ((ArrayList<String>) yyval.obj).add(val_peek(0).sval); }
break;
case 5:
//#line 83 "type_parser.y"
{ yyval.obj = new ArrayList<String>();
                                         ((ArrayList<String>) yyval.obj).add(val_peek(0).sval); }
break;
case 6:
//#line 85 "type_parser.y"
{ yyval.obj = new ArrayList<String>();}
break;
case 7:
//#line 88 "type_parser.y"
{ inter.put(val_peek(0).sval, val_peek(1).sval);
                                         yyval.sval = val_peek(0).sval; }
break;
case 8:
//#line 92 "type_parser.y"
{ yyval.sval = "int"; }
break;
case 9:
//#line 93 "type_parser.y"
{ yyval.sval = "double"; }
break;
case 10:
//#line 94 "type_parser.y"
{ yyval.sval = "String"; }
break;
case 11:
//#line 97 "type_parser.y"
{ yyval.obj = val_peek(2).obj;
                                         ((ArrayList<String>) yyval.obj).add(val_peek(0).sval); }
break;
case 12:
//#line 99 "type_parser.y"
{ yyval.obj = new ArrayList<String>();
                                         ((ArrayList<String>) yyval.obj).add(val_peek(0).sval);
                                         ((Hashtable) symbols.get("labels")).put(val_peek(0).sval, "Node"); }
break;
case 13:
//#line 102 "type_parser.y"
{ /* nothing */ }
break;
//#line 502 "TypeParser.java"
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
public TypeParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public TypeParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################