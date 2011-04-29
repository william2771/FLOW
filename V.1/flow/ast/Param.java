////////////////////
public class Param {
	Type type;
	ID id;

	Param(Type type, ID id) {
	
	this.type = type;
		this.id = id;
	}

	public String toString() {
		return "" + type + " " + id;
	}
}
