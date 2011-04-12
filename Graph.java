import java.util.ArrayList;


public class Graph {
	private ArrayList<Node> nodes;
	private ArrayList<Arc> arcs;

	public Graph(ArrayList<Node> vertices, ArrayList<Arc> edges){
		nodes = vertices;
		arcs = edges;
	}
	
	public ArrayList<Node> getNodes(){
		return nodes;
	}
	
	public ArrayList<Arc> getArcs(){
		return arcs;
	}
	
	public int getNumNodes(){
		return nodes.size();
	}
	
	public int getNumArcs(){
		return arcs.size();
	}
}
