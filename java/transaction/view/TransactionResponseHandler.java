package transaction.view;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import login.view.LoginResponse;

public class TransactionResponseHandler {

	public enum ERROR{LIMIT_REACHED,BALANCE_CHECK,SOMETHING_WRONG}
	public Map<String,String> getErrorMsgOfValidation(ERROR key) {
		Map<String,String> response = new HashedMap();
		String message = "Something went wrong";
		if(key.equals(ERROR.LIMIT_REACHED)) {
			message  = "You are exceed the limit of transaction today";
		}else if(key.equals(ERROR.BALANCE_CHECK)) {
			message  = "Transaction failed due to the invalid amount"; //Balance error message
		}
		response.put("message", message);
		response.put("status", LoginResponse.FAILURE_STATUS+"");
		response.put("code","7327");
		return response;
	}
	public Map<String,String> getSuccessMsgOfTransaction(String balance) {
		Map<String,String> response = new HashedMap();
		response.put("message", "Transaction successfully completed");
		response.put("status", LoginResponse.SUCCESS_STATUS+"");
		response.put("code","7328");
		response.put("balance",balance);
		return response;
	}
	
	public  Map<String,String> getErrorMsgOfInvalidAccount() {
		Map<String,String> response = new HashedMap();
		response.put("message", "Please enter valid benificiary Id");
		response.put("status", LoginResponse.FAILURE_STATUS+"");
		response.put("code","7329");
		return response;
	}
}
