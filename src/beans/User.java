package beans;



import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

import enums.UserType;
@XmlRootElement
public class User{
	private long id;
	private String role;
	private UserType userType;
	private String email;
	private String name;
	private String userName;
	private String password;
	private long loginTimeML;
	
	public User(long id, String role, String email, String name, String password, String userName) {
		super();
		this.id = id;
		this.role = role;
		this.email = email;
		this.name = name;
		this.password =password;
		this.userName = userName;
		this.loginTimeML = Calendar.getInstance().getTimeInMillis();
	}

	public User() {
		this.loginTimeML = Calendar.getInstance().getTimeInMillis();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String  role) {
		this.role = role;
		this.userType = UserType.getEnamulatorForCouponType(role);
	}
	public UserType getUserType() {
		return this.userType;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public long getLoginTimeInML() {
		return this.loginTimeML;
	}
}
