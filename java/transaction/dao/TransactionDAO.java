package transaction.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import signup.dao.SignupDao;
import transaction.model.TransactionModel;

public class TransactionDAO {
	
	
	private TransactionModel transactionModel;
	
	public TransactionDAO(TransactionModel transactionModel) {
		this.transactionModel = transactionModel;
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(SignupDao.URL,SignupDao.MYSQL_USER,SignupDao.MYSQL_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public void getAccountId(String upi) {
		
	}

	public boolean validateBenificiary() {
		Connection connection = null;
		boolean result = false;
		try {
			String queryOfAccountsTable ="select * from  Accounts where id =? And id!=? ";
			connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryOfAccountsTable);
        	preparedStatement.setString(1,transactionModel.getBeneficiary());
        	preparedStatement.setString(2,transactionModel.getAccountId());
        	ResultSet rs = preparedStatement.executeQuery();
        	System.out.println("accId: "+transactionModel.getAccountId()+" , benificiary - "+transactionModel.getBeneficiary());
        	while (rs.next()) {
        		String id = rs.getString("id");
        		String userId = rs.getString("user_id");
        		System.out.println("Acc Id "+id+", user Id "+userId);
        		result = true;
        	}
		}catch(SQLException e) {
			result = false;
			e.printStackTrace();
			System.out.println("SQL Exception in befnificary data ->"+e.getMessage());
		}catch(Exception e) {
			result = false;
			e.printStackTrace();
			System.out.println("Exception in befnificary data ->"+e.getMessage());
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				result = false;
				e.printStackTrace();
				System.out.println("Exception in closing connection  ->"+e.getMessage());
			}
			System.out.println("befnificary connection final result ->"+result);
		}
		return result;
	}
	
	public int addTransactionEntry(double balance, String account,String action) {
		Connection connection = null;
		int result = 0;
		try {
			connection = getConnection();
			String queryOfTransactionAdd = "insert into Transactions (account_id, deposit_amount,withdraw_amount,current_balance) values (?, ?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(queryOfTransactionAdd);
            double depositAmount = 0;
            double withdrawAmount = 0;
            if(action.equals("withdraw")) {
            	withdrawAmount = transactionModel.getCurrentTransactionmount();
            }else { //Deposit
            	depositAmount = transactionModel.getCurrentTransactionmount();
            }
            preparedStatement.setString(1, account);
            preparedStatement.setString(2, depositAmount+"");
            preparedStatement.setString(3, withdrawAmount+"");
            preparedStatement.setString(4, balance+"");
            result = preparedStatement.executeUpdate();
           
		}catch(SQLException e) {
			result = 0;
			e.printStackTrace();
			System.out.println("SQL Exception in getlogin data ->"+e.getMessage());
		}catch(Exception e) {
			result = 0;
			e.printStackTrace();
			System.out.println("Exception in getlogin data ->"+e.getMessage());
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				result = 0;
				e.printStackTrace();
				System.out.println("Exception in closing connection  ->"+e.getMessage());
			}
			System.out.println("connection final result ->"+result);
		}
		return result;
	}
	
}
