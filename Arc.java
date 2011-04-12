import java.util.ArrayList;


public class Arc {
	private Node fromNode;
	
	private Node toNode;
	
	public Arc(Node from, Node to){
		fromNode = from;
		toNode = to;
	}
	
	public Node getToNode(){
		return toNode;
	}
	
	public Node getFromNode(){
		return fromNode;
	}

	public boolean setToNode(Node to){
		toNode = to;
		return true;
	}
	
	public boolean setFromNode(Node from){
		fromNode = from;
		return true;
	}
	
	public boolean setNodes(Node from, Node to){
		fromNode = from;
		toNode = to;
		return true;
	}
	
	public ArrayList<Node> getNodes(Node from, Node to){
		fromNode = from;
		toNode = to;
		ArrayList<Node> twoNodes = new ArrayList<Node>();
		twoNodes.add(from);
		twoNodes.add(to);
		return twoNodes;
	}
}
