
//////////////////////////
public class ReturnNode {
	Expression expr
	
	public ReturnNode(Expression expr) {
		this.expr = expr;
	}
	
	public String toString() {
		return "return" + expr;
	}

}

/////////////////////////
