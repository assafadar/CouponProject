package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import enums.UserType;
import exceptions.ApplicationException;

public class DataValidationUtils {

	// Validates password under several criterias
	public static boolean validatePassword(String password, String name) throws ApplicationException{

		if (compareNameAndPassword(name, password)){
			return false;
		}

		if (passwordLowerThan8Tabs(password)){
			return false;
		}
		
		return true;
	}

	//Checking that the password is 8 characters or more
	private static boolean passwordLowerThan8Tabs(String password){

		if (password.length()<8){
			return true;
		}
		return false;
	}


	//Validatin that company name and password can't match.
	private static boolean compareNameAndPassword(String companyName, String password) {
		if (companyName.toLowerCase().equals(password.toLowerCase())){
			return true;
		}
		return false;
	}

	//Email validation -according to specific format
	public static boolean validateEmail(String email) throws ApplicationException{
		final Pattern VALID_EMAIL_ADDRESS_REGEX = 
				Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);

		return matcher.find();
	}

	public static boolean checkPermissions(String path, UserType userRole) {
		if(userRole.equals(UserType.Admin)) {
			return true;
		}
		if(userRole.equals(UserType.Company)) {
			return checkPermissionForUserType(path,userRole);
		}
		if(userRole.equals(UserType.Customer)) {
			return checkPermissionForUserType(path, userRole);
		}
		return false;
	}

	private static boolean checkPermissionForUserType(String path, UserType userRole) {
		if(userRole == UserType.Admin) {
			return true;
		}
		else if (path.contains(userRole.toString().toLowerCase()) || path.contains("/buy") || path.contains("/amount")) {
			return true;
		}
		else {
			if(path.contains("/users/")) {
				return true;
			}
		}
		return false;
	}

}
