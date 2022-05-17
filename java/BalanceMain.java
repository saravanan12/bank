import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import accounts.model.AccountModel;
import balance.controller.BalanceController;
import balance.model.BalanceModel;
import balance.view.BalanceResponseHandler;

public class BalanceMain extends ActionSupport {

	
	private Map<String, Map<String,String>> data = new HashMap<String, Map<String,String>>();
	private String customerId;
	
	public String execute() {
		AccountModel accountModel = new AccountModel(customerId);
		Map userDetailsMap = accountModel.getUserDetails();
		BalanceModel balanceModel =  new BalanceModel(userDetailsMap.get("accountId")+"");
		BalanceResponseHandler balanceResponseHandler = new BalanceResponseHandler();
		BalanceController balanceController = new BalanceController(balanceModel, balanceResponseHandler);
		userDetailsMap.put("balance", balanceController.getBalance().get("balance").toString());
		data.put("response", userDetailsMap);
		return SUCCESS;
	}
	public Map<String, Map<String, String>> getData() {
		return data;
	}
	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
