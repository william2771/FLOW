package flow.ast;

public class ReturnNode extends StatementNode {
	Expression expr;
	
	public ReturnNode(Expression expr) {
		this.expr = expr;
	}
	
	public String toString() {
		if (expr == null)
		{
			return "return";
		}
		else
		{
			return "return " + expr;
		}
	}

}