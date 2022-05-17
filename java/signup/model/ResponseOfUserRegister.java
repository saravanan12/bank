package signup.model;

public class ResponseOfUserRegister {
	
	private String emailId;
	
	private int status;
	
	private boolean success;
	
	private String message;
	
	
	public ResponseOfUserRegister(String email, int status, boolean success, String message) {
		this.emailId = email;
		this.status = status;
		this.success =  success;
		this.message = message;
				
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
		
		

}
