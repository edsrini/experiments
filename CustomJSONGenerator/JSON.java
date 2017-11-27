package test1;

import java.util.LinkedList;
import java.util.List;

public class JSON{
	static JSONObject jsonObject;
	static String spacer="-";

	private JSON(){
		
	}

	
	public static JSONObject jsonBuilder(){
		if(spacer.equals("-")){
			spacer = "";
		}
		else{
			spacer += " ";
		}
		return new JSONObject(spacer);
	}
	
	public static JSONArray createJSONArray(){
		spacer += " ";
		return new JSONArray(spacer);
	}

}
class JSONArray{
	List<JSONObject> jsonArray = new LinkedList<JSONObject>();
	static String spacer = "-";
	
	protected JSONArray(String spacer){
		if(this.spacer.equals("-")){
			this.spacer = spacer;
		}
		
		//this.spacer = spacer;
	}

	
	public JSONArray add(JSONObject jsonObject){
		jsonObject.setSpacer(this.spacer+" ");
		jsonArray.add(jsonObject);
		
		List<ObjectMapper> mapper = jsonObject.getMapper();
		//Adjust tabs while rendering JSON payload.
		for(ObjectMapper obj : mapper){
			obj.spacer = jsonObject.spacer;
		}
		return this;
	}
	
	@Override
	public String toString() {
		if(jsonArray.size() <= 0){
			return "";
		}
		String result = "[";
		int count = 0;
		for(JSONObject obj : jsonArray){
			count ++;
			if(count < jsonArray.size()){
				result=result+"\n"+obj.toString()+",";
			}
			else{
				result=result+"\n"+obj.toString();
			}
			
		}
		return result+"\n"+spacer+"]";
	}
}

class JSONObject{
	List<ObjectMapper> mapper = new LinkedList<ObjectMapper>();
	public List<ObjectMapper> getMapper() {
		return mapper;
	}

	public void setMapper(List<ObjectMapper> mapper) {
		this.mapper = mapper;
	}
	String spacer="";
	
	public void setSpacer(String spacer) {
		this.spacer = spacer;
	}

	protected JSONObject(String spacer){
		this.spacer = spacer;
	}
	
	
	
	public JSONObject add(String key, Object value){
		mapper.add(new ObjectMapper(key, value, this.spacer));
		return this;
	}
	@Override
	public String toString() {
		if(mapper.size() <= 0){
			return "";
		}
		String result = spacer+"{";
		int count = 0;
		for(ObjectMapper obj : mapper){
			count ++;
			if(count < mapper.size()){
				result=result+"\n"+obj.toString()+",";
			}
			else{
				result=result+"\n"+obj.toString();
			}
			
		}
		return result+"\n"+spacer+"}";
	}
}

class ObjectMapper{
	String key;
	Object value;
	String spacer="";
	protected ObjectMapper(String key, Object value, String spacer) {
		this.key = key;
		this.value = value;
		this.spacer = spacer;
	}
	
	public String getKey() {
		return key;
	}
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		String result = spacer+" \""+key+"\": ";
		if(value instanceof String){
			result=result+"\""+value+"\"";
		}
		else{
			result=result+value;
		}
		
		return result;
	}
}



	
	

