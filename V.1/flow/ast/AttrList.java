package flow.ast;

public class AttrList {
    Attribute head;
    AttrList tail;
    
    public AttributeListNode(Attribute head, AttributeListNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    public String toString(){
    	return head +", "+ tail;
    }
}