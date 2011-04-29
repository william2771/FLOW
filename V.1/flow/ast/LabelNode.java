package flow.ast;

public class LabelNode extends StatementNode {

	public LabelNode(ID label, NodeDec dec)
	{
		this.label = label;
		this.dec = dec;
	}

	ID label;
	NodeDec dec;
	@Override
	public String toString() {
		return dec.toString() + ";\n" + label + " = " + dec.id;
	}
}
