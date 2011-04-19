package flow.ast;

public class AttributeListNode {
    Attribute head;
    AttributeListNode tail;
    
    public AttributeListNode(Attribute head, AttributeListNode tail) {
        this.head = head;
        this.tail = tail;
    }
    
    public String toString(){
    	return head +","+ tail;
    }
}