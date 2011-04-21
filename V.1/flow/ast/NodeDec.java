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
		return "Node " + id.toString() + " = new Node(" + alist.toString() + ");\nnodes.append("+id+")";
	}
	

}
