package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Coupon;
import enums.ErrorType;
import enums.CouponType;
import exceptions.ApplicationException;
import utils.JdbcUtils;

public class CouponDAO implements ICouponDAO {

	// Create new coupon
	@Override
	public void createCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "insert into coupons (title, start_date, end_date,amount, type, message, price, image,companyID) values (?,?,?,?,?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setString(2, coupon.getStartDate());
			preparedStatement.setString(3, coupon.getEndDate());
			preparedStatement.setInt(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getType().toString());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImg());
			preparedStatement.setLong(9, coupon.getCompanyID());

			preparedStatement.executeUpdate();

			System.out.println("New coupon was created");
			

		} catch (Exception e) {

			throw new ApplicationException("Couldn't create Coupon", ErrorType.CONNECTION_ERROR, e);
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	// Remove coupon
	@Override
	public void removeCoupon(long couponID) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "delete from coupons where ID =?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, couponID);

			preparedStatement.executeUpdate();

			System.out.println("Coupon was deleted");

		} catch (Exception e) {

			throw new ApplicationException("Couldn't delete coupon number: " + couponID, ErrorType.CONNECTION_ERROR, e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	// Update coupon
	@Override
	public void updateCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "update coupons set end_date=?,amount=?,price=?, image=? where ID=?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, coupon.getEndDate());
			preparedStatement.setInt(2, coupon.getAmount());
			preparedStatement.setDouble(3, coupon.getPrice());
			preparedStatement.setString(4, coupon.getImg());
			preparedStatement.setLong(5, coupon.getId());

			preparedStatement.executeUpdate();

			System.out.println("Coupon number " + coupon.getId() + " was updated");

		} catch (Exception e) {

			throw new ApplicationException("Couldn't update coupon", ErrorType.CONNECTION_ERROR, e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	// Extract coupon
	@Override
	public Coupon getCoupon(long couponID) throws ApplicationException {

		Connection connection = null;

		ResultSet resultSet = null;

		PreparedStatement preparedStatement = null;

		Coupon coupon = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select * from coupons where ID =?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, couponID);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {

				return null;
			}

			coupon = createCoupon(resultSet);

			return coupon;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't find coupon", ErrorType.CONNECTION_ERROR, e);
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
			;
		}
	}

	private Coupon createCoupon(ResultSet resultSet) throws ApplicationException {

		Coupon coupon = new Coupon();

		try {

			coupon.setAmount(resultSet.getInt("amount"));
			coupon.setCompanyID(resultSet.getLong("companyID"));
			coupon.setStartDate(resultSet.getString("start_date"));
			coupon.setEndDate(resultSet.getString("end_date"));
			coupon.setId(resultSet.getLong("ID"));
			coupon.setImg(resultSet.getString("image"));
			coupon.setMessage(resultSet.getString("message"));
			coupon.setPrice(resultSet.getDouble("price"));
			coupon.setTitle(resultSet.getString("title"));
			coupon.setType(CouponType.getEnamulatorForCouponType( resultSet.getString("type")));

			return coupon;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't assemble coupon object", ErrorType.CONNECTION_ERROR, e);
		}
	}

	// Extract all coupons
	@Override
	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		List<Coupon> coupons = null;

		Coupon coupon = null;
		try{

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select * from coupons";

			preparedStatement = connection.prepareStatement(sqlQuery);

			resultSet = preparedStatement.executeQuery();

			coupons = new ArrayList<>();
			while (resultSet.next()){
				coupon = createCoupon(resultSet);
				coupons.add(coupon);
			}

			return coupons;

		}
		catch(Exception e)
		{

			throw new ApplicationException("Couldn't fetch all Coupons", ErrorType.CONNECTION_ERROR, e);
		}finally
		{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}

	}

	// Extract all coupons by company
	@Override
	public List<Coupon> getCouponsByCompany(long companyID) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		List<Coupon> couponsByCompany = null;

		Coupon coupon = null;
		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select *  from coupons where companyID=?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, companyID);

			resultSet = preparedStatement.executeQuery();

			couponsByCompany = new ArrayList<>();

			while (resultSet.next()) {

				coupon = createCoupon(resultSet);

				couponsByCompany.add(coupon);
			}

			return couponsByCompany;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't fetch all coupons for company id: " + companyID,
					ErrorType.CONNECTION_ERROR, e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	// Extract all coupons by client
	@Override
	public List<Coupon> getAllCouponsByClient(long customerID) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		Coupon coupon = null;

		List<Coupon> couponsByCustomer = null;
		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select *,customer_coupon.customer_id from coupons join customer_coupon on coupons.ID=customer_coupon.coupon_id where customer_id=?";

			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, customerID);
			resultSet = preparedStatement.executeQuery();

			couponsByCustomer = new ArrayList<Coupon>();

			while (resultSet.next()) {

				coupon = createCoupon(resultSet);

				couponsByCustomer.add(coupon);
			}

			return couponsByCustomer;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't get all coupons by customer", ErrorType.CONNECTION_ERROR, e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	// Extract all coupons by Type
	@Override
	public List<Coupon> getCouponByType(CouponType couponType) throws ApplicationException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Coupon coupon = null;
		List<Coupon> couponsByType = null;

		try {

			connection = JdbcUtils.getConnection();
			String sqlQuery = "select * from coupons where type=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, couponType.toString());
			resultSet = preparedStatement.executeQuery();

			couponsByType = new ArrayList<Coupon>();
			while (resultSet.next()) {
				coupon = createCoupon(resultSet);
				couponsByType.add(coupon);
			}

			return couponsByType;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't fetch all coupons by type: " + couponType,
					ErrorType.CONNECTION_ERROR, e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	// returns true if coupon title already exists
	public boolean isTitleExists(String title) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select ID from coupons where title =?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, title);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				return true;
			}

			return false;
		} catch (Exception e) {

			throw new ApplicationException("Couldn't search for title", ErrorType.CONNECTION_ERROR, e);
		}

		finally {

			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	// returns true if this coupon id exists
	public boolean isIdExists(long couponID) throws ApplicationException {

		Connection connection = null;

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		try {

			connection = JdbcUtils.getConnection();

			String sqlQuery = "select title from coupons where ID =?";

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, couponID);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				return true;
			}

			return false;

		} catch (Exception e) {

			throw new ApplicationException("Couldn't search for company num: " + couponID, ErrorType.CONNECTION_ERROR,
					e);
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public void buyCoupon(long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		int amountOfCoupons = CouponDAO.getAmountOfCoupons(couponID);
		try{
			connection=JdbcUtils.getConnection();
			amountOfCoupons-=1;
			String sqlQuery= "update coupons set amount=? where ID=?";

			preparedStatement=connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, amountOfCoupons);
			preparedStatement.setLong(2, couponID);

			preparedStatement.executeUpdate();
		}catch (Exception e){
			throw new ApplicationException("Couldn't update amount of coupons for coupon id: "+couponID, ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void deleteExpiredCoupons(String currentDate) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = JdbcUtils.getConnection();
			String sqlQuery ="delete from coupons where end_date=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, currentDate);
			preparedStatement.executeUpdate();
		}catch (Exception e){
			throw new ApplicationException("Couldn't preform daily service from updater thread", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}


	public static int getAmountOfCoupons(long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try{
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select amount from coupons where ID=?";

			preparedStatement=connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, couponID);

			resultSet=preparedStatement.executeQuery();
			if (resultSet.next()){
				return resultSet.getInt("amount");
			}
			return 0;
		}catch (Exception e){
			throw new ApplicationException("Couldn't get amount of coupons", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public List<Coupon> getCouponsByComapnyAndUpToPrice(long companyID, int price) throws ApplicationException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		List<Coupon> couponsByComapnyAndUpToPrice;

		try{
			connection =JdbcUtils.getConnection();
			String sqlQuery = "select * from coupons where id=? and where price<=? ";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, companyID);
			preparedStatement.setInt(2, price);

			resultSet = preparedStatement.executeQuery();
			couponsByComapnyAndUpToPrice=new ArrayList<>();
			while(resultSet.next()){
				couponsByComapnyAndUpToPrice.add(createCoupon(resultSet));
			}

			return couponsByComapnyAndUpToPrice;
		}catch (Exception e){
			throw new ApplicationException("Couldn't get coupons by company and price", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public List<Coupon> getCouponsByCompnayAndType(long companyID, String type) throws ApplicationException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		List<Coupon> couponsByComapnyAndType;

		try{
			connection =JdbcUtils.getConnection();
			String sqlQuery = "select * from coupons where type=? and companyID=?";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, type);
			preparedStatement.setLong(2, companyID);
			

			resultSet = preparedStatement.executeQuery();
			couponsByComapnyAndType=new ArrayList<>();
			while(resultSet.next()){
				couponsByComapnyAndType.add(createCoupon(resultSet));
			}

			return couponsByComapnyAndType;
		}catch (Exception e){
			throw new ApplicationException("Couldn't get coupons by company and type", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public List<Coupon> getCouponsByCompanyAndUpToDate(long companyID, String date) throws ApplicationException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		List<Coupon> couponsByComapnyAndUpToDate;

		try{
			connection =JdbcUtils.getConnection();
			String sqlQuery = "select * from coupons where companyID=? AND end_date<=? ";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, companyID);
			preparedStatement.setString(2, date);

			resultSet = preparedStatement.executeQuery();
			couponsByComapnyAndUpToDate=new ArrayList<>();
			while(resultSet.next()){
				couponsByComapnyAndUpToDate.add(createCoupon(resultSet));
			}

			return couponsByComapnyAndUpToDate;
		}catch (Exception e){
			throw new ApplicationException("Couldn't get coupons by company and up to date", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public void updatePurchaseOnCustomerCoupon(long couponID, long customerID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement =null;
		try{
			connection = JdbcUtils.getConnection();
			String sqlQuery ="insert into customer_coupon(customer_id,coupon_id) values(?,?)";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, customerID);
			preparedStatement.setLong(2, couponID);

			preparedStatement.executeUpdate();
		}catch(Exception e){
			throw new ApplicationException("Couldn't update customer_coupon on purchase", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	@Override
	// Delete a purchase recoed from cutomer_coupon. 
	public void cancelPurchaseFromCustomerCoupon( long couponID,long customerID) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		try{
			connection = JdbcUtils.getConnection();
			String sqlQuery ="delete from customer_coupon where customer_id=? and coupon_id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, customerID);
			preparedStatement.setLong(2, couponID);

			preparedStatement.executeUpdate();
		}catch(Exception e){
			throw new ApplicationException("Couldn't update customer_coupon on purchase", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public List<Coupon> getCouponsByCompanyAndAmount(long companyID) throws ApplicationException {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		List<Coupon> couponsByComapnyAndAmount;

		try{
			connection =JdbcUtils.getConnection();
			String sqlQuery = "select * from coupons where companyID=? order by amount";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, companyID);

			resultSet = preparedStatement.executeQuery();
			couponsByComapnyAndAmount=new ArrayList<>();
			while(resultSet.next()){
				couponsByComapnyAndAmount.add(createCoupon(resultSet));
			}

			return couponsByComapnyAndAmount;
		}catch (Exception e){
			throw new ApplicationException("Couldn't get coupons by company and up to date", ErrorType.CONNECTION_ERROR,e);
		}
		finally{
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
}


