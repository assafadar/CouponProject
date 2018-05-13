package controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beans.Coupon;
import dao.CouponDAO;
import dao.ICouponDAO;
import dao.UserDAO;
import enums.CouponType;
import enums.ErrorType;
import exceptions.ApplicationException;



public class CouponsController{

	private ICouponDAO couponDAO;
	private UserDAO userDAO;
	public CouponsController(){
		couponDAO = new CouponDAO();
		userDAO = new UserDAO();
	}

	// Validates all aspects of coupon creations.
	private boolean validteCouponData(Coupon coupon) throws ApplicationException{

		//If title found invalid - title already exists or that the text length
		// is bigger than defined in db (15 chars)
		if(!isTitleValid(coupon.getTitle())){

			throw new ApplicationException("Title Can't be added", ErrorType.DATA_VALIDATION_ERROR);
		}

		//Validates that the date is in dd/MM/yyyy format and that 
		// Start date is before end date
		if (!isDatesValid(coupon.getStartDate(),coupon.getEndDate())){

			throw new ApplicationException("Dates are not valid", ErrorType.DATA_VALIDATION_ERROR);

		}

		//Validates that the amount of listed coupons is bigger than 0
		if (coupon.getAmount()<=0){

			throw new ApplicationException("Amount of coupons invalid", ErrorType.DATA_VALIDATION_ERROR);
		}

		// validates that coupon description is not bigger than defined in db (250)
		if (coupon.getMessage().length()>250 || coupon.getMessage().length()==0){

			throw new ApplicationException("Message is invalid", ErrorType.DATA_VALIDATION_ERROR);
		}

		// validates image string format - ends with .gif/.jpg/.jpeg...
		if (!isImageValid(coupon.getImg())){

			throw new ApplicationException("Image loading failure", ErrorType.DATA_VALIDATION_ERROR);
		}

		// Validates that coupon price is more than 0
		if (coupon.getPrice()<=0){

			throw new ApplicationException("Invalid price for coupon", ErrorType.DATA_VALIDATION_ERROR);
		}

		return true;
	}

	// validates imgae source path ends with IMG ending - jpg,jpeg,gif,bmp
	private boolean isImageValid(String img)throws ApplicationException {

		final String IMAGE_PATTERN =
				"([^\\s]+(\\.(?i)(jpg|png|jpeg|gif|bmp))$)";

		Pattern pattern = Pattern.compile(IMAGE_PATTERN);

		Matcher matcher =  pattern.matcher(img);

		return matcher.matches();

	}

	// validtes dates valid
	private boolean isDatesValid(String startDate, String endDate) throws ApplicationException{

		if (isDateFormatValid(startDate) && isDateFormatValid(endDate)){
			if (isDatesOrderValid(startDate,endDate)){

				return true;
			}
		}

		return false;
	}

	//validates that start date is before end date
	private boolean isDatesOrderValid(String startDate, String endDate) throws ApplicationException{

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

	// validates dates format dd/MM/yyyy
	private boolean isDateFormatValid(String strDate) throws ApplicationException{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(strDate);
			return true;
		}catch (Exception e) {
			throw new ApplicationException("COuldn't validate dates",ErrorType.DATA_VALIDATION_ERROR,e);
		}
	}

	// validates that title does not exists and that length is lower than 15
	private boolean isTitleValid(String title) throws ApplicationException {

		if(!couponDAO.isTitleExists(title)){

			if (title.length()<=25){

				return true;
			}

			else {
				throw new ApplicationException("Title too long", ErrorType.DATA_VALIDATION_ERROR);
			}
		}
		throw new ApplicationException("Title already exists", ErrorType.DATA_VALIDATION_ERROR);
	}

	// will create a new coupon if all data comes valid
	public void createCoupon(Coupon coupon) throws ApplicationException {

		if (validteCouponData(coupon)){

			couponDAO.createCoupon(coupon);
		}

	}

	// In case that the ID is found in db, remove this specific coupon
	public void removeCoupon(long couponID) throws ApplicationException {

		if (couponDAO.isIdExists(couponID)){

			couponDAO.removeCoupon(couponID);
		}

		else {

			throw new ApplicationException("Couldn't find coupon num: "+couponID, ErrorType.DATA_VALIDATION_ERROR);
		}

	}

	// if date is valid preform update on the coupon
	public void updateCoupon(Coupon coupon) throws ApplicationException {

		if (validteCouponData(coupon)){

			couponDAO.updateCoupon(coupon);
		}

		else {

			throw new ApplicationException("Couldn't update company due to bad info", ErrorType.DATA_VALIDATION_ERROR);
		}

	}

	// extract specific coupon and return it if  not null
	public Coupon getCoupon(long couponID) throws ApplicationException {

		Coupon coupon = couponDAO.getCoupon(couponID);

		if (coupon.equals(null)){

			throw new ApplicationException("Couldn't find Coupon number: "+couponID, ErrorType.DATA_VALIDATION_ERROR);
		}

		return coupon;

	}

	// Extract all coupons unless there is no coupons
	public List<Coupon> getAllCoupons() throws ApplicationException {

		List<Coupon> allCoupons = couponDAO.getAllCoupons();

		if (allCoupons.isEmpty()){

			throw new ApplicationException("Couldn't get coupons", ErrorType.GENERAL_ERROR);
		}

		return allCoupons;
	}

	// Extract all coupons by company unless there aren't any listed coupons for this
	// company
	public List<Coupon> getCouponsByCompany(long companyID) throws ApplicationException {
		if(userDAO.isUserExists(companyID)) {

			List<Coupon> allCouponsByCompany = couponDAO.getCouponsByCompany(companyID);

			return allCouponsByCompany;
		}
		else {
			throw new ApplicationException("User dosent exists", ErrorType.DATA_VALIDATION_ERROR);
		}
	}

	//Extract all coupons by client from join table unless there aren't any

	public List<Coupon> getAllCouponsByClient(long customerID) throws ApplicationException {

		List<Coupon> allCouponsByClient = couponDAO.getAllCouponsByClient(customerID);

		return allCouponsByClient;
	}

	// Extract coupons by their type unless there aren't any
	public List<Coupon> getCouponByType(CouponType couponType) throws ApplicationException {

		List<Coupon> allCouponsByType = couponDAO.getCouponByType(couponType);

		if (allCouponsByType.isEmpty()){

			throw new ApplicationException("Couldn't get coupons for coupon type: "+couponType, ErrorType.DATA_VALIDATION_ERROR);
		}

		return allCouponsByType;
	}
	// Two stages - On the first one we will reduce the amount of coupons by 1
	// At the second stage we will register this purchase in customer_coupon table.
	// In case that the second process will fail we will need to reverse the first process as well
	// there for we will call cancel purchase on the catch section.
	public void buyCoupon(long couponID,long customerID) throws ApplicationException{
		
			if (couponDAO.isIdExists(couponID)){
				if(CouponDAO.getAmountOfCoupons(couponID)>0){
					couponDAO.updatePurchaseOnCustomerCoupon(couponID,customerID);
					try{
					couponDAO.buyCoupon(couponID);
					}
					catch(Exception e){
						//reverses the first process in case of a failure
						couponDAO.cancelPurchaseFromCustomerCoupon( couponID, customerID);
						throw new ApplicationException("Coouldn't complete buy coupon", ErrorType.GENERAL_ERROR);
					}
				}
			}
		
		else {
			throw new ApplicationException("Coupon Id does not exists or amout of coupons is 0", ErrorType.DATA_VALIDATION_ERROR);
		}
	}

	public List<Coupon> getCouponsByComapnyAndUpToPrice(long companyID,int price)throws ApplicationException{
		List<Coupon> couponsByCompanyAndUpToPrice =
				couponDAO.getCouponsByComapnyAndUpToPrice(companyID, price);

		return couponsByCompanyAndUpToPrice;
	}
	public List<Coupon> getCouponsByCompnayAndType(long companyID, String type)throws ApplicationException{
		List<Coupon> couponsByCompanyAndType= couponDAO.getCouponsByCompnayAndType(companyID, type);
		return couponsByCompanyAndType;
	}
	public List<Coupon> getCouponsByCompanyAndUpToDate(long companyID,String date) throws ApplicationException{
		List<Coupon>couponsByCompanyAndUpToDate=couponDAO.getCouponsByCompanyAndUpToDate( companyID, date);
		return couponsByCompanyAndUpToDate;
	}
	public List<Coupon> getCouponsByCompanyAndAmount(long companyID) throws ApplicationException{
		return this.couponDAO.getCouponsByCompanyAndAmount(companyID);
	}
}
