public class Node{
   public Node()
   {
      /* default constructor does nothing! */
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

   int getDegree() {return degree;}
   int getInDegree() {return inDegree;}
   int getOutDegree() {return outDegree;}

   ArrayList<Arc> getArcs() {return arcs;}
   ArrayList<Arc> getArcsIn() {return arcsOut;}
   ArrayList<Arc> getArcsOut() {return arcsIn;}

   int inDegree;
   int outDegree;
   int degree;

   ArrayList<Arc> arcsIn;
   ArrayList<Arc> arcsOut;
   ArrayList<Arc> arcs;
}