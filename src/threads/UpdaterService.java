package threads;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import dao.CouponDAO;
import dao.ICouponDAO;
import exceptions.ApplicationException;

public class UpdaterService extends TimerTask {
	private ICouponDAO couponDAO;
	
	public UpdaterService(){
		couponDAO = new CouponDAO();
	}
	@Override
	public void run(){
		String currentDate = getDate();
		try {
			couponDAO.deleteExpiredCoupons(currentDate);
		} 
		// Exception swalloed on purpose 
		catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private String getDate() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr = sdf.format(date).toString();
		return dateStr;
	}
	
}
