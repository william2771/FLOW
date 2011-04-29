package flow.ast;

public class PrimDec extends StatementNode {

	public PrimDec(pType type, ID id, Expression val)
	{
		this.type = type;
		this.id = id;
		this.val = val;
	}

	private pType type;
	private ID id;
	private Expression val;
	
	@Override
	public String toString() {
		return type + " " + id + " = " + val;
	}

}
