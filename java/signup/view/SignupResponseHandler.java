package signup.view;

import java.util.HashMap;
import java.util.Map;

import signup.model.SignupModel;
import signup.model.ResponseOfUserRegister;

public class SignupResponseHandler {
	
	public enum FIELDS{USERNAME,PASSWORD,EMAILID,GENDER};
	private enum STATUS{FAILURE_STATUS(0),SUCCESS_STATUS(1);
		

	private int status;

	STATUS(int status) {
		this.status = status;
	}};
	
	public Map<String, String> getErrorMessage(FIELDS fieldKey){
		Map<String, String> errorMap = new HashMap<String, String>();
		int code = 0;
		String message = "";
		String errorFieldKey = "";
		switch (fieldKey) {
			case USERNAME -> {
				code = 7320;
				message = "Please enter valid user name";
				errorFieldKey = "userName";
			}
			case PASSWORD -> {
				code = 7321;
				message = "Password is not valid";
				errorFieldKey = "password";
			}
			case EMAILID -> {
				code = 7322;
				message = "Please enter valid email";
				errorFieldKey = "emailId";
			}
			case GENDER -> {
				code = 7323;
				message = "Please choose valid gender";
				errorFieldKey = "gender";
			}
		}
		errorMap.put("key", errorFieldKey);
		errorMap.put("code", code+"");
		errorMap.put("status", STATUS.FAILURE_STATUS.status+"");
		errorMap.put("message", message);
		errorMap.put("success", false+"");
		return errorMap;
	}
	
	public Map<String, String> RegisterationSuccessOrFailureResponse(String emailId,String customerId,boolean isRegisterSucceed) {
		String message = "Registration failed ";
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("emailId", emailId);
		if(isRegisterSucceed) {
			message = "Successfully registered ";
			responseMap.put("customerId", customerId);
			responseMap.put("status", STATUS.SUCCESS_STATUS.status+"");
		}else {
			responseMap.put("status", STATUS.FAILURE_STATUS.status+"");
		}
		responseMap.put("message", message+emailId);
		responseMap.put("success", isRegisterSucceed+"");
		
//		return new ResponseOfUserRegister(signupModel.getEmailId(),STATUS.SUCCESS_STATUS.status,true,"Successfully regitered "+signupModel.getEmailId());
		return responseMap;
		
	}
	
	
	

}
