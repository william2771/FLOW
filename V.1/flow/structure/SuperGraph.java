package flow.structure;

import java.util.ArrayList;

public class SuperGraph {
	private ArrayList<SuperNode> nodes;
	private ArrayList<SuperArc> arcs;

	public SuperGraph(){
		nodes = new ArrayList<SuperNode>();
		arcs = new ArrayList<SuperArc>();
	}
	
	public ArrayList<SuperNode> getNodes(){
		return nodes;
	}
	
	public ArrayList<SuperArc> getArcs(){
		return arcs;
	}
	
	public int getNumNodes(){
		return nodes.size();
	}
	
	public int getNumArcs(){
		return arcs.size();
	}
}
