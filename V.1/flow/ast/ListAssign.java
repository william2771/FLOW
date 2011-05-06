package flow.ast;

public class ListAssign extends Expression {

	public ListAssign(ID id, Expression index, Expression value)
	{
		this.id = id;
		this.index = index;
		this.value = value;
	}

	private ID id;
	private Expression index;
	private Expression value;

	public String toString()
	{
	    String str = "if (" + id + ".size()" + "< " + index + "){\n;";
	    int val = index + 1;
	    str += "\tFlowList tmp = new FlowList(" + val + ");\n";
	    str+= "\ttmp.addAll(" + id + ");\n";
	    str+= "\t" + id + "= tmp;\n}";
		str += id + ".set(" + index + ", " + value + ")";
		return str;
	}

}