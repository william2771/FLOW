package flow.ast;

public class Assignment extends Expression
{
	public Assignment(Expression lval, Expression expr)
	{
		this.lval = lval;
		this.expr = expr;
	}

	private Expression lval;
	private Expression expr;

	public String toString()
	{
		return lval + " = " + expr;
	}
}