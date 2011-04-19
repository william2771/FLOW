package flow.ast;

public class PrimDec extends StatementNode {

	public PrimDec(pType type, ID id, pValue val)
	{
		this.type = type;
		this.id = id;
		this.val = val;
	}

	private pType type;
	private ID id;
	private pValue val;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type.toString() + id.toString() + "=" + val;
	}

}
