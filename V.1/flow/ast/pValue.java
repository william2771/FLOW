package flow.ast;

public class pValue extends Attr {

	private Integer ival;
	private Double dval;
	private String sval;

	public pValue(int val)
	{
		ival = val;
	}

	public pValue(double val)
	{
		dval = val;
	}

	public pValue(String val)
	{
		sval = val;
	}

	public String toString()
	{
		if (ival != null) return "" + ival;
		else if (dval != null) return "" + dval;
		else return sval;
	}
}