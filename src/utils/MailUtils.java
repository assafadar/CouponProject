package utils;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import beans.Coupon;
import beans.User;
import enums.ErrorType;
import exceptions.ApplicationException;

public class MailUtils {
	
	final static String FROM_MAIL_ADDRESS = "asafadar55@gmail.com";
	final static String EMAIL_ACCOUNT_PASSWORD = "Massarik11a";


	// This is an automated Email sent for Company Registration.
	public static void sendWelcomeEmailForCompany(User user) throws ApplicationException {
		String sub = "Warm Welcome To Our Sellers";
		String msg = "This is a test registration email!";
		String to = user.getEmail();
		sendMessage(getSession(), sub, msg, to);
	}

	// This is an automated email sent to customer after buying a coupon
	public static void sendAftherPurchaseEmail(Coupon coupon, User user) throws ApplicationException {
		String sub = "Your new coupon!";
		String msg = coupon.getTitle() + "\n" + coupon.getMessage() + "\n" + "This coupon is valid until: "
				+ coupon.getEndDate();
		String to = user.getEmail();
		sendMessage(getSession(), sub, msg, to);
	}

	// This is an automated Email sent for new Customers.
	public static void sendWelcomeEmailForCustomer(User user) throws ApplicationException {
		String sub = "Warm Welcome From Out Team!";
		String msg = "This is a test email for cutomer";
		String to = user.getEmail();
		sendMessage(getSession(), sub, msg, to);
	}

	// Getting the session and Authentication Obj
	private static Session getSession() {
		Session session = Session.getDefaultInstance(getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_MAIL_ADDRESS, EMAIL_ACCOUNT_PASSWORD);
			}
		});
		return session;
	}

	// Sending a message via gmail SMTP server.
	private static void sendMessage(Session session, String sub, String msg, String to) throws ApplicationException {
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setContent(msg,"text/html; charset=utf-8");
			
			// send message
			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (Exception e) {
			throw new ApplicationException("Couldn't send email", ErrorType.CONNECTION_ERROR, e);
		}
	}

	private static Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		return props;
	}
}
