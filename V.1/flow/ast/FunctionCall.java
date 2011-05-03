package flow.ast;

public class FunctionCall extends Expression{
    ID name;
    ParamList params;

    public FunctionCall(ID myName, ParamList myParams){
	name = myName;
	params = myParams;
    }

    public String toString(){
	return name.toString() + '(' + params.toString() + ')';
    }
}