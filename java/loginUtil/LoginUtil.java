package loginUtil;

public class LoginUtil {
	
	public static boolean isNotNull(String value) {
		return (value!=null);
	}
	public static boolean isNotNullAndNotEmpty(String value) {
		System.out.println("isNotNullAndNotEmpty value -> "+ value);
		try {
			return (value!=null && !value.isEmpty());
		}catch(Exception e) {
			System.out.println("Exception isNotNullAndNotEmpty -> "+e.getMessage());
		}
		return false;
	}
	
}
