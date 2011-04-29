package flow.ast;

public class NodeDec extends StatementNode {

	private ID id;
	private AttributeListNode alist;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Node " + id.toString() + " = new Node(" + alist.toString() + ");\nnodes.append("+id+")";
	}
	

}
