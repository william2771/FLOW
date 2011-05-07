import flow.structure.*;

public class Solver {
public static void main(String[] args) {
graph = new Graph();
FlowList<Arc> arcList = new FlowList<Arc>();
arcList = graph.getarcs();
int k = 0;
int arcSum = 0;
while (((k < arcList.getlength()) ? 1 : 0)!= 0) {
Arc arc = arcList.get(k);
arcSum = arcSum + arc.gettransition();
k = k + 1;
}
;
System.out.println(arcSum);
FlowList<Node> nodeList = new FlowList<Node>();
nodeList = graph.getnodes();
int j = 0;
int nodeSum = 0;
while (((j < nodeList.getlength()) ? 1 : 0)!= 0) {
Node node = nodeList.get(j);
nodeSum = nodeSum + node.getisAccepting();
j = j + 1;
}
;
System.out.println(nodeSum);
}
private static Graph graph;
}