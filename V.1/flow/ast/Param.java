package flow.ast;

public class Param {
	public Type type;
	public ID id;

	public Param(Type type, ID id) {
		this.type = type;
		this.id = id;
	}

	public String toString() {
		return "" + format_list_type(type.toString()) + " " + id;
	}

	private String format_list_type(String type) {
	if (type.length() > 4 && type.substring(0,4).equals("list")) {
		return "FlowList<" + format_list_type(type.substring(5)) + ">";
 	}
		else return type;
	}


}
