public class Arc extends flow.structure.SuperArc {
  public Arc(Node source, Node dest, int transition) {
    super(source, dest);
    this.transition = transition;
  }
  private int transition;
  public int gettransition()
  { return transition; }
}