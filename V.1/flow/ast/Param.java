package flow.ast;

public class Param {
	public Type type;
	public ID id;

	public Param(Type type, ID id) {
		this.type = type;
		this.id = id;
	}

	public String toString() {
		return "" + type + " " + id;
	}
}
