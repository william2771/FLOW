package flow.ast;

public class FunctionNode extends StatementNode {
	Param ftype;
	ParamList signature;
	FuncSequenceNode statements;

	public FunctionNode(Param ftype, ParamList signature, FuncSequenceNode statements) {
		this.ftype = ftype;
		this.signature = signature;
		this.statements = statements;
	}

	public String toString() {
	    return "";
	}
	
	public String realToString() {
		return "public " + ftype + " (" + signature + ")" + " {\n" + statements + "\n}\n";
	}
	
}

