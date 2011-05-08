import java.util.*;
import flow.structure.*;

public class Solver {
public static void main(String[] args) {
graph = new Graph();
;
String[] input_tmp = {"H", "e", "l", "l", "o", " ", "W", "o", "r", "l", "d"};
FlowList<String> input = new FlowList<String>((List<String>) Arrays.asList(input_tmp));
Integer truth = 0;
if (simulate(input) != 0) {
truth = 1;
System.out.println("Input is accepted.\n");
}
;
if (((truth == 0) ? 1 : 0) != 0) {
System.out.println("Input is not accepted.\n");
}
;
}
private static Graph graph;
public static int simulate (FlowList<String> input) {
Node current = graph.getSTART();
System.out.println("The first current variable is set.");
System.out.println("Entering the for loop to look through the arcs.");
Integer i = 0;
while (((i < input.getlength()) ? 1 : 0)!= 0) {
FlowList<Arc> pathLst = new FlowList<Arc>();
pathLst = current.getarcsOut();
Arc next = pathLst.get(0);
Integer j = 0;
while (((j < pathLst.getlength()) ? 1 : 0)!= 0) {
Arc connected = pathLst.get(j);
if (((connected.getsymbol().equals(input.get(i))) ? 1 : 0 ) != 0) {
next = connected;
System.out.println("Went to the next edge.");
}
;
j = j + 1;
}
;
Integer isNext = 0;
if (next != null) {
isNext = 1;
System.out.println("Navigating to node at the end of that edge.");
current = next.getto();
}
;
if (((isNext == 0) ? 1 : 0) != 0) {
System.out.println("There was a problem.  Now exiting.");
return 0;
}
;
i = i + 1;
}
;
if (current.getisAccepting() != 0) {
return 1;
}
;
return 0;

}
}