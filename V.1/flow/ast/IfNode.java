package flow.ast;

public class IfNode extends StatementNode {

	public IfNode(Expression exp, SequenceNode block) {
		this.exp = exp;
		this.block = block;
		retType = null;
	}

	private Expression exp;
	private SequenceNode block;

	@Override
	public String toString() {
	    if (this.exp.type.type == "Node" || this.exp.type.type == "Arc"){
		return "if (" + exp + " != null) {\n" + block + "}\n";
	    }
	    else{
		return "if (" + exp + " != 0) {\n" + block +"}\n";
	    }
	}

}
