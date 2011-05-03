package flow.ast;

public class ListDec extends StatementNode {

	public ListDec( Type type, ID id, AttrList alist)
	{
		this.id = id;
		this.aList = aList;
		if (type.type == "int") this.type = "Integer";
		else if (type.type == "double") this.type = "Double";
		else this.type = type.toString();
	}

	private String type;
	private ID id;
	private AttrList aList;
	@Override
	public String toString() {
		return "ArrayList<" + type + "> " + id + " = new ArrayList<" + type + ">(Arrays.asList("+aList+"))"; 
	}

}
