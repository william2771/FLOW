%%

%byaccj

%class GraphLexer

%line

%{
  private GraphParser yyparser;

  private String string; /* used to build up string literals */

  public GraphLexer(java.io.Reader r, GraphParser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  /* track the line numbers for debugging */
  public int getLine() { return yyline; }
%}

comment = "/*" [^*] ~"*/" | "/*" "*"+ "/" | "//" .* {NL}
FLT     = [0-9]+"."[0-9]+
INT     = [0-9]+
ID      = [A-Za-z][A-Za-z0-9_]*
NL      = \n | \r | \r\n
ARC     = "->"
EQ      = "=="
NEQ     = "!="
LTE     = "<="
GTE     = ">="

/* error lexemes */
BAD_ID  = [^A-Za-z][A-Za-z0-9]+

%state STRING
%state SQSTRING

%%

<YYINITIAL>{

/* strip whitespace and comments */
{NL}      |
[ \t]+    |
{comment}   { }

/* reserved words - more to follow */
int         { return GraphParser.INT_T; }
float       { return GraphParser.FLT_T; }
string      { return GraphParser.STR_T; }
Node        { return GraphParser.NODE_T; }
Arc         { return GraphParser.ARC_T; }
Graph       { return GraphParser.GRAPH; }
List        { return GraphParser.LIST_T; }
while       { return GraphParser.WHILE; }
if          { return GraphParser.IF; }
of          { return GraphParser.OF; }
use         { return GraphParser.USE; }
print       { return GraphParser.PRINT; }

/* arc connector */
{ARC}       { return GraphParser.ARC; }

/* equality and comparison operators */
{EQ}        { return GraphParser.EQ; }
{NEQ}       { return GraphParser.NEQ; }
{LTE}       { return GraphParser.LTE; }
{GTE}       { return GraphParser.GTE; }

/* single-character operators */
"@" |
":" |
"." |
"," |
";" |
"<" |
">" |
"=" |
"+" | 
"-" | 
"*" | 
"/" | 
"%" | 
"{" |
"}" |
"[" |
"]" |
"(" | 
")"         { return (int) yycharat(0); }

/* string literal */

\"          { yybegin(STRING);
              string = ""; }

\'          { yybegin(SQSTRING); 
              string = ""; }

/* float */
{FLT}       { yyparser.yylval = new GraphParserVal(Double.parseDouble(yytext()));
              return GraphParser.FLT; }

/* int */
{INT}       { yyparser.yylval = new GraphParserVal(Integer.parseInt(yytext()));
              return GraphParser.INT; }

/* identifier */
{ID}        { yyparser.yylval = new GraphParserVal(yytext());
              return GraphParser.ID; }

.           { yyparser.yylval = new GraphParserVal(yytext());
              return GraphParser.UNK; }

} /* End state YYINITIAL */

<STRING> {

/* Closing double quote */

\"          { yybegin(YYINITIAL); 
              yyparser.yylval = new GraphParserVal(string);
              return GraphParser.STR; }

/* Escaped characters */

"\\\""      { string += "\""; }
"\\n"       { string += "\n"; }
"\\t"       { string += "\t"; }
"\\r"       { string += "\r"; }
"\\f"       { string += "\f"; }
"\\b"       { string += "\b"; }
"\\\\"      { string += "\\"; }

/* All other characters */

[^\"\\]+    { string += yytext(); }

} /* End state STRING */

<SQSTRING> {

/* Closing single quote */

\'          { yybegin(YYINITIAL);
              yyparser.yylval = new GraphParserVal(string);
              return GraphParser.STR; }

/* Escaped single quote */

"\\'"       { string += "'"; }

/* All other characters */

[^\'\\]+    { string += yytext(); }

} /* End state SQSTRING */