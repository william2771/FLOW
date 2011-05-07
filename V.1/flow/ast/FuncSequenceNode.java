package flow.ast;

public class FuncSequenceNode extends SequenceNode {

    public Type type;
    private FuncSequenceNode prev;
    private StatementNode exec;
    
	public FuncSequenceNode(FuncSequenceNode prev, StatementNode exec)
	{
	    super(prev, exec);

	    type = null;
	    this.exec = exec;
	    this.prev = prev;
	    
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