package utils;


import java.util.Random;

import javax.servlet.http.Cookie;
import beans.User;
import enums.UserType;
import exceptions.ApplicationException;
import managers.SessionManager;

public class UserUtils {
	
	public static String generateActivationToken() {
		String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
		String Token = "";
		for (int a = 1; a <= 10; a++) {
			Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
		}
		return Token;
	}
	public static User getUserFromCookie(Cookie [] cookies) throws ApplicationException{
			Cookie userCookie=null;
			for (Cookie cookie:cookies) {
				if(cookie.getName().equals("CouponsProjectCookie")) {
					userCookie = cookie;
				}
			}
			if(userCookie!=null) {
				return getUserFromSessionManager(userCookie.getValue());
			}
			return null;
		}
	public static User getUserFromSessionManager(String hashedId) throws ApplicationException {
		SessionManager sessionManager = SessionManager.getSessionManagerInstance();
		User user = sessionManager.getUserFromSessionManager(hashedId);
		return user;
	}
	public static void addUserObjectToMap(User user) throws ApplicationException{
		SessionManager sessionManager = SessionManager.getSessionManagerInstance();
		sessionManager.addSealedUserToSessionManager(user);
	}
	public static Cookie addUserCookieToResponse(long userID)throws ApplicationException{
		Cookie cookie = new Cookie("CouponsProjectCookie",EncryptionUtils.getHashForString(String.valueOf(userID)));
		cookie.setMaxAge(60*60);
		return cookie;
	}
	public static void sendWelcomeEmailsToNewUser(User user) throws ApplicationException {
		if(user.getUserType().equals(UserType.Company)) {
			MailUtils.sendWelcomeEmailForCompany(user);
		}
		else if(user.getUserType().equals(UserType.Customer)){
			MailUtils.sendWelcomeEmailForCustomer(user);
		}
	}
	
}
