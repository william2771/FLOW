package flow.ast;

public class WhileNode extends StatementNode {

	public WhileNode(Expression exp, SequenceNode block) {
		this.exp = exp;
		this.block = block;
	}

	private Expression exp;
	private SequenceNode block;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "while (" + exp + "!= 0) {\n" + block +"}\n";
	}

}
