package flow.ast;

public class NodeDec extends StatementNode {

	public NodeDec(ID id, AttrList alist)
	{
		this.id = id;
		this.alist = alist;
	}

	ID id;
	AttrList alist;
	@Override
	public String toString() {
	    if (alist != null){
		return "Node " + id.toString() + " = new Node(" + alist.toString() + ");\nnodes.add("+id+")";
	    }
	    else{
		return "Node " + id.toString() + " = new Node();\nnodes.add(" + id + ")";
	    }

	}
	

}
