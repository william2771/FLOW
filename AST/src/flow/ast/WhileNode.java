package flow.ast;

public class WhileNode extends StatementNode {

	public WhileNode(Expression exp, SequenceNode block) {
		this.exp = exp;
		this.block = block;
	}

	private Expression exp;
	private SequenceNode block;

	@Override
<<<<<<< HEAD
	public String emit() {
		return "while (" + exp.emit() + ") {\n" + block.emit() +"}\n";
=======
	public String toString() {
		// TODO Auto-generated method stub
		return null;
>>>>>>> bf3ff178548136d5ee5d81657ebf39efe61f8790
	}

}
