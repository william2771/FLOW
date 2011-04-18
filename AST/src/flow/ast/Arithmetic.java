package flow.ast;

public class Arithmetic extends Expression {

	private Expression e1;
	private Expression e2;
	private String operator;
	
	public String toString(){
		return e1 + operator + e2 + ";";
	}
}
