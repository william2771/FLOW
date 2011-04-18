package flow.ast;

public class WhileNode extends StatementNode {

	public WhileNode(Expression exp, SequenceNode block) {
		this.exp = exp;
		this.block = block;
	}

	private Expression exp;
	private SequenceNode block;

	@Override
	public String emit() {
		return "while (" + exp.emit() + ") {\n" + block.emit() +"}\n";
	}

}
