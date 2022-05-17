package balance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import balance.model.BalanceModel;
import signup.dao.SignupDao;

public class BalanceDAO {

	private BalanceModel balanceModel;
	
	public BalanceDAO(BalanceModel balanceModel) {
		this.balanceModel = balanceModel;
	}
	private Connection getConnection() {
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
	
	
	public Map<String, String> getBalance() {
		Connection connection = null;
		int result = 0;
		double balanceAmount = 0; 
		Map<String, String> balanceMap = new HashMap<String, String>();
		try {
			System.out.println("accid "+balanceModel.getAccountId());
			connection = getConnection();
			String queryOfBalanceTable ="select * from  Balance where account_id =?";
			PreparedStatement preparedStatement = connection.prepareStatement(queryOfBalanceTable);
        	preparedStatement.setString(1,balanceModel.getAccountId());
        	ResultSet rs = preparedStatement.executeQuery();
        	while (rs.next()) {
        		balanceAmount = rs.getInt("amount");
        		balanceMap.put("balance", balanceAmount+"");
        		System.out.println("amount "+balanceAmount);
        	}
        	result = 1;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception in getlogin data ->"+e.getMessage());
		}finally {
			try {
				if(connection!=null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Exception in closing connection  ->"+e.getMessage());
			}
			System.out.println("getBalance final result ->"+result);
		}
		System.out.println("balance is "+balanceMap);
		balanceMap.put("result", result+"");
		return balanceMap;
		
	}
	
	public boolean updateBalace(double amount) {
		
		int result = 0;
		try {
	         Connection connection = getConnection();
	         String queryOfUpdateBalance = "update Balance set amount=? where account_id=?";
         	 PreparedStatement preparedStatement = connection.prepareStatement(queryOfUpdateBalance);
             preparedStatement.setDouble(1, amount);
             preparedStatement.setString(2, balanceModel.getAccountId());
             result = preparedStatement.executeUpdate();
             System.out.println("result :"+result);
             connection.close();
             return true;
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception in insert data ->"+e.getMessage());
		}finally {
			System.out.println("connection final result ->"+result);
		}
		
		return false;
	}
	
}
