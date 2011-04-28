package flow.ast;

public class Unary extends Expression {
	private Expression e;
	private String operator;

    public Unary(Expression expr, String oper){
	e = expr;
	operator = oper;
    }
	
	public String toString(){
		return operator+"("+ e + ")";
	}
}
