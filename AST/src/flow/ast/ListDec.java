package flow.ast;

public class ListDec extends StatementNode {

	private Type type;
	private ID id;
	private AttributeListNode aList;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ArrayList " + id + " = new ArrayList(Arrays.asList("+aList+"))";
	}

}
