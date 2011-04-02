%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

comment = "/*"(.|\n)*"*/"
FLT     = [0-9]+"."[0-9]+
INT     = [0-9]+
ID      = [A-Za-z][A-Za-z0-9_]*
NL      = \n | \r | \r\n
ARC     = "->"
EQ      = "=="
NEQ     = "!="
LTE     = "<="
GTE     = ">="

%%

/* reserved words - more to follow */
int         { return Parser.INT_T; }
float       { return Parser.FLT_T; }
Node        { return Parser.NODE_T; }
Arc         { return Parser.ARC_T; }
Graph       { return Parser.GRAPH; }
while       { return Parser.WHILE; }
if          { return Parser.IF; }
use         { return Parser.USE; }
print       { return Parser.PRINT; }

/* arc connector */
{ARC}       { return Parser.ARC; }

/* equality and comparison operators */
{EQ}        { return Parser.EQ; }
{NEQ}       { return Parser.NEQ; }
{LTE}       { return Parser.LTE; }
{GTE}       { return Parser.GTE; }

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
"(" | 
")"         { return (int) yycharat(0); }

/* newline 
{NL}        { return Parser.NL; }*/

/* float */
{FLT}       { yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
              return Parser.FLT; }

/* int */
{INT}       { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
              return Parser.INT; }

/* identifier */
{ID}        { yyparser.yylval = new ParserVal(yytext());
              return Parser.ID; }

/* strip whitespace and comments */
[ \t]+    |
{NL}      |
{comment}   { }