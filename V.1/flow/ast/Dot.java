package flow.ast;

/* The dot operator used to access the attributes of Nodes and Arcs */
public class Dot extends Expression {

	public Dot(ID id, ID field)
	{
		this.id = id;
		field = field;
	}

	private ID id;
	private ID field;

	public String toString()
	{
		return id.toString() + ".get" + field.toString() + "()";
	}
}