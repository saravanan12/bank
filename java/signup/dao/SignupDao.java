package signup.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.antlr.v4.parse.ANTLRParser.throwsSpec_return;

import signup.model.SignupModel;


public class SignupDao {

	
	public final static String URL = "jdbc:mysql://localhost:3306/BANK?allowPublicKeyRetrieval=true&useSSL=false";
	public final static String MYSQL_USER = "root";
	public final static String MYSQL_PASSWORD = "People@12";
	
	private SignupModel signupModel; 
	
	public SignupDao(SignupModel signupModel) {
		this.signupModel =  signupModel;
	}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL,MYSQL_USER,MYSQL_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
//	public void insertUserDetails() {
//		
//		Connection connection = null;
//		int result = 0;
//		try {
//	         connection = getConnection();
//	         String queryOfUserAdd = "INSERT INTO User" +
//	                 "  (first_name, last_name) VALUES " +
//	                 " (?, ?);";
//         	 PreparedStatement preparedStatement = connection.prepareStatement(queryOfUserAdd);
//             preparedStatement.setString(1, "sarom1");
//             preparedStatement.setString(2, "sarom2");
//             result = preparedStatement.executeUpdate();
//             System.out.println("result :"+result);
//	         System.out.println("connection :"+connection.toString());
//             connection.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("Exception in insert data ->"+e.getMessage());
//		}finally {
//			System.out.println("connection final result ->"+result);
//		}
//	}
	public String addSignupDetails() {
		Connection connection = null;
		int result = 0;
		try {
			String addQueryOfUserTable ="insert into User(first_name, last_name,customer_id) values  (?, ?, ?)";
			String getQueryOfUserTable ="select * from  User where customer_id =?";
			String addQueryOfAccountsTable ="insert into Accounts(user_id) values  (?)";
			String getQueryOfAccountsTable = "select * from  Accounts where user_id =?";
			String addQueryOfPasswordTable ="insert into Password_Details(password,user_id) values  (?,?)";
			String addQueryOfBalanceTable ="insert into Balance(account_id,amount) values  (?,?)";
			
			connection = getConnection();
			Random random = new Random();
			int customerId =  random.nextInt(999999);
			PreparedStatement preparedStatement = connection.prepareStatement(addQueryOfUserTable);
            preparedStatement.setString(1, signupModel.getFirstName());
            preparedStatement.setString(2, signupModel.getLastName());
            preparedStatement.setString(3, customerId+"");
            result = preparedStatement.executeUpdate();
            String primaryIdOfUser ="";
            if(result==1) {
            	preparedStatement = connection.prepareStatement(getQueryOfUserTable);
            	preparedStatement.setString(1,customerId+"");
            	ResultSet rs = preparedStatement.executeQuery();
            	
            	while (rs.next()) {
            		primaryIdOfUser = rs.getString("id");
    				String firstName = rs.getString("first_name");
    				String lastName = rs.getString("last_name");
    				System.out.println("Row -> firstName - "+firstName+",lastName - "+lastName+", primary keyid - "+primaryIdOfUser);
    			}
            	
            }
            if(!primaryIdOfUser.isEmpty()) {
	            //Add entry in Accounts
	            preparedStatement = connection.prepareStatement(addQueryOfAccountsTable);
	            preparedStatement.setString(1, primaryIdOfUser+"");
	            result = preparedStatement.executeUpdate();
	            System.out.println("Account table result ->"+result);
	            
	           //Add entry in Password_Details
	            preparedStatement = connection.prepareStatement(addQueryOfPasswordTable);
	            preparedStatement.setString(1, signupModel.getPassword());
	            preparedStatement.setString(2, primaryIdOfUser+"");
	            result = preparedStatement.executeUpdate();
	            System.out.println("Password table result ->"+result);
	            
	            
	            //Get Accounts table Id
	            preparedStatement = connection.prepareStatement(getQueryOfAccountsTable);
            	preparedStatement.setString(1,primaryIdOfUser+"");
            	ResultSet rs = preparedStatement.executeQuery();
            	String accountId = "";
            	while (rs.next()) {
            		accountId = rs.getString("id");
    				System.out.println("Row -> accountId - "+accountId);
    			}
            	
            	//Add entry in Balance
            	if(!accountId.isEmpty()) {
	            	preparedStatement = connection.prepareStatement(addQueryOfBalanceTable);
	             	preparedStatement.setString(1,accountId+"");
	             	preparedStatement.setInt(2,1000);
	             	result = preparedStatement.executeUpdate();
	             	System.out.println("Password table result ->"+result);
            	}
	            
            }
            
            connection.close();
            System.out.println("added signup ->"+result);
			return customerId+"";
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception in insert data ->"+e.getMessage());
		}finally {
			System.out.println("connection final result ->"+result);
		}
		return "";
	}
}
