package api;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import controllers.UserController;
import dataObjects.LoginData;
import exceptions.ApplicationException;
import utils.EncryptionUtils;
import utils.UserUtils;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginAPI {
	private UserController userController;

	public LoginAPI() {
		this.userController = new UserController();
	}
	@POST
	public User login(LoginData loginData, @Context HttpServletResponse res)throws ApplicationException{
		String password = EncryptionUtils.getHashForString(loginData.getPassword());
		User user = this.userController.login(loginData.getUserName(), password);
		if(user!=null) {
			UserUtils.addUserObjectToMap(user);
			addCustomCookieForLogin(res,user.getId());
			return user;
		}
		else {
			return null;
		}
		
	}
	private void addCustomCookieForLogin(HttpServletResponse res, long userID) throws ApplicationException {
		res.addCookie(UserUtils.addUserCookieToResponse(userID));
	}

}
