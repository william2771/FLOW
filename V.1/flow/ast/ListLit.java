package flow.ast;

public class ListLit extends Expression {

	private AttrList aList;

	public ListLit(AttrList aList)
	{
		this.aList = aList;
	}

	public String toString()
	{
		return aList.toString();
	}
}