package flow.ast;

import java.util.ArrayList;

public class ParamList {
	ParamList paramList;
	Param param;

	public ParamList(ParamList paramList, Param param) {
		this.paramList = paramList;
		this.param = param;
	}


    public ArrayList<Param> toArrayList(){
	ArrayList<Param> arr;
	if (paramList == null){
	    arr = new ArrayList<Param>();
	}
	else{
	    arr = paramList.toArrayList();
	}
	arr.add(param);
	return arr;
    }

	public String toString() {
		String s = "";
		if(paramList != null) {
			s += paramList + " ,";
		}
		return s + param;
	}
}
