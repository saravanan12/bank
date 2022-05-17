package accounts.model;

import java.util.Map;

import accounts.dao.AccountDAO;

public class AccountModel {

	private String customerId;
	
	public AccountModel(String customerId) {
		this.setCustomerId(customerId);
	}
	
	public String getAccountId() {
//		AccountDAO accountDAO = new AccountDAO(this);
		return "";//accountDAO.getAccountId();
	}
	
	public Map<String,String> getUserDetails() {
		AccountDAO accountDAO = new AccountDAO(this);
		return accountDAO.getUserDetails();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	
}
