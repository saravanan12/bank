package balance.view;

import java.util.HashMap;
import java.util.Map;

public class BalanceResponseHandler {
	public static final int SUCCESS_STATUS = 1 ;
	public static final int FAILURE_STATUS = 0 ;
	
	public void getBalanceResponse(double balance) {
		HashMap<String, String> response = new HashMap<String, String>();
		response.put("status", SUCCESS_STATUS+"");
		response.put("balance", balance+"");
		
	}
	
	
	public Map<String, String> balanceFailureResponse() {
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("status", FAILURE_STATUS+"");
		responseMap.put("message", "Something went wrong ");
		responseMap.put("success", false+"");
		responseMap.put("code", "7324");
		
		return responseMap;
		
	}
}
