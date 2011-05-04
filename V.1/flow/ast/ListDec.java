package flow.ast;

public class ListDec extends StatementNode {

	public ListDec(Type type, ID id, AttrList aList)
	{
		this.id = id;
		this.aList = aList;
		if (type.type == "int") this.type = "Integer";
		else if (type.type == "double") this.type = "Double";
		else if (type.type.length() > 4 && type.type.substring(0,4) == "list") this.type = "ArrayList";
		else this.type = type.toString();
	}

	private String type;
	private ID id;
	private AttrList aList;

	public String toString() {
	    if (aList != null) {
		String temp_name = id.toString() + "_tmp";
		String str;
		str = type + "[] " + temp_name + " = {" + aList.toString() + "};\n";
		str +=  "ArrayList<" + type + "> " + id + " = new ArrayList<" + type + ">((List<" + type + ">) Arrays.asList(" + temp_name + "))";
		return str;
	    }
	    else {
		return "ArrayList<" + type + "> " + id + " = new ArrayList<" + type + ">()";
	    }
	}
}
