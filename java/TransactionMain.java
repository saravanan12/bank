import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import accounts.model.AccountModel;
import balance.model.BalanceModel;
import transaction.controller.TransactionController;
import transaction.model.TransactionModel;
import transaction.view.TransactionResponseHandler;

public class TransactionMain extends ActionSupport {
	
	private String action;
	private String amount;
	private String accountId;
	private String beneficiary;
	private Map<String, Map<String,String>> data = new HashMap<String, Map<String,String>>();
	
	public String execute() {
		Map<String,String> responseMap = new HashMap<String, String>();
		System.out.println("enter into TransactionMain");
		TransactionModel transactionModel = new TransactionModel(accountId,amount,action,beneficiary);
		TransactionResponseHandler transactionResponseHandler = new TransactionResponseHandler();
		TransactionController transactionController =  new TransactionController(transactionModel, transactionResponseHandler);
		if(transactionController.validateAccount()) {
			Map<String,Boolean> validateTransactionMap = transactionController.validateTransaction();
			boolean isValidAmount = Boolean.parseBoolean(validateTransactionMap.get("isValidAmount")+"");
			boolean isValidLimit = Boolean.parseBoolean(validateTransactionMap.get("isValidLimit")+"");
			
			if(isValidAmount && isValidLimit) {
				Map <String,Boolean> transactionResponse = transactionController.addTransactionAmount();
				boolean completedTransaction = transactionResponse.get("transaction");
				boolean balanceUpdated = transactionResponse.get("balanceUpdate");
				System.out.println("transaction -> "+completedTransaction+", balanceUpdated -> "+balanceUpdated);
				if(completedTransaction && balanceUpdated) {
					BalanceModel balanceModel = new BalanceModel(accountId);
					double currentBalance = balanceModel.getBalanceAmount();
					responseMap =  transactionController.getSuccessMsgOfTransaction(currentBalance+"");
				}else {
					responseMap = transactionController.getErrorMsgOfValidation(transaction.view.TransactionResponseHandler.ERROR.SOMETHING_WRONG);
				}
			}else {
				if(!isValidAmount) {
					responseMap =  transactionController.getErrorMsgOfValidation(transaction.view.TransactionResponseHandler.ERROR.BALANCE_CHECK);
				}else {
					responseMap =  transactionController.getErrorMsgOfValidation(transaction.view.TransactionResponseHandler.ERROR.LIMIT_REACHED);
				}
			}
		}else {
			responseMap =  transactionController.getErrorMsgOfInvalidAccount();
			
		}
		data.put("response",responseMap);
		return SUCCESS;
	}


	public void setAction(String action) {
		this.action = action;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

}
