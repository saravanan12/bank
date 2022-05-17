package login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import login.model.LoginModel;
import signup.dao.SignupDao;


public class LoginDao {

	private LoginModel loginModel; 
	private final static int WEB_MODE =1;
	private final static int MOBILE_MODE =2;
	private final static int API_MODE =3;
	
	public LoginDao(LoginModel loginModel) {
		this.loginModel = loginModel;
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
	public boolean checkLoginCredential() {
		
		Connection connection = null;
		int result = 0;
		boolean isValidCredential = false;
		try {
			String queryOfUserTable ="select * from  User where customer_id =?";
			String queryOfPasswordTable ="select * from Password_Details  where user_id =? and password=?";// where user_id =? and password=?
			connection = getConnection();
			//String queryOfUserAndPasswordTable = "select * from  User inner join Password_Details  on User.id=Password_Details.user_id ";//where User.customer_id=? and Password_Details.password=?
			System.out.println("customerid: "+loginModel.getCustomerId()+" , password :"+loginModel.getPassword());
			PreparedStatement preparedStatement = connection.prepareStatement(queryOfUserTable);
        	preparedStatement.setString(1,loginModel.getCustomerId());
        	ResultSet rs = preparedStatement.executeQuery();
        	String primaryKeyIdOfUser ="";
        	while (rs.next()) {
        		primaryKeyIdOfUser = rs.getString("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				System.out.println("Row ->  firstName - "+firstName+",lastName - "+lastName+", primary keyid - "+primaryKeyIdOfUser);
			}
        	
        	 System.out.println("pId :"+primaryKeyIdOfUser);
        	 if(!primaryKeyIdOfUser.isEmpty()) {
        		preparedStatement = connection.prepareStatement(queryOfPasswordTable);
 	            preparedStatement.setString(1, primaryKeyIdOfUser);
 	            preparedStatement.setString(2, loginModel.getPassword());
 	            rs = preparedStatement.executeQuery();
 	            isValidCredential = rs.next();
        	 }
        	 if(isValidCredential) {
        		 //Add current login
        		 String addQueryOfCurrentLoginTable ="insert into Current_Login_Details(mode, user_id) values  (?,?)";
        		 preparedStatement = connection.prepareStatement(addQueryOfCurrentLoginTable);
                 preparedStatement.setString(1, WEB_MODE+"");
                 preparedStatement.setString(2, primaryKeyIdOfUser);
                 result = preparedStatement.executeUpdate();
                 System.out.println("Current login table result ->"+result);
        	 }else {
        		 //Add failed login
             	 if(!primaryKeyIdOfUser.isEmpty()) {
                	 System.out.println("primaryKeyIdOfUser: "+primaryKeyIdOfUser);
	        		 String addQueryOfFailedLoginTable ="insert into Failed_Login_Details(mode, user_id) values  (?,?)";
	        		 preparedStatement = connection.prepareStatement(addQueryOfFailedLoginTable);
	                 preparedStatement.setString(1, WEB_MODE+"");
	                 preparedStatement.setString(2, primaryKeyIdOfUser);
	                 result = preparedStatement.executeUpdate();
	                 System.out.println("Failed login table result ->"+result);
             	 }
        	 }
        	 rs.close();
        	 preparedStatement.close();
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
			System.out.println("connection final result ->"+result);
		}
		return isValidCredential;
	}
}
