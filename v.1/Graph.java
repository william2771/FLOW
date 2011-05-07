import flow.structure.*;
import java.util.ArrayList;
public class Graph {
public Graph() {
nodes = new FlowList<Node>();
arcs = new FlowList<Arc>();
Node Evenstate = new Node(1);
nodes.add(Evenstate);
START = Evenstate;
Node Oddstate = new Node(0);
nodes.add(Oddstate);
arcs.add(new Arc(Evenstate, Evenstate, 0));
arcs.add(new Arc(Oddstate, Oddstate, 0));
arcs.add(new Arc(Evenstate, Oddstate, 1));
arcs.add(new Arc(Oddstate, Evenstate, 1));

}
private FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }
 private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } 
 public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}
private Node START;
public Node getSTART() {
  return START;
}

}
