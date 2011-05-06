package flow.ast;
import java.util.ArrayList;
public class AttrList {
    Attr tail;
    AttrList alist;
    
    public AttrList(AttrList alist, Attr tail) {
        this.alist = alist;
        this.tail = tail;
    }
    
    public String toString() {
        if (alist != null) {
            return alist.toString() + ", " + tail.toString();
        }
        else {
            return tail.toString();
        }
    }

    public ArrayList<Attr> toArrayList() {
        ArrayList<Attr> result;
        if(alist != null) {
            result = new ArrayList<Attr>();
            result.add(tail);
            return result;
        }
        else {
            result = alist.toArrayList();
            result.add(tail);
            return result;
        } 
    }
}