import java.util.*;
import flow.structure.*;

public class Solver {
public static void main(String[] args) {
graph = new Graph();
Integer[] binary_tmp = {1, 0, 1, 1, 0, 0, 1, 0};
FlowList<Integer> binary = new FlowList<Integer>((List<Integer>) Arrays.asList(binary_tmp));
int count = binary.getlength() - 1;
Node current = graph.getSTART();
while (((count > 0) ? 1 : 0)!= 0) {
FlowList<Arc> fromArcs = new FlowList<Arc>();
fromArcs = current.getarcsOut();
int k = fromArcs.getlength() - 1;
while (((k >= 0) ? 1 : 0)!= 0) {
Arc myArc = fromArcs.get(k);
if (((myArc.gettransition() == binary.get(count)) ? 1 : 0) != 0) {
current = myArc.getto();
}
;
k = k - 1;
}
;
count = count - 1;
}
;
if (((current.getisAccepting() == 1) ? 1 : 0) != 0) {
System.out.println("Even parity");
}
;
if (((current.getisAccepting() != 1) ? 1 : 0) != 0) {
System.out.println("Odd parity");
}
;
}
private static Graph graph;
}