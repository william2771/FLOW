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
		return id + ".set(" + index + ", " + value + ")";
	}

}