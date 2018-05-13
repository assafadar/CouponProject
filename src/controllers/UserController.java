package controllers;



import java.util.List;

import beans.User;
import dao.UserDAO;
import enums.ErrorType;
import enums.UserType;
import exceptions.ApplicationException;
import utils.DataValidationUtils;
import utils.EncryptionUtils;

public class UserController {
	private UserDAO userDAO;

	public UserController() {
		this.userDAO = new UserDAO();
	}

	public User login(String userName, String hashedPassword) throws ApplicationException {
		if (userName==null || hashedPassword ==null) {
			return null;
		}
		long userID = this.userDAO.login(userName, hashedPassword);
		if (userID!=0) {
			User user = this.userDAO.getUser(userID);
			return user;
		}
		return null;

	}

	public void createUser(User user)throws ApplicationException{
		if (DataValidationUtils.validateEmail(user.getEmail())) {	
			if(!isEmailExists(user.getEmail())){
				if(DataValidationUtils.validatePassword(user.getPassword(), user.getName())) {
					user.setPassword(EncryptionUtils.getHashForString(user.getPassword()));
					user.setId(this.userDAO.createUser(user));
				}
				else {
					throw new ApplicationException("Password too weak", ErrorType.DATA_VALIDATION_ERROR);
				}
			}
			else {
				throw new ApplicationException("Email Already exists", ErrorType.DATA_VALIDATION_ERROR);
			}

		}
		else {
			throw new ApplicationException("User's dara is not valid", ErrorType.USER_INPUT_ERROR);
		}
	}

	private boolean isEmailExists(String email) throws ApplicationException {
		if(this.userDAO.isEmailExists(email)) {
			return true;
		}
		return false;
	}
	public void removeUser(long userID) throws ApplicationException{
		if(isUserExistsById(userID)) {
			this.userDAO.removeUser(userID);
		}
	}
	private boolean isUserExistsById(long userID) throws ApplicationException {
		if(this.userDAO.isUserExists(userID)) {
			return true;
		}
		return false;
	}
	public void updateUser(User user)throws ApplicationException{
		if(DataValidationUtils.validateEmail(user.getEmail())) {
			if(this.isUserExistsById(user.getId())) {
				this.userDAO.updateUser(user);
			}
		}
		else {
			throw new ApplicationException("Couldn't match data",ErrorType.DATA_VALIDATION_ERROR);
		}
	}
	public User getUser(long userID)throws ApplicationException{
		if(isUserExistsById(userID)) {
			return this.userDAO.getUser(userID);
		}
		return null;
	}

	public List<User> getAllUsers()throws ApplicationException{
		return this.userDAO.getAllUsers();
	}


	public boolean isUserActivated(long userID) throws ApplicationException{
		if(isUserExistsById(userID)) {
			return this.isUserActivated(userID);
		}
		return false;
	}
	public List<User> getAllUserByType(String type) throws ApplicationException {

		return this.userDAO.getAllUsersByRole(UserType.getEnamulatorForCouponType(type));
	}
}

