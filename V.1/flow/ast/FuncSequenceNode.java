package flow.ast;

public class FuncSequenceNode extends ASTNode {

    public Type type;
    private FuncSequenceNode prev;
    private StatementNode exec;
    
	public FuncSequenceNode(FuncSequenceNode prev, StatementNode exec)
	{
	    this.prev = prev;
	    this.exec = exec;
	    type = null;
	}
	
    public String toString() {
		if (prev != null){
			return prev.toString() + exec + ";\n";
		}
		else{
			return exec + ";\n";
		}
	}
}