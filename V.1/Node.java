public class Node extends flow.structure.SuperNode {
  public Node(int isAccepting) {
    this.isAccepting = isAccepting;
  }
  private int isAccepting;
  public int getisAccepting()
  { return isAccepting; }
}