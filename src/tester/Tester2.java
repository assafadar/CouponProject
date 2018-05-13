package tester;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import beans.User;
import enums.ErrorType;
import enums.UserType;
import exceptions.ApplicationException;
import utils.EncryptionUtils;
import utils.MailUtils;

public class Tester2 {

	private static boolean isDatesOrderValid(String startDate, String endDate) throws ApplicationException{

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.applyPattern("dd/MM/yyyy");
		sdf.setLenient(false);
		try{
			Date date = new Date();
			Date startDateFormat = sdf.parse(startDate);
			Date endDateFormat = sdf.parse(endDate);
			DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate =sdf.parse(dateFormat.format(date));
			if(currentDate.before(endDateFormat)||currentDate.equals(startDateFormat)){
				if(startDateFormat.before(endDateFormat)){
					return true;
				}
			}
		}catch(Exception e){
			throw new ApplicationException("Coudn't validate dates", ErrorType.DATA_VALIDATION_ERROR);
		}

		return false;
	}

	public static void main(String[] args) throws ApplicationException {
//		CouponsController c = new CouponsController();
//		CustomerController customer = new CustomerController();
//		Coupon coupon = new Coupon();
//		Customer newCompany = new Customer("abcdreffsafaa","padspfgAA","abcd@gmail.com");
//		CustomerController customerController = new CustomerController();
//		coupon.setAmount(10);
//		coupon.setCompanyID(3);
//		coupon.setEndDate("10/12/2019");
//		coupon.setImg("img.jpg");
//		coupon.setMessage("this is a message");
//		coupon.setPrice(100);
//		coupon.setStartDate("24/12/2017");
//		coupon.setTitle("This is e title");
//		MailUtils.sendWelcomeEmailForCustomer(newCompany);
		

	}

}
