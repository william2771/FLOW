package flow.ast;

public class SequenceNode extends ASTNode {

    public Type type;

	public SequenceNode(SequenceNode prev, StatementNode exec)
	{
	    super();
	    type = null;
	}
}