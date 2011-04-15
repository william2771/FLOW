package flow.ast;

public class SequenceNode extends ASTNode {

	public SequenceNode(SequenceNode prev, StatementNode exec)
	{
		this.prev = prev;
		this.exec = exec;
	}

	@Override
	public String emit() {
		return prev.emit() + exec.emit();
	}

	private SequenceNode prev;
	private StatementNode exec;

}
