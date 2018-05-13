package managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import beans.User;
import exceptions.ApplicationException;
import threads.SessionCleaner;
import utils.EncryptionUtils;

public class SessionManager {
	private Map<String,User> encryptUserObjectMap = null; 
	private static SessionManager sessionManager = null;
	private TimerTask sessionCleaner;
	private SessionManager() {
		encryptUserObjectMap = new HashMap<String,User>();
		this.sessionCleaner = new SessionCleaner();
		startSessionManagerThreadRun();
	}
	
	private void startSessionManagerThreadRun() {
		this.sessionCleaner = new SessionCleaner();
		Timer timer = new Timer(true);		
		timer.scheduleAtFixedRate(sessionCleaner,0,1000*60*60*24);
	}

	public static SessionManager getSessionManagerInstance() {
		if(sessionManager==null) {
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}
	public void addSealedUserToSessionManager(User user) throws ApplicationException {
		String stringUserID = String.valueOf(user.getId());
		this.encryptUserObjectMap.put(EncryptionUtils.getHashForString(stringUserID), user);
	}
	public User getUserFromSessionManager(String hashedId) throws ApplicationException {
		return this.encryptUserObjectMap.get(hashedId);
	}
	public void deleteUserFromSessionManager(long userID) throws ApplicationException {
		String stringUserID = String.valueOf(userID);
		this.encryptUserObjectMap.remove(EncryptionUtils.getHashForString(stringUserID));
	}
	public Map<String,User> getObjMap(){
		return this.encryptUserObjectMap;
	}
}
