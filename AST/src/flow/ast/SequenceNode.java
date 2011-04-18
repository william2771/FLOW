package flow.ast;

public class SequenceNode extends ASTNode {

	public SequenceNode(SequenceNode prev, StatementNode exec)
	{
		this.prev = prev;
		this.exec = exec;
	}

	@Override
<<<<<<< HEAD
	public String emit() {
		return prev.emit() + exec.emit();
=======
	public String toString() {
		// TODO Auto-generated method stub
		return prev.toString() + exec.toString();
>>>>>>> bf3ff178548136d5ee5d81657ebf39efe61f8790
	}

	private SequenceNode prev;
	private StatementNode exec;

}
