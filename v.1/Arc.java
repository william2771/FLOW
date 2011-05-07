import flow.structure.*; public class Arc {
  private Node fromNode;
	
	private Node toNode; public Arc(Node source, Node dest, int transition) {
    fromNode = source; toNode = dest;
 source.addOutArc(this);  dest.addInArc(this);     this.transition = transition;
  }
  private int transition;
  public int gettransition()
  { return transition; }
public Node getto(){
		return toNode;
	}
	
	public Node getfrom(){
		return fromNode;
	}

	public boolean setto(Node to){
		toNode = to;
		return true;
	}
	
	public boolean setfrom(Node from){
		fromNode = from;
		return true;
	}
	
	public boolean setNodes(Node from, Node to){
		fromNode = from;
		toNode = to;
		return true;
	}
	

	public FlowList<Node> getNodes(Node from, Node to){
		fromNode = from;
		toNode = to;
		FlowList<Node> twoNodes = new FlowList<Node>();
		twoNodes.add(from);
		twoNodes.add(to);
		return twoNodes;
	}} 