package flow.ast;

public class ListAccess extends Expression {

	public ListAccess(ID id, Expression exp)
	{
		this.id = id;
		this.exp = exp;
	}
	private ID id;
	private Expression exp;
	public String toString(){
		return id + ".get(" + exp + ")";
	}

	public ListAssign makeLVal(Expression value)
	{
		return new ListAssign(id, exp, value);
	}
}
