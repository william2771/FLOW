import flow.structure.*;
import java.util.ArrayList;
public class Graph {
public Graph() {
nodes = new FlowList<Node>();
arcs = new FlowList<Arc>();
Node s0 = new Node("s0", 1);
nodes.add(s0);
START = s0;
Node s1 = new Node("s1", 0);
nodes.add(s1);
Node s2 = new Node("s2", 0);
nodes.add(s2);
Node s3 = new Node("s3", 0);
nodes.add(s3);
arcs.add(new Arc(s0, s1, "a"));
arcs.add(new Arc(s1, s0, "a"));
arcs.add(new Arc(s1, s2, "b"));
arcs.add(new Arc(s2, s1, "b"));
arcs.add(new Arc(s2, s3, "a"));
arcs.add(new Arc(s3, s2, "a"));
arcs.add(new Arc(s3, s0, "b"));
arcs.add(new Arc(s0, s3, "b"));

}
private FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }
 private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } 
 public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}
private Node START;
public Node getSTART() {
  return START;
}

}
