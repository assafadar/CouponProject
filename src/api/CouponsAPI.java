package api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import beans.Coupon;
import beans.User;
import controllers.CouponsController;
import enums.CouponType;
import exceptions.ApplicationException;
import utils.MailUtils;
import utils.UserUtils;
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/coupons")
public class CouponsAPI {
	private CouponsController couponsController;
	private User user;
	public CouponsAPI() {
		super();
		this.user= new User();
		this.couponsController =new CouponsController();
	}

	@GET
	public List<Coupon> getAllCoupons() throws ApplicationException{
		List<Coupon>allCoupons=couponsController.getAllCoupons();
		return allCoupons;
	}

	@POST
	public void createCoupon(Coupon coupon) throws ApplicationException{

		couponsController.createCoupon(coupon);
	}
	@POST
	@Path("/buy/{couponID}")
	public void purchaseCoupon(@PathParam("couponID") long couponID,@Context HttpServletRequest req) throws ApplicationException{
		this.user=UserUtils.getUserFromCookie(req.getCookies());
		couponsController.buyCoupon(couponID, user.getId());
		Coupon coupon = this.couponsController.getCoupon(couponID);
		MailUtils.sendAftherPurchaseEmail(coupon, user);
	}
	@DELETE
	@Path("{couponID}")
	public void removeCoupon(@PathParam("couponID")long couponID)throws ApplicationException{
		couponsController.removeCoupon(couponID);
	}
	@PUT
	public void updateCoupon(Coupon coupon)throws ApplicationException{
		couponsController.updateCoupon(coupon);
	}
	@GET
	@Path("{couponID}")
	public Coupon getCoupon(@PathParam("couponID") long couponID) throws ApplicationException{
		return couponsController.getCoupon(couponID);
	}
	@GET
	@Path("company/{companyID}")
	public List<Coupon> getAllCouponsByCompany(@PathParam("companyID") long companyID) throws ApplicationException{
		return couponsController.getCouponsByCompany(companyID);
	}

	@GET
	@Path("customer/{customerID}")
	public List<Coupon> getAllCouponsByCustomer(@PathParam("customerID") long customerID) throws ApplicationException{
		return couponsController.getAllCouponsByClient(customerID);
	}

	@GET
	@Path("/type/{type}")
	public List<Coupon> getAllCouponsByType(@PathParam("type") String type) throws ApplicationException{
		return couponsController.getCouponByType(CouponType.getEnamulatorForCouponType(type));

	}

	@GET
	@Path("maxPrice/{companyID}/{maxPrice:.+}")
	public List<Coupon> getCouponsByComapnyAndUpToPrice(@PathParam("companyID") long companyID, @PathParam("maxPrice") int maxPrice)throws ApplicationException{
		return couponsController.getCouponsByComapnyAndUpToPrice(companyID, maxPrice);
	}

	@GET
	@Path("/type/{companyID}/{type:.+}")
	public List<Coupon> getCouponsByComapnyAndType(@PathParam("companyID") long companyID, @PathParam("type") String type)throws ApplicationException{
		return couponsController.getCouponsByCompnayAndType(companyID, type);
	}

	@GET
	@Path("date/{companyID}/{endDate:.+}")
	public List<Coupon> getCouponsByComapnyAndUpToDate(@PathParam("companyID") long companyID, @PathParam("endDate") String endDate)throws ApplicationException{
		return couponsController.getCouponsByCompanyAndUpToDate(companyID, endDate);
	}
	@GET
	@Path("amount/{userID}")
	public List<Coupon> getAllCouponsByCompanyAndAmount(@PathParam("userID") long userID) throws ApplicationException{
		return this.couponsController.getCouponsByCompanyAndAmount(userID);
	}
}
