package flow.ast;

public class WhileNode extends StatementNode {

	public WhileNode(Expression exp, SequenceNode block) {
		this.exp = exp;
		this.block = block;
		retType = null;
	}

	private Expression exp;
	private SequenceNode block;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
	    if (this.exp.type.type == "Node" || this.exp.type.type == "Arc"){
		return "while (" + exp + " != null) {\n" + block + "}\n";
	    }
	    else{
		return "while (" + exp + "!= 0) {\n" + block +"}\n";
	    }
	}

}
