package flow.ast;

public class ListDec extends StatementNode {

	public ListDec( Type type, ID id, AttrList alist)
	{
		this.type = type;
		this.id = id;
		this.aList = aList;
	}

	private Type type;
	private ID id;
	private AttrList aList;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ArrayList " + id + " = new ArrayList(Arrays.asList("+aList+"))";
	}

}
