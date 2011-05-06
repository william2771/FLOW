package flow.ast;

public class pValue extends Attr {

	private Integer ival;
	private Double dval;
	private String sval;

	public pValue(int val)
	{
	    super();
		ival = val;
	}

	public pValue(double val)
	{
	    super();

		dval = val;
	}

	public pValue(String val)
	{
	    super();

		sval = val;
	}

	public String toString()
	{
		if (ival != null) return "" + ival;
		else if (dval != null) return "" + dval;
		else return sval;
	}
}