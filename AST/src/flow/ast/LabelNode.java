package flow.ast;

public class LabelNode extends StatementNode {

	private ID id;
	private NodeDec dec;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "public Node " + id.toString() + "(){\nreturn " + id + ";\n}";
	}

}
