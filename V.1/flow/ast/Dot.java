package flow.ast;

/* The dot operator used to access the attributes of Nodes and Arcs */
public class Dot extends Expression {

	public Dot(ID id, String field)
	{
		this.id = id;
		this.field = field;
	}

	private ID id;
	private String field;

    public String getField(){
	return field;
    }

    public ID getId(){
	return id;
    }

	public String toString()
	{
		return id.toString() + ".get" + field + "()";
	}
}