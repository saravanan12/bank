package transaction.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import balance.model.BalanceModel;
import transaction.dao.TransactionDAO;

public class TransactionModel {
	
	private String accountId;
	private double currentTransactionmount;
	private String action;
	private String beneficiary;
	private String level="";
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public double getCurrentTransactionmount() {
		return currentTransactionmount;
	}
	public void setCurrentTransactionmount(double currentTransactionmount) {
		this.currentTransactionmount = currentTransactionmount;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getBeneficiary() {
		return beneficiary;
	}
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	public TransactionModel(String accountId,String amount,String action,String beneficiary) {
		this.accountId =  accountId;
		this.currentTransactionmount =  Double.parseDouble(amount);
		this.action =  action;
		this.setBeneficiary(beneficiary);
		
	}
	public boolean validateBenificiary() {
		TransactionDAO transactionDAO = new TransactionDAO(this);
		return transactionDAO.validateBenificiary();
	}
	
	public Map<String,Boolean> validateTransaction() {
		Map<String,Boolean>  validationResponse =  new HashMap<String, Boolean>();
		boolean isValidAmount = false;
		boolean isValidLimit = false;
		BalanceModel balanceModel = new BalanceModel(accountId);
		Map<String,String> balanceMap = balanceModel.getBalance();
		String result = balanceMap.get("result");
//		System.out.println("result of transaction balancemap "+balanceMap+", "+result.equals("1"));
		if(result.equals("1")) {
			double currentBalance = Double.parseDouble(balanceMap.get("balance"));
			if(action.equals("withdraw")) {
				isValidAmount = ((currentBalance>=currentTransactionmount) && (currentTransactionmount>0));
				int totalTransactionToday = 0;//test
				isValidLimit  = getWithdrawLimit()>=(totalTransactionToday+currentTransactionmount);
			}else { //Deposit
				isValidAmount = currentTransactionmount>0;
				isValidLimit =  getDepositLimit()>=(currentBalance+currentTransactionmount);//This check limit the every transaction.
			}
		}
		validationResponse.put("isValidAmount", isValidAmount);
		validationResponse.put("isValidLimit", isValidLimit);
		System.out.println("currentTransactionmount - "+currentTransactionmount+", validationResponse -> "+validationResponse);
		return validationResponse;
	}
	public Map <String,Boolean> addTransactionAmount() {
		Map <String,Boolean> transactionResponse = new HashedMap();
		BalanceModel balanceModel = new BalanceModel(beneficiary);
		double currentBalance = balanceModel.getBalanceAmount();
		double processedAmount = 0;
		processedAmount = currentBalance+currentTransactionmount;
		TransactionDAO transactionDAO = new TransactionDAO(this);
		int result = transactionDAO.addTransactionEntry(processedAmount,beneficiary,"deposit");
		transactionResponse.put("transaction",(result==1));
		if(result==1) { //Success
			boolean updatedBalance = balanceModel.setBalance(processedAmount);
			transactionResponse.put("balanceUpdate",updatedBalance);
		}else {
			transactionResponse.put("balanceUpdate",false);
		}
		return transactionResponse;
	}
	public Map <String,Boolean> deductTransactionAmount() {
		Map <String,Boolean> transactionResponse = new HashedMap();
		BalanceModel balanceModel = new BalanceModel(accountId);
		double currentBalance = balanceModel.getBalanceAmount();
		double processedAmount = 0;
	    processedAmount = currentBalance - currentTransactionmount;
		TransactionDAO transactionDAO = new TransactionDAO(this);
		int result = transactionDAO.addTransactionEntry(processedAmount,accountId,"withdraw");
		transactionResponse.put("transaction",(result==1));
		if(result==1) { //Success
			boolean updatedBalance = balanceModel.setBalance(processedAmount);
			transactionResponse.put("balanceUpdate",updatedBalance);
		}else {
			transactionResponse.put("balanceUpdate",false);
		}
		return transactionResponse;
	}
	
	public int getWithdrawLimit() {
		
		switch (level) {
			case "SILVER": 
				return 25000;
			case "GOLD":
				return 50000;
			case "DIAMOND":
				return 100000;
			default:
				return 10000;
		}
	}
	
public int getDepositLimit() {
		
		switch (level) {
			case "SILVER": 
				return 25000;
			case "GOLD":
				return 50000;
			case "DIAMOND":
				return 100000;
			default:
				return 10000;
		}
	}

}
