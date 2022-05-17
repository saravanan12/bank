import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import signup.controller.SignupController;
import signup.model.ResponseOfUserRegister;
import signup.model.SignupModel;
import signup.model.SignupValidationResponseBean;
import signup.view.SignupResponseHandler;

public class SignupMain extends ActionSupport {
	
	private String firstName="";
	private String lastName="";
	private String emailId="";
	private String password="";
	private String phone="";
	private String gender="";
	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();

	public String execute() {
		
		try {
			System.out.println("email: " + emailId);
			SignupModel signupProcess = new SignupModel(firstName, lastName, password, emailId, phone, gender);
			SignupResponseHandler responseHandler = new SignupResponseHandler();
			SignupController signupController = new SignupController(signupProcess, responseHandler);
			SignupValidationResponseBean validateOfParams = signupController.checkSignupParams();
			if (validateOfParams.isValidParams()) {
				Map<String, String> response = signupController.registerUser();
				data.put("response", response);
			} else {
				data.put("response", validateOfParams.getValidationResponse());
			}
			System.out.println("validateOfParams is : " + validateOfParams.getValidationResponse());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e  : " + e.getMessage());
			Map<String, String>  error = new HashMap<String, String>();
			error.put("errorMessage", e.getMessage());
			data.put("response", error);
		}
		
		return SUCCESS;
	}


//	public String getFirstName() {
//		return firstName;
//	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




//	public String getEmailId() {
//		return emailId;
//	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


//	public String getPassword() {
//		return password;
//	}


	public void setPassword(String password) {
		this.password = password;
	}


//	public String getPhone() {
//		return phone;
//	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


//	public String getGender() {
//		return gender;
//	}


	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Map<String, Map<String, String>> getData() {
	    return data;
	}

	public void setData(Map<String, Map<String, String>> maps) {
	    this.data = maps;
	}

}
