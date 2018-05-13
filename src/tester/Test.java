package tester;
import dao.CouponDAO;
import enums.ErrorType;
import enums.CouponType;
import exceptions.ApplicationException;
import threads.UpdaterService;
import utils.DataValidationUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
	private static boolean isDatesOrderValid(String startDate, String endDate) throws ApplicationException{
		Calendar cal =Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.applyPattern("dd/MM/yyyy");
		sdf.setLenient(false);
		try{
			Date date = new Date();
			Date startDateFormat = sdf.parse(startDate);
			Date endDateFormat = sdf.parse(endDate);
			DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate =sdf.parse(dateFormat.format(date));
			if(currentDate.before(startDateFormat)||currentDate.equals(startDateFormat)){
				if(startDateFormat.before(endDateFormat)){
					return true;
				}
			}
		}catch(Exception e){
			throw new ApplicationException("Coudn't validate dates", ErrorType.DATA_VALIDATION_ERROR);
		}

		return false;
	}

	public static void main(String[] args) {
		try {
			System.out.println(isDatesOrderValid("12/11/2017", "12/12/2020"));
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		int hours = calendar.get(Calendar.HOUR_OF_DAY)*3600000;
		double minutes =calendar.get(Calendar.MINUTE)*60000;
		long firstTimerDelay =(long)(86400000 - hours - minutes);
		System.out.println(firstTimerDelay);	
		TimerTask updateCouponsTable = new UpdaterService();
		Timer timer = new Timer(true);		
		timer.scheduleAtFixedRate(updateCouponsTable,firstTimerDelay,1000*60*60*24);			
	} 
}

