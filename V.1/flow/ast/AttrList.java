package flow.ast;

public class AttrList {
    Attr tail;
    AttrList alist;
    
    public AttrList(AttrList alist, Attr tail) {
        this.alist = alist;
        this.tail = tail;
    }
    
    public String toString() {
	if (alist != null) return alist.toString() + ", " + tail.toString();
	else return tail.toString();
    }
}