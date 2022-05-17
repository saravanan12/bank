package login.model;

import login.dao.LoginDao;
import loginUtil.LoginUtil;

public class LoginModel {
	
	private String customerId;
	private String password;
	
	public LoginModel(String customerId,String password) {
		this.customerId =  customerId;
		this.password = password;
				
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public boolean paramsValidation() {
		System.out.println("enter into paramsValidation , customerId :"+customerId+", password:"+password);
		if(LoginUtil.isNotNullAndNotEmpty(customerId) &&  LoginUtil.isNotNullAndNotEmpty(password)) {
				return true;
		}
		
		return false;
		
	}
	
	public boolean checkLoginUserCredentials() {
		
		LoginDao loginDao =  new LoginDao(this);
		return loginDao.checkLoginCredential();
	}

	

}
