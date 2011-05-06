package flow.structure;

import java.util.ArrayList;

public class SuperNode{
   public SuperNode()
   {
      /* default constructor does nothing! pretty much */
      degree = inDegree = outDegree = 0;
   }

   void addInArc(SuperArc in)
   {
      arcsIn.add(in);
      arcs.add(in);
      inDegree++;
      degree++;
   }

   void addOutArc(SuperArc out)
   {
      arcsOut.add(out);
      arcs.add(out);
      outDegree++;
      degree++;
   }

   int getDegree() { return degree; }
   int getInDegree() { return inDegree; }
   int getOutDegree() { return outDegree; }

   FlowList<SuperArc> getArcs() { return arcs; }
   FlowList<SuperArc> getArcsIn() { return arcsOut; }
   FlowList<SuperArc> getArcsOut() { return arcsIn; }

   int inDegree;
   int outDegree;
   int degree;

   FlowList<SuperArc> arcsIn;
   FlowList<SuperArc> arcsOut;
   FlowList<SuperArc> arcs;
}