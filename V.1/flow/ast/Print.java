package flow.ast;

public class Print extends StatementNode {

	private Expression expr;

	public Print(Expression expr)
	{
		this.expr = expr;
	}

	public String toString()
	{
		return "System.out.println(" + expr + ")";
	}
}