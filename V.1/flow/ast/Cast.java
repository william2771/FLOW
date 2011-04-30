package flow.ast;

public class Cast{
    Expression expr;
    String type;

    public Cast(String t,Expression e){
	type = t;
	expr = e;
    }

    public String toString(){
	return  "(" + type + ") (" + expr + ")";
    }

}