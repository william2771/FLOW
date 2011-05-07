package flow.ast;

public class PrimDec extends StatementNode {

	public PrimDec(Type type, ID id, Expression val)
	{
		this.type = type;
		this.id = id;
		this.val = val;
		retType = null;
	}

	private Type type;
	private ID id;
	private Expression val;
	
	@Override
	public String toString() {
		if (type.toString().length() > 4 && type.toString().substring(0,4).equals("list")) {
			if (val != null) {
				String temp_name = id.toString() + "_tmp";
				String str;
				String flt = format_list_type(type.toString());
				str = flt.substring(9,flt.length()-1) + "[] " + temp_name + " = {" + val.toString() + "};\n";
				str +=  flt + " " + id + " = new " + flt + "((List<" + flt.substring(9,flt.length()-1) + ">) Arrays.asList(" + temp_name + "))";
				return str;
			}
			else {
				return format_list_type(type.toString()) + " " + id + " = new " + format_list_type(type.toString()) + "()";
			}
		}
		else {
			if (val != null) {
				return format_list_type(type.toString()) + " " + id + " = " + val;
			}
			else {
				return format_list_type(type.toString()) + " " + id;
			}
		}
	}

	private String format_list_type(String type) {
	if (type.length() > 4 && type.substring(0,4).equals("list")) {
		return "FlowList<" + format_list_type(type.substring(5)) + ">";
 	}
	else if (type.equals("int")) {
		return "Integer";
	}
	else return type;
	}
}
