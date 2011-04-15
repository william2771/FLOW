package flow.ast;

public class SequenceNode extends ASTNode {

	public SequenceNode()
	{
		left = right = null;
	}
	
	@Override
	public String emit() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private SequenceNode left;
	private StatementNode right;

}
