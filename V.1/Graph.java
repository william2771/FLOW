import flow.structure.*;
import java.util.*;
public class Graph {
public Graph() {
nodes = new FlowList<Node>();
arcs = new FlowList<Arc>();
Node s0 = new Node("s0", 0);
nodes.add(s0);
START = s0;
Node s1 = new Node("s1", 0);
nodes.add(s1);
arcs.add(new Arc(s0, s1, "h"));
Node s2 = new Node("s2", 0);
nodes.add(s2);
arcs.add(new Arc(s1, s2, "e"));
Node s3 = new Node("s3", 0);
nodes.add(s3);
arcs.add(new Arc(s2, s3, "l"));
Node s4 = new Node("s4", 0);
nodes.add(s4);
arcs.add(new Arc(s3, s4, "l"));
Node s5 = new Node("s5", 0);
nodes.add(s5);
arcs.add(new Arc(s4, s5, "o"));
Node s6 = new Node("s6", 0);
nodes.add(s6);
arcs.add(new Arc(s5, s6, " "));
Node s7 = new Node("s7", 0);
nodes.add(s7);
arcs.add(new Arc(s6, s7, "w"));
Node s8 = new Node("s8", 0);
nodes.add(s8);
arcs.add(new Arc(s7, s8, "o"));
Node s9 = new Node("s9", 0);
nodes.add(s9);
arcs.add(new Arc(s8, s9, "r"));
Node s10 = new Node("s10", 0);
nodes.add(s10);
arcs.add(new Arc(s9, s10, "l"));
Node s11 = new Node("s11", 0);
nodes.add(s11);
arcs.add(new Arc(s10, s11, "d"));
Node s12 = new Node("s12", 1);
nodes.add(s12);
arcs.add(new Arc(s11, s12, "!"));
Node skill = new Node("kill", 0);
nodes.add(skill);
String[] lst_tmp = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " ", "!"};
FlowList<String> lst = new FlowList<String>((List<String>) Arrays.asList(lst_tmp));
Node[] dfaLst_tmp = {s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, skill};
FlowList<Node> dfaLst = new FlowList<Node>((List<Node>) Arrays.asList(dfaLst_tmp));
String[] strLst_tmp = {"h", "e", "l", "l", "o", " ", "w", "o", "r", "l", "d", "!", ""};
FlowList<String> strLst = new FlowList<String>((List<String>) Arrays.asList(strLst_tmp));
Integer pos = 0;
while (((pos < dfaLst.getlength()) ? 1 : 0)!= 0) {
Integer k = 0;
while (((k < lst.getlength()) ? 1 : 0)!= 0) {
String character = lst.get(k);
if (((!character.equals(strLst.get(pos))) ? 1 : 0) != 0) {
Node n = dfaLst.get(pos);
arcs.add(new Arc(n, skill, character));
}
;
k = k + 1;
}
;
pos = pos + 1;
}
;

}
private FlowList<Arc> arcs; public FlowList<Arc> getarcs() { return arcs; }
 private FlowList<Node> nodes; public FlowList<Node> getnodes() { return nodes; } 
 public int getnumNodes(){return nodes.size();} public int getnumArcs(){ return arcs.size();}
private Node START;
public Node getSTART() {
  return START;
}

}
