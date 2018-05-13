package api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.User;
import controllers.UserController;
import exceptions.ApplicationException;
import utils.UserUtils;
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAPI {
	private UserController userController;
	
	public UserAPI() {
		this.userController = new UserController();
	}
	@POST
	@Path("/register")
	public void createUser(User user) throws ApplicationException{
		this.userController.createUser(user);
		
		UserUtils.sendWelcomeEmailsToNewUser(user);
	}
	
	@GET
	public List<User> getAllUsers()throws ApplicationException{
		return this.userController.getAllUsers();
	}
	@PUT
	public void updateUser(User user)throws ApplicationException{
		this.userController.updateUser(user);
	}
	@DELETE
	@Path("{userID}")
	public void deleteUser(@PathParam("userID") long userID) throws ApplicationException{
		this.userController.removeUser(userID);
	}
	@GET
	@Path("{userID}")
	public User getUser(@PathParam("userID") long userID) throws ApplicationException{
		return this.userController.getUser(userID);
	}
	@GET
	@Path("/type/{type}")
	public List<User> getUsersByType(@PathParam("type") String type) throws ApplicationException{
		return this.userController.getAllUserByType(type);
	}
}
