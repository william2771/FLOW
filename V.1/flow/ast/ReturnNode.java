package flow.ast;

public class ReturnNode extends StatementNode {
	Expression expr;
	
	public ReturnNode(Expression expr) {
	    System.out.println("IN RETURN NODE");
		this.expr = expr;
		retType = null;
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