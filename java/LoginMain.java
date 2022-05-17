import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import login.controller.LoginController;
import login.model.LoginModel;
import login.view.LoginResponse;


public class LoginMain extends ActionSupport {
	
	private Map<String, Map<String,String>> data = new HashMap<String, Map<String,String>>();
	private String customerId  = "";
	private String password = "";

	public String execute() {
		
		System.out.println("customerId : "+customerId+", password :"+password);
		
		
		LoginModel  loginProcessObject =  new LoginModel(customerId,password);
		LoginResponse loginResponse =  new LoginResponse();
		LoginController loginController =  new LoginController(loginProcessObject,loginResponse);
		data.put("response",loginController.verifyUserCredentials());//loginController.verifyUserCredentials().toString()
		System.out.println("end of LoginMain");
		return SUCCESS;
	}
	
//	public String getUserName() {
//		return userName;
//	}

	public void setCustomerId(String userName) {
		this.customerId = userName;
	}

//	public String getPassword() {
//		return password;
//	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Map<String, Map<String,String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String,String>> data) {
		this.data = data;
	}
	
	

}
