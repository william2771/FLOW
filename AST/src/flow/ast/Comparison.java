package flow.ast;

public class Comparison extends Expression {

	private Expression e1;
	private Expression e2;
	private String operator;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return e1 + operator + e2;
	}

}
