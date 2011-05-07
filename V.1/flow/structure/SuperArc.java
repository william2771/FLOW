package flow.structure;

public class SuperArc {
	private SuperNode fromNode;
	
	private SuperNode toNode;
	
	public SuperArc(SuperNode from, SuperNode to){
		fromNode = from;
		toNode = to;
	}
	
	public SuperNode getto(){
		return toNode;
	}
	
	public SuperNode getfrom(){
		return fromNode;
	}

	public boolean setto(SuperNode to){
		toNode = to;
		return true;
	}
	
	public boolean setfrom(SuperNode from){
		fromNode = from;
		return true;
	}
	
	public boolean setNodes(SuperNode from, SuperNode to){
		fromNode = from;
		toNode = to;
		return true;
	}
	

	public FlowList<SuperNode> getNodes(SuperNode from, SuperNode to){
		fromNode = from;
		toNode = to;
		FlowList<SuperNode> twoNodes = new FlowList<SuperNode>();
		twoNodes.add(from);
		twoNodes.add(to);
		return twoNodes;
	}
}
