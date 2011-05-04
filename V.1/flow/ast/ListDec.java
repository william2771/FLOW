package flow.ast;

public class ListDec extends StatementNode {

	public ListDec(Type type, ID id, AttrList alist)
	{
		this.id = id;
		this.aList = aList;
		if (type.type == "int") this.type = "Integer";
		else if (type.type == "double") this.type = "Double";
		else if (type.type.substring(0,4) == "list") this.type = "ArrayList";
		else this.type = type.toString();
	}

	private String type;
	private ID id;
	private AttrList aList;

	public String toString() {
	    if (aList != null) {
		String str;
		str = type + "[] tmp = {" + alist.toString() + "};\n";
		str +=  "ArrayList<" + type + "> " + id + " = new ArrayList<" + type + ">((List<" + type + ">) Arrays.asList(tmp))";
		return str;
	    }
	    else {
		return "ArrayList<" + type + "> " + id + " = new ArrayList<" + type + ">()";
	    }
	}
}
