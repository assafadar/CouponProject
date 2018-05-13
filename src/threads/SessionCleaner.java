package threads;

import java.util.Calendar;
import java.util.Map;
import java.util.TimerTask;

import beans.User;
import managers.SessionManager;

public class SessionCleaner extends TimerTask{


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void run() {
		SessionManager sessionManager = SessionManager.getSessionManagerInstance();
		Map<String,User> sessionMap = sessionManager.getObjMap(); 
		long hour =  Calendar.getInstance().getTimeInMillis();
		for(Map.Entry<String,User> entry : sessionMap.entrySet()) {
			if(hour - entry.getValue().getLoginTimeInML()>=3600000) {
				sessionMap.remove(entry);
			}
		}
	}

}
