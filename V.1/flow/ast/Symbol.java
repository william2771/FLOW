package flow.ast;

public class Symbol
{
	private String id;
	private String type;
	
	public Symbol(String id, String type)
	{
		this.id = id;
		this.type = type;
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getType()
	{
		return type;
	}
	

}