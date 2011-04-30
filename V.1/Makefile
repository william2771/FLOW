YACC = yacc.macosx

all :	Flow.class
Flow.class :	Flow.java SolverLexer.java SolverParser.java GraphLexer.java GraphParser.java TypeParser.java TypeLexer.java
	javac Flow.java
SolverLexer.java :	flow.flex
	jflex flow.flex
SolverParser.java :	solver_parser.y
	$(YACC) -Jclass='SolverParser' solver_parser.y
GraphLexer.java :	flowg.flex
	jflex flowg.flex
GraphParser.java :	graph_parser.y
	$(YACC) -Jclass='GraphParser' graph_parser.y
TypeLexer.java :	flowt.flex
	jflex flowt.flex
TypeParser.java :	type_parser.y
	$(YACC) -Jclass='TypeParser' type_parser.y

Test1 :	Flow.class SimpleTest.flow SimpleGraph.flowg
	java Flow SimpleTest.flow
	java Flow SimpleGraph.flowg
	
Test2 : Flow.class ParityTest.flow SimpleGraph.flowg
	java Flow ParityTest.flow
	java Flow SimpleGraph.flowg
	
clean :
	rm *~ *.class SolverLexer.java SolverParser.java SolverParserVal.java GraphLexer.java GraphParser.java GraphParserVal.java TypeParser.java TypeParserVal.java TypeLexer.java Node.java Arc.java Graph.java
	rm flow/ast/*.class
	rm flow/structure/*.class