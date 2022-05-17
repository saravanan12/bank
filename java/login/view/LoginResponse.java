package login.view;

import java.util.HashMap;
import java.util.Map;

public class LoginResponse {
	
	public static final int SUCCESS_STATUS = 1 ;
	public static final int FAILURE_STATUS = 0 ;
	
	public Map<String, String> getResponseOfLoginStatus(boolean isVerified) {
		
		HashMap<String, String> response = new HashMap<String, String>();
		int status = FAILURE_STATUS;
		int loginSuccess = FAILURE_STATUS;
		if(isVerified) {
			loginSuccess = status = SUCCESS_STATUS;
			response.put("message", "Successfully login");
		}else {
			response.put("message", "Username or Password mismatched");
		}
		response.put("status", status+"");
		response.put("isLoginSuccess", loginSuccess+"");
		return response;
	}
	
	public Map<String, String> getResponseOfInvalidParams() {
		
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("status", FAILURE_STATUS+"");
		response.put("message", "Invaid type of params please provide valid credentials");
		return response;
		
	}

}
