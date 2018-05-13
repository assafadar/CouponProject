package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import beans.User;
import enums.ErrorType;
import enums.UserType;
import exceptions.ApplicationException;
import utils.JdbcUtils;

public class UserDAO {

	public long createUser(User user) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet = null;
		try {
			connection=JdbcUtils.getConnection();
			String sqlQuery = "insert into users(name,user_name,email,password,role) values(?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sqlQuery,Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getRole().toString());

			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			long userID = 0;
			while (resultSet.next()) {
				userID = resultSet.getLong(1);
			}
			return userID;
		}catch (Exception e) {
			throw new ApplicationException("Couldn't create new user", ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

	} 
	
	public User getUser(long companyID) throws ApplicationException {
		Connection conenction = null;
		PreparedStatement preparedStatment=null;
		ResultSet resultSet = null;
		try {
			conenction=JdbcUtils.getConnection();
			String sqlQuery = "select * from users where id=?";
			preparedStatment= conenction.prepareStatement(sqlQuery);
			preparedStatment.setLong(1,companyID);
			resultSet = preparedStatment.executeQuery();
			User user = null;
			while(resultSet.next()) {
				user = createUserFromResultSet(resultSet);
			}
			return user;
		}catch(Exception e) {
			throw new ApplicationException("Couldn't get user obj info", ErrorType.CONNECTION_ERROR,e);
		}
	}

	public void removeUser(long userID) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement =null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery ="delete from users where id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setLong(1, userID);

			preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw new ApplicationException("Couldn't remove user", ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	} 

	public void updateUser(User user) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery ="update users set name=?, user_name=?, email=? where id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setLong(4, user.getId());

			 preparedStatement.executeUpdate();
		}catch (Exception e) {
			throw new ApplicationException("Couldn't update user", ErrorType.CONNECTION_ERROR,e);
		}finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	public List<User> getAllUsers() throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select * from users";
			preparedStatement =connection.prepareStatement(sqlQuery);
			resultSet = preparedStatement.executeQuery();
			List<User> allUsers = new ArrayList<>();
			while(resultSet.next()) {
				allUsers.add(createUserFromResultSet(resultSet));
			}
			return allUsers;
		}catch(Exception e) {
			throw new ApplicationException("Couldn't get all users", ErrorType.CONNECTION_ERROR,e);
		}finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<User> getAllUsersByRole(UserType role) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select * from users where role=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			preparedStatement.setString(1, role.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			List<User> usersByType = new ArrayList<>();
			while(resultSet.next()) {
				usersByType.add(createUserFromResultSet(resultSet));
			}
			return usersByType;
		}catch (Exception e) {
			throw new ApplicationException("Couldn't get users by type: "+role.toString(), ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		
			user.setEmail(resultSet.getString("email"));
			user.setId(resultSet.getLong("id"));
			user.setName(resultSet.getString("name"));
			user.setPassword(resultSet.getString("password"));
			user.setUserName(resultSet.getString("user_name"));
			user.setRole(resultSet.getString("role"));
		
		return user;
	}
	
	public long login(String userName,String password) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet=null;
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery="select id from users where password=? AND user_name=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, userName);
			resultSet = preparedStatement.executeQuery();
			long userID =0;
			if(resultSet.next()) {
				userID = resultSet.getLong("id");
			}
			return userID;
		}catch(Exception e) {
			throw new ApplicationException("Couldn't access users database", ErrorType.CONNECTION_ERROR,e);
		}

	}
	
	
	public boolean isUserExists(long userID) throws ApplicationException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select * from users where id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setLong(1, userID);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new ApplicationException("Couldnlt check for user", ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public boolean isUserExistsByName(String userName) throws ApplicationException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select id from users where name=?";
			
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			preparedStatement.setString(1, userName);
			
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			throw new ApplicationException("Couldn't locate user By name", ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public boolean isEmailExists(String email) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sqlQuery = "select id from users where email =?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			
			preparedStatement.setString(1, email);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				return true;
			}
			return false;
		}catch(Exception e) {
			throw new ApplicationException("Couldn't validate user's email", ErrorType.CONNECTION_ERROR,e);
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}


}

