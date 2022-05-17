package balance.controller;

import java.util.Map;

import balance.dao.BalanceDAO;
import balance.model.BalanceModel;
import balance.view.BalanceResponseHandler;

public class BalanceController {
	
	
	private BalanceModel balanceModel;
	private BalanceResponseHandler balanceResponseHandler;
	
	public BalanceController(BalanceModel balanceModel, BalanceResponseHandler balanceResponseHandler) {
		
		this.balanceModel = balanceModel;
		this.balanceResponseHandler = balanceResponseHandler;
		
	}
	
	public Map<String,String> getBalance() {
		return balanceModel.getBalance();
	}
	
	public Map<String,String> updateBalance(double amount) {
		
		return balanceModel.updateBalance(amount);
	
	}
	
	
	

}
