package flow.ast;

public class Cast extends Expression {
    Expression expr;

    public Cast(String t,Expression e){
	expr = e;
    }

    public String toString(){
	return  "(" + type.type + ") (" + expr + ")";
    }

}