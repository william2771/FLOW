package flow.ast;

public class Comparison extends Expression {

	private Expression e1;
	private Expression e2;
	private String operator;
        
    public Comparison(Expression expr1, Expression expr2, String oper){
	e1 = expr1;
	e2 = expr2;
	operator = oper;
    }

    
	@Override
	public String toString() {
		// TODO Auto-generated method stub
	    return "(" + e1 + " " + operator + " " + e2 + " ? 1 : 0)";
	}

}
