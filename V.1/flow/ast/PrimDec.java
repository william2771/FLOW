package flow.ast;

public class PrimDec extends StatementNode {

	public PrimDec(Type type, ID id, Expression val)
	{
		this.type = type;
		this.id = id;
		this.val = val;
	}

	private Type type;
	private ID id;
	private Expression val;
	
	@Override
	public String toString() {
		return type + " " + id + " = " + val;
	}

}
