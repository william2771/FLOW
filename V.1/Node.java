import flow.structure.*; public class Node {
  public Node(String value, int isAccepting) {
 arcsIn = new FlowList<Arc>(); arcsOut = new FlowList<Arc>(); arcs = new FlowList<Arc>(); degree = inDegree = outDegree = 0;    this.value = value;
    this.isAccepting = isAccepting;
  }
 void addInArc(Arc in)
   {
      arcsIn.add(in);
      arcs.add(in);
      inDegree++;
      degree++;
   }

   void addOutArc(Arc out)
   {
      arcsOut.add(out);
      arcs.add(out);
      outDegree++;
      degree++;
   }

   int getdegree() { return degree; }
   int getdnDegree() { return inDegree; }
   int getoutDegree() { return outDegree; }

   FlowList<Arc> getarcs() { return arcs; }
   FlowList<Arc> getarcsIn() { return arcsOut; }
   FlowList<Arc> getarcsOut() { return arcsIn; }

   int inDegree;
   int outDegree;
   int degree;

   FlowList<Arc> arcsIn;
   FlowList<Arc> arcsOut;
   FlowList<Arc> arcs;  private String value;
  public String getvalue()
  { return value; }
  private int isAccepting;
  public int getisAccepting()
  { return isAccepting; }
}