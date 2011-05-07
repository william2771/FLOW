%%

%byaccj

%class TypeLexer

%line

%{
  private TypeParser yyparser;

  public TypeLexer(java.io.Reader r, TypeParser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }

  /* track the line numbers for debugging */
  public int getLine() { return yyline; }
%}

comment = "/*" [^*] ~"*/" | "/*" "*"+ "/" | "//" .* {NL}
ID      = [A-Za-z][A-Za-z0-9_]*
NL      = \n | \r | \r\n

%%

<YYINITIAL>{

/* strip whitespace and comments */
{NL}      |
[ \t]+    |
{comment}   { }

/* reserved words - more to follow */
int         { return TypeParser.INT_T; }
float       { return TypeParser.FLT_T; }
string      { return TypeParser.STR_T; }
Node        { return TypeParser.NODE_T; }
Arc         { return TypeParser.ARC_T; }

/* single-character operators */
"." |
"," |
";" |
"(" | 
")"         { return (int) yycharat(0); }

/* identifier */
{ID}        { //add this id to the symbol table
              yyparser.yylval = new TypeParserVal(yytext());
              return TypeParser.ID; }

} /* End state YYINITIAL */

