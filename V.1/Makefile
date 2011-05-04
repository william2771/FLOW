#YACC = yacc.macosx
YACC = yacc.linux

AST_FILES = 	flow/ast/ASTNode.java		flow/ast/Expression.java	flow/ast/NodeDec.java		flow/ast/Symbol.java \
		flow/ast/ArcDec.java		flow/ast/FunctionCall.java	flow/ast/Param.java		flow/ast/SymbolTable.java \
		flow/ast/Arithmetic.java	flow/ast/FunctionNode.java	flow/ast/ParamList.java		flow/ast/Type.java \
		flow/ast/Assignment.java	flow/ast/ID.java		flow/ast/PrimDec.java		flow/ast/Unary.java \
		flow/ast/Attr.java		flow/ast/IfNode.java		flow/ast/Print.java		flow/ast/WhileNode.java \
		flow/ast/AttrList.java		flow/ast/LabelNode.java		flow/ast/ReturnNode.java	flow/ast/pType.java \
		flow/ast/Cast.java		flow/ast/ListAccess.java	flow/ast/SequenceNode.java	flow/ast/pValue.java \
		flow/ast/Comparison.java	flow/ast/ListDec.java		flow/ast/StatementNode.java	flow/ast/ListAssign.java


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

Test1 :	Flow.class SimpleTest.flow SimpleGraph.flowg
	java Flow SimpleTest.flow
	java Flow SimpleGraph.flowg

Test2 : Flow.class ParityTest.flow SimpleGraph.flowg
	java Flow ParityTest.flow
	java Flow SimpleGraph.flowg

clean :
	rm *~ *.class SolverLexer.java SolverParser.java SolverParserVal.java GraphLexer.java GraphParser.java GraphParserVal.java TypeParser.java TypeParserVal.java TypeLexer.java Node.java Arc.java Graph.java Solver.java flow/ast/*.class flow/structure/*.class
