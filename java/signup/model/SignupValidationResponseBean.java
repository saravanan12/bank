package signup.model;

import java.util.HashMap;
import java.util.Map;

public class SignupValidationResponseBean {
		
		private boolean isValidParams = false;
		
		private Map<String,String> validationResponse = new HashMap<String,String>();

		public boolean isValidParams() {
			return isValidParams;
		}

		public void setIsValidParams(boolean isValidParams) {
			this.isValidParams = isValidParams;
		}

		public Map<String,String> getValidationResponse() {
			return validationResponse;
		}

		public void setValidationResponse(Map<String,String> validationResponse) {
			this.validationResponse = validationResponse;
		}
}
