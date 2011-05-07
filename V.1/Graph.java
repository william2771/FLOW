import flow.structure.*;
import java.util.ArrayList;
public class Graph {
public Graph() {
nodes = new FlowList<Node>();
arcs = new FlowList<Arc>();
Node a = new Node();
nodes.add(a);
ROOT = a;
Node b = new Node();
nodes.add(b);
Node c = new Node();
nodes.add(c);
arcs.add(new Arc(a, b, null));
arcs.add(new Arc(a, c, null));

}
private FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }
 private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } 
 public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}
private Node ROOT;
public Node getROOT() {
  return ROOT;
}

}
