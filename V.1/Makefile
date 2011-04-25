all :	Flow.class
Flow.class :	Flow.java Yylex.java Parser.java TypeParser.java TypeLexer.java
	javac Flow.java
Yylex.java :	flow.flex
	jflex flow.flex
Parser.java :	flow.y
	yacc.macosx -J flow.y
TypeParser.java :	type_parser.y
	yacc.macosx -Jclass='TypeParser' type_parser.y
TypeLexer.java :	flowt.flex
	jflex flowt.flex

clean :
	rm *.class Yylex.java Parser.java ParserVal.java TypeParser.java TypeParserVal.java TypeLexer.java Node.java Arc.java Graph.java
	rm flow/ast/*.class
	rm flow/structure/*.class