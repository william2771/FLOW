%%

%byaccj

%class SolverLexer

%line

%{
  private SolverParser yyparser;

  private String string; /* used to build up string literals */

  public SolverLexer(java.io.Reader r, SolverParser yyparser) {
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
int         { return SolverParser.INT_T; }
float       { return SolverParser.FLT_T; }
string      { return SolverParser.STR_T; }
Node        { return SolverParser.NODE_T; }
Arc         { return SolverParser.ARC_T; }
Graph       { return SolverParser.GRAPH; }
List        { return SolverParser.LIST_T; }
while       { return SolverParser.WHILE; }
if          { return SolverParser.IF; }
of          { return SolverParser.OF; }
use         { return SolverParser.USE; }
return      { return SolverParser.RET; }
print       { return SolverParser.PRINT; }

/* arc connector */
{ARC}       { return SolverParser.ARC; }

/* equality and comparison operators */
{EQ}        { return SolverParser.EQ; }
{NEQ}       { return SolverParser.NEQ; }
{LTE}       { return SolverParser.LTE; }
{GTE}       { return SolverParser.GTE; }

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
{FLT}       { yyparser.yylval = new SolverParserVal(Double.parseDouble(yytext()));
              return SolverParser.FLT; }

/* int */
{INT}       { yyparser.yylval = new SolverParserVal(Integer.parseInt(yytext()));
              return SolverParser.INT; }

/* identifier */
{ID}        { yyparser.yylval = new SolverParserVal(yytext());
              return SolverParser.ID; }

.           { yyparser.yylval = new SolverParserVal(yytext());
              return SolverParser.UNK; }

} /* End state YYINITIAL */

<STRING> {

/* Closing double quote */

\"          { yybegin(YYINITIAL); 
              yyparser.yylval = new SolverParserVal(string);
              return SolverParser.STR; }

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
              yyparser.yylval = new SolverParserVal(string);
              return SolverParser.STR; }

/* Escaped single quote */

"\\'"       { string += "'"; }

/* All other characters */

[^\'\\]+    { string += yytext(); }

} /* End state SQSTRING */