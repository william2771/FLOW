package flow.structure;

import java.util.ArrayList;

public class SuperGraph {
	protected ArrayList<SuperNode> nodes;
	protected ArrayList<SuperArc> arcs;

	public SuperGraph(){
		nodes = new ArrayList<SuperNode>();
		arcs = new ArrayList<SuperArc>();
	}
	
	public ArrayList<SuperNode> getnodes(){
		return nodes;
	}
	
	public ArrayList<SuperArc> getnrcs(){
		return arcs;
	}
	
	public int getnumNodes(){
		return nodes.size();
	}
	
	public int getnumArcs(){
		return arcs.size();
	}
}
