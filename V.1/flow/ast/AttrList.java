package flow.ast;
import java.util.ArrayList;
public class AttrList {
    Expression tail;
    AttrList alist;
    
    public AttrList(AttrList alist, Expression tail) {
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

    public ArrayList<Expression> toArrayList() {
        ArrayList<Expression> result;
        if(alist == null) {
            result = new ArrayList<Expression>();
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