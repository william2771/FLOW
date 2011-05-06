public class Graph extends flow.structure.SuperGraph
{
public Graph() {
super();
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
private Node START;
public Node START() {
  return START;
}

}
