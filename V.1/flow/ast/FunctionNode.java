package flow.ast;

public class FunctionNode {
	Param ftype;
	ParamList signature;
	SequenceNode statements;

	public FunctionNode(Param ftype, ParamList signature, SequenceNode statements) {
		this.ftype = ftype;
		this.signature = signature;
		this.statements = statements;
	}

	public String toString() {
		return "public " + ftype + " (" + signature + ")" + " {\n" + statements + "\n}\n";
	}
	
}

