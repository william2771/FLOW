package flow.ast;
import java.util.*;
public class fType extends Type {
    public ArrayList<Param> paramTypes;
    
    public fType(String type,ParamList params){
        super(type);
        this.paramTypes = params.toArrayList();
    }
}