package flow.ast;

public class Arithmetic extends Expression {

	public Arithmetic(Expression e1, Expression e2, String op)
	{
	    super();
		this.e1 = e1;
		this.e2 = e2;
		this.operator = op;
	}

	private Expression e1;
	private Expression e2;
	private String operator;
	
	public String toString(){
		return e1 + " " + operator + " " + e2;
	}
}
