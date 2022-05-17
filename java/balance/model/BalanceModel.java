package balance.model;

import java.util.Map;

import balance.dao.BalanceDAO;

public class BalanceModel {

	
	private String accountId="";
	public BalanceModel(String accountId) {
		this.accountId = accountId;
	}
	
	public Map<String,String> getBalance() {
		System.out.println("accountId "+accountId);
		BalanceDAO balanceDao = new BalanceDAO(this);
		Map<String,String> balanceMap = balanceDao.getBalance();
		System.out.println("balance  :"+balanceMap);
		return balanceMap;
	}
	
	public double getBalanceAmount() {
		
		BalanceDAO balanceDao = new BalanceDAO(this);
		Map<String,String> balanceMap = balanceDao.getBalance();
		return Double.parseDouble(balanceMap.get("balance"));
	}
	
	public Map<String,String> updateBalance(Double amount) {
		
		BalanceDAO balanceDAO = new BalanceDAO(this);
		Map<String,String>  balanceMap = getBalance();
		if(balanceMap.get("result").equals("1")) {
			double balance = Double.parseDouble(balanceMap.get("balance"));
			double currentBalance = balance+(amount);
			balanceDAO.updateBalace(currentBalance);
			balanceMap.put("balance", currentBalance+"");
		}
		return balanceMap;
	}
	
	public boolean setBalance(Double amount) {
		
		BalanceDAO balanceDAO = new BalanceDAO(this);
		return balanceDAO.updateBalace(amount);
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
