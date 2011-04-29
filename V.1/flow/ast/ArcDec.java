package flow.ast;

public class ArcDec extends StatementNode {

	public ArcDec(ID node1, ID node2, AttrList alist)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.alist = alist;
	}

	private ID node1;
	private ID node2;
	private AttrList alist;
		
	@Override
	public String toString() {
		return "arcs.add(new Arc("+ node1 + ", " + node2 + ", " + alist +"))" ;
	}

}
