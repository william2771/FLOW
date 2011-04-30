package flow.ast;

/////////////////////
public class ParamList {
	ParamList paramList;
	Param param;

	public ParamList(ParamList paramList, Param param) {
		this.paramList = paramList;
		this.param = param;
	}


	public String toString() {
		String s = "";
		if(paramList != null) {
			s += paramList + " ,";
		}
		return s + param;
	}
}
