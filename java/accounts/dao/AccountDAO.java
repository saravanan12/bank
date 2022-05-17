package accounts.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import accounts.model.AccountModel;
import signup.dao.SignupDao;

public class AccountDAO {

	
	private AccountModel accountModel;
	
	public AccountDAO(AccountModel accountModel) {
		this.accountModel = accountModel;
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
	
//	public String getAccountId() {
//		
//		Connection connection = null;
//		String accountId = "";
//		String queryOfAccountTable ="select * from Accounts where user_id =?";
//		try {
//			connection = getConnection();
//			PreparedStatement preparedStatement = connection.prepareStatement(queryOfAccountTable);
//        	preparedStatement.setString(1,accountModel.getUserId());
//        	ResultSet rs = preparedStatement.executeQuery();
//        	while (rs.next()) {
//        		accountId = rs.getString("id");
//        	}
//			
//		}catch(SQLException e) {
//			e.printStackTrace();
//			System.out.println("SQL Exception in getlogin data ->"+e.getMessage());
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("Exception in getlogin data ->"+e.getMessage());
//		}finally {
//			try {
//				if(connection!=null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				System.out.println("Exception in closing connection  ->"+e.getMessage());
//			}
//		}
//		return accountId;
//	}
	
	public Map<String,String> getUserDetails() {
		
		Map<String,String> userDetailsMap = new HashMap<String, String>();
		Connection connection = null;
		String queryOfUserTable ="select first_name,last_name,customer_id,Accounts.id from  User inner join Accounts  on User.id=Accounts.user_id  where User.customer_id=?";
		try {
			connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(queryOfUserTable);
        	preparedStatement.setString(1,accountModel.getCustomerId());
        	ResultSet rs = preparedStatement.executeQuery();
//        	ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
//        	for (int i = 1; i <= rsmd.getColumnCount(); i++ ) {
//        		  String name = rsmd.getColumnName(i);
//        		  String tableName = rsmd.getTableName(i);
//        		  System.out.println("Column name: "+name+", table :"+tableName);
//        		}
        	while (rs.next()) {
        		String fName = rs.getString("first_name");
        		String lName = rs.getString("last_name");
        		String customerId = rs.getString("customer_id");
        		String accountId = rs.getString("id");
        		System.out.println(" id : "+rs.getString("first_name"));
        		userDetailsMap.put("firstName", fName);
        		userDetailsMap.put("lastName", lName);
        		userDetailsMap.put("customerId", customerId);
        		userDetailsMap.put("accountId", accountId);
        		
        	}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("SQL Exception in getlogin data ->"+e.getMessage());
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
		}
		System.out.println("userdetails map -> "+userDetailsMap);
		return userDetailsMap;	
		
	}
	
}
