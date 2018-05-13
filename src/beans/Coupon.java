
package beans;

import javax.xml.bind.annotation.XmlRootElement;

import enums.CouponType;
import exceptions.ApplicationException;
@XmlRootElement
public class Coupon {
	
	private long id;
	private String title;
	private String startDate;
	private String endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String img;
	private long companyID;
	
	public Coupon(){

	}
	
	
	public Coupon(String title, String startDate, String endDate, int amount, String type, String message, double price,
			String img, long companyID) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = CouponType.getEnamulatorForCouponType(type);
		this.message = message;
		this.price = price;
		this.img = img;
		this.companyID = companyID;
	}


	public Coupon(long id, String title, String startDate, String endDate, int amount, String type, String message,
			double price, String img, long companyID) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = CouponType.valueOf(type);
		this.message = message;
		this.price = price;
		this.img = img;
		this.companyID = companyID;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public CouponType getType() {
		return type;
	}
	public String getTypeString(){
		String type = this.type.toString();
		return type;
	}
	public void setType(CouponType type) throws ApplicationException {
		this.type = CouponType.getEnamulatorForCouponType(type.toString());
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getCompanyID() {
		return companyID;
	}
	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", img=" + img
				+ ", companyID=" + companyID + "]";
	}
	
}
