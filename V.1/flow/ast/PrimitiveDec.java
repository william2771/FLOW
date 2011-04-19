package flow.ast;

public class PrimitiveDec extends StatementNode {

	private pType type;
	private ID id;
	private pValue val;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return type.toString() + id.toString() + "=" + val;
	}

}
