package flow.ast;

import java.util.*;


public class SymbolTable
{
	private HashMap<String,Symbol> table;
	
	public SymbolTable()
	{
		table = new HashMap<String,Symbol>();
		
	}
	
	public void add(Symbol s)
	{
		table.put(s.getID(), s);
		
	}
	
	public Symbol get(String id)
	{
		return table.get(id);
	}
	
	public boolean contains(String id)
	{
		return table.get(id) != null;
	}
}