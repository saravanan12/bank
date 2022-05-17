package transaction.controller;

import java.util.HashMap;
import java.util.Map;

import transaction.model.TransactionModel;
import transaction.view.TransactionResponseHandler;
import transaction.view.TransactionResponseHandler.ERROR;

public class TransactionController {

	private TransactionModel transactionModel;
	private TransactionResponseHandler transactionResponseHandler;
	
	public TransactionController(TransactionModel transactionModel,TransactionResponseHandler transactionResponseHandler) {
		this.transactionModel =  transactionModel;
		this.transactionResponseHandler =  transactionResponseHandler;
	}
	
	public boolean validateAccount(){
		return transactionModel.validateBenificiary();
	}
	
	public Map<String,Boolean> validateTransaction() {
		return transactionModel.validateTransaction();
	}
	
	public Map <String,Boolean > addTransactionAmount(){
		Map <String,Boolean>  response = new HashMap();
		Map <String,Boolean> deductResponse = transactionModel.deductTransactionAmount();
		Map <String,Boolean> addResponse = transactionModel.addTransactionAmount();
		response.put("transaction", deductResponse.get("transaction") && addResponse.get("transaction"));
		response.put("balanceUpdate", deductResponse.get("balanceUpdate") && addResponse.get("balanceUpdate"));
		System.out.println("deduct response map -> "+deductResponse);
		System.out.println("add response map -> "+addResponse);
		return response;
	}
	
	public Map<String,String> getErrorMsgOfValidation(ERROR errorKey ){
		return transactionResponseHandler.getErrorMsgOfValidation(errorKey);
	}
	
	public Map<String,String> getSuccessMsgOfTransaction(String balance){
		return transactionResponseHandler.getSuccessMsgOfTransaction(balance);
	}
	
	public Map<String,String> getErrorMsgOfInvalidAccount() {
		return transactionResponseHandler.getErrorMsgOfInvalidAccount();
	}
	
}
