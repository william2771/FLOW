package flow.ast;

/* The dot operator used to access the attributes of Nodes and Arcs */
public class StrDot extends Dot {

	public StrDot(ID id, String field)
	{
	    super(id,field);
	}

	public String toString()
	{
	    return getId().toString() + "." + getField() + "()";
	}
}