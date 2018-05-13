package dao;

import java.util.List;
import beans.Coupon;
import enums.CouponType;
import exceptions.ApplicationException;

public interface ICouponDAO {
	public void createCoupon(Coupon coupon) throws ApplicationException;
	public void removeCoupon(long couponID) throws ApplicationException;
	public void updateCoupon(Coupon coupon) throws ApplicationException;
	public Coupon getCoupon(long couponID) throws ApplicationException;
	public List<Coupon> getAllCoupons() throws ApplicationException;
	public List<Coupon> getCouponsByCompany(long companyID) throws ApplicationException;
	public List<Coupon> getAllCouponsByClient(long customerID) throws ApplicationException;
	public List<Coupon> getCouponByType(CouponType couponType) throws ApplicationException;
	public boolean isTitleExists(String title)throws ApplicationException;
	public boolean isIdExists(long couponID) throws ApplicationException;
	public void buyCoupon(long couponID) throws ApplicationException;
	public void deleteExpiredCoupons(String currentDate)throws ApplicationException;
	public List<Coupon> getCouponsByComapnyAndUpToPrice(long companyID, int price) throws ApplicationException;
	public List<Coupon> getCouponsByCompnayAndType(long companyID, String type)throws ApplicationException;
	public List<Coupon> getCouponsByCompanyAndUpToDate(long companyID, String date)throws ApplicationException;
	public void updatePurchaseOnCustomerCoupon(long couponID, long customerID)throws ApplicationException;
	public void cancelPurchaseFromCustomerCoupon(long couponID, long customerID) throws ApplicationException;
	public List<Coupon> getCouponsByCompanyAndAmount(long companyID) throws ApplicationException;
	
}
