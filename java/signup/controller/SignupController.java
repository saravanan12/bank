package signup.controller;

import signup.model.SignupModel;
import signup.model.SignupValidationResponseBean;

import java.util.Map;

import signup.model.ResponseOfUserRegister;
import signup.view.SignupResponseHandler;
import signup.view.SignupResponseHandler.FIELDS;

public class SignupController {
	
	private SignupModel signupModel;
	private SignupResponseHandler signupResponseHandler;
	
	public SignupController(SignupModel signupModel, SignupResponseHandler signupResponseHandler) {
			this.signupModel = signupModel;
			this.signupResponseHandler =  signupResponseHandler;
	}
	
	public SignupValidationResponseBean checkSignupParams() {
		System.out.println("enter into checkSignupParams");
		SignupValidationResponseBean validationResponseBean = new SignupValidationResponseBean();
		boolean isValidParams = true;
		try {
			if(!signupModel.isValidUserName()) {
				validationResponseBean.setValidationResponse(signupResponseHandler.getErrorMessage(FIELDS.USERNAME));
				isValidParams = false;
			}
			else if(!signupModel.isValidaPassword()) {
				validationResponseBean.setValidationResponse(signupResponseHandler.getErrorMessage(FIELDS.PASSWORD));
				isValidParams = false;
			}else if(!signupModel.isValidateEmailId()) {
				validationResponseBean.setValidationResponse(signupResponseHandler.getErrorMessage(FIELDS.EMAILID));
				isValidParams = false;
			}else if(!signupModel.isValidateGender()) {
				validationResponseBean.setValidationResponse(signupResponseHandler.getErrorMessage(FIELDS.GENDER));
				isValidParams = false;
			}
			
		}catch(Exception e) {
			isValidParams = false;
			e.printStackTrace();
			System.out.println("checkSignupParams error message of exeption "+e.getMessage());
		}
		System.out.println("checkSignupParams isValidParams "+isValidParams);
		validationResponseBean.setIsValidParams(isValidParams);
		return validationResponseBean;
	}
	
	public Map<String,String> registerUser() {
		try {
			//Add user details in db;
			String customerId = signupModel.addUser();
			if(!customerId.isEmpty()) {
				return signupResponseHandler.RegisterationSuccessOrFailureResponse(signupModel.getEmailId(),customerId,true);
			}else {
				return signupResponseHandler.RegisterationSuccessOrFailureResponse(signupModel.getEmailId(),customerId,false);
			}
		}catch (Exception e) {
			return signupResponseHandler.RegisterationSuccessOrFailureResponse(signupModel.getEmailId(),"",false);
		}
		
	}
	
}
