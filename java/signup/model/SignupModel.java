package signup.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.zoho.logs.shaded.com.google.code.regexp.Matcher;
import com.zoho.logs.shaded.com.google.code.regexp.Pattern;

import loginUtil.LoginUtil;
import signup.SignupInterface;
import signup.dao.SignupDao;

public class SignupModel implements SignupInterface {
	
	private String firstName;
	private String lastName;
	private String password;
	private String emailId;
	private String phone;
	private String gender;
	
	private final String[] GENDERS =  {"Male","Female","Transgender","Genderless"};
	private final String NAME_PATTERN = "[a-zA-Z0-9\\._\\-]{3,}";
	private final String PASSWORD_PATTERN = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{3,}$";
	private final String EMAIL_PATTERN = 
	        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	public SignupModel(String firstName,String lastName, String password, String emailId, String phoneNumber, String gender) {
		
		this.firstName =  firstName;
		this.lastName =  lastName;
		this.password = password;
		this.emailId = emailId;
		this.phone = phoneNumber;
		this.gender = gender;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public boolean paramsValidation() {
		
		return false;
		
	}
		
	public boolean update() {
		
		return false;
	}
	
	@Override
	public boolean isValidUserName() {
		System.out.println("isValidUserName -> firstname :" + this.firstName + ", lastname: " + lastName);
		try {
			if (LoginUtil.isNotNullAndNotEmpty(firstName) && LoginUtil.isNotNullAndNotEmpty(lastName)) {
				Pattern pattern = Pattern.compile(NAME_PATTERN);
				Matcher firstNameMatch = pattern.matcher(firstName);
				Matcher lastNameMatch = pattern.matcher(lastName);
				System.out.println(
						"firstNameMatch:" + firstNameMatch.matches() + " , lastNameMatch :" + lastNameMatch.matches());
				return firstNameMatch.matches() && lastNameMatch.matches();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("isValidUserName Exception is " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isValidaPassword() {
		try {
			if (LoginUtil.isNotNullAndNotEmpty(password)) {
				Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
				Matcher match = pattern.matcher(password);
				System.out.println("  Password match " + match.matches());
				return match.matches();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("isValidaPassword Exception is " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isValidateEmailId() {
		System.out.println("emailId :" + emailId);
		try {
			if (LoginUtil.isNotNullAndNotEmpty(emailId)) {
				Pattern pattern = Pattern.compile(EMAIL_PATTERN);
				Matcher match = pattern.matcher(emailId);
				System.out.println("  Email match " + match.matches());
				return match.matches();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("isValidaPassword Exception is " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean isValidateGender() {
		try {
			List<String> list = Arrays.asList(GENDERS);
			System.out.println("  Email match " + list.contains(gender));
			return list.contains(gender);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("isValidaPassword Exception is " + e.getMessage());
			return false;
		}
	}

	public String addUser() {

		SignupDao sigupDao = new SignupDao(this);
		return sigupDao.addSignupDetails();

	}

}