package flow.ast;


public class ID {
    
    String idname;
    
    public ID( String idname) {
        this.idname = idname;
    }    
    
    public String toString(){
    	return idname;
    }
}