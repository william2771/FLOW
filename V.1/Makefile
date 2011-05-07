
#YACC = yacc.macosx
#YACC = yacc.linux
YACC = yacc

AST_FILES = 	flow/ast/ASTNode.java		flow/ast/Expression.java	flow/ast/NodeDec.java		flow/ast/Symbol.java \
		flow/ast/ArcDec.java		flow/ast/FunctionCall.java	flow/ast/Param.java		flow/ast/SymbolTable.java \
		flow/ast/Arithmetic.java	flow/ast/FunctionNode.java	flow/ast/ParamList.java		flow/ast/Type.java \
		flow/ast/Assignment.java	flow/ast/ID.java		flow/ast/PrimDec.java		flow/ast/Unary.java \
		flow/ast/Attr.java		flow/ast/IfNode.java		flow/ast/Print.java		flow/ast/WhileNode.java \
		flow/ast/AttrList.java		flow/ast/LabelNode.java		flow/ast/ReturnNode.java	flow/ast/pType.java \
		flow/ast/Cast.java		flow/ast/ListAccess.java	flow/ast/SequenceNode.java	flow/ast/pValue.java \
		flow/ast/Comparison.java	flow/ast/ListDec.java		flow/ast/StatementNode.java	flow/ast/ListAssign.java


TESTDIR = ../Test\ Cases/


all :	Flow.class
Flow.class :	Flow.java SolverLexer.java SolverParser.java GraphLexer.java GraphParser.java TypeParser.java TypeLexer.java $(AST_FILES)
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

SumTest : Flow.class $(TESTDIR)SimpleTest/SimpleTest.flow $(TESTDIR)SimpleTest/SimpleTest.flowg $(TESTDIR)SimpleTest/SimpleTest.flowt
	java Flow $(TESTDIR)SimpleTest/SimpleTest.flowg
	java Flow $(TESTDIR)SimpleTest/SimpleTest.flow
	javac Solver.java
	java Solver

ParityTest : Flow.class $(TESTDIR)ParityTest/ParityTest.flow $(TESTDIR)ParityTest/ParityTest.flowg $(TESTDIR)ParityTest/ParityTest.flowt
	java Flow $(TESTDIR)ParityTest/ParityTest.flowg
	java Flow $(TESTDIR)ParityTest/ParityTest.flow
	javac Solver.java
	java Solver

FunctionTest : Flow.class $(TESTDIR)FunctionTest/FunctionTest.flowg $(TESTDIR)FunctionTest/FunctionTest.flowt $(TESTDIR)FunctionTest/FunctionTest.flow
	java Flow $(TESTDIR)FunctionTest/FunctionTest.flowg
	java Flow $(TESTDIR)FunctionTest/FunctionTest.flow
	javac Solver.java
	java Solver

DepthTest : Flow.class $(TESTDIR)Depth/Depth.flowt $(TESTDIR)Depth/Depth.flowg $(TESTDIR)Depth/Depth.flow
	java Flow $(TESTDIR)Depth/Depth.flowg
	java Flow $(TESTDIR)Depth/Depth.flow

	javac Solver.java
	java Solver

EvenDFATest : Flow.class $(TESTDIR)EvenDFA/Even.flowt $(TESTDIR)EvenDFA/Even.flowg $(TESTDIR)EvenDFA/Even.flow
	java Flow $(TESTDIR)EvenDFA/Even.flowg
	java Flow $(TESTDIR)EvenDFA/Even.flow
	javac Solver.java
	java Solve

HelloWorldTest : Flow.class $(TESTDIR)HelloWorld/HelloWorld.flowt $(TESTDIR)HelloWorld/HelloWorld.flowg $(TESTDIR)HelloWorld/HelloWorld.flow 
	java Flow $(TESTDIR)HelloWorld/HelloWorld.flowg
	java Flow $(TESTDIR)HelloWorld/HelloWorld.flow
	javac Solver.java
	java Solve

clean :
	rm *~ *.class SolverLexer.java SolverParser.java SolverParserVal.java GraphLexer.java GraphParser.java GraphParserVal.java TypeParser.java TypeParserVal.java TypeLexer.java Node.java Arc.java Graph.java Solver.java flow/ast/*.class flow/structure/*.class
