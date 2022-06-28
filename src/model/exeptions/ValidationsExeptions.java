package model.exeptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationsExeptions extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errors = new HashMap<>();
	

	public  ValidationsExeptions(String msg) {
		super(msg);
	}
	
	public Map<String,String> getErros(){
		return errors;
	}
	
	public void addError(String field, String erroMsg) {
		errors.put(field, erroMsg);
	}
	
}
