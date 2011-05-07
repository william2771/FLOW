package flow.ast;

public class FunctionCall extends Expression{
    ID name;
    AttrList params;

    public FunctionCall(ID myName, AttrList myParams){
	super();
	name = myName;
	params = myParams;
    }

    public String toString(){
	return name.toString() + "(" + params.toString() + ")";
    }
}