package flow.ast;

public class pValue extends Attr {

	public pValue(int val)
	{
		this.val = val;
	}
	private int val;

	public String toString()
	{
		return "" + val;
	}
}