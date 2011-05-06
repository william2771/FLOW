package flow.structure;

import java.util.ArrayList;

public class SuperGraph {
	protected FlowList<SuperNode> nodes;
	protected FlowList<SuperArc> arcs;

	public SuperGraph(){
		nodes = new FlowList<SuperNode>();
		arcs = new FlowList<SuperArc>();
	}
	
	public FlowList<SuperNode> getNodes(){
		return nodes;
	}
	
	public FlowList<SuperArc> getArcs(){
		return arcs;
	}
	
	public int getNumNodes(){
		return nodes.size();
	}
	
	public int getNumArcs(){
		return arcs.size();
	}
}
