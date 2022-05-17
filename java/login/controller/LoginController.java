package login.controller;


import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import login.model.LoginModel;
import login.view.LoginResponse;

public class LoginController {
	
	private LoginModel loginProcessHandler;
	private LoginResponse loginResponseHandler;
	
	
	public LoginController(LoginModel loginObject,LoginResponse responseObject) {
		this.loginProcessHandler = loginObject;
		this.loginResponseHandler = responseObject;
		
	}
	
	public Map<String,String> verifyUserCredentials() {
		System.out.println("enter into verifyUserCredentials");
		
	    if(loginProcessHandler.paramsValidation()) {
			
			boolean checkUserLogin =  loginProcessHandler.checkLoginUserCredentials();
			
			return loginResponseHandler.getResponseOfLoginStatus(checkUserLogin);
		
		}else {
			
			return loginResponseHandler.getResponseOfInvalidParams();
			
		}
		
		
	}

}
