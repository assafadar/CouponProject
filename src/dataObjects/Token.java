package dataObjects;
import java.time.LocalDateTime;

import exceptions.ApplicationException;
import utils.EncryptionUtils;


public class Token {
	private long userID;
	private String tokenizedUserID;
	private LocalDateTime timeNow;

	public Token(long userID) throws ApplicationException {
		this.timeNow = LocalDateTime.now();
		this.userID = userID;
		this.tokenizedUserID  = EncryptionUtils.getHashForString(String.valueOf(userID));
	}
	public LocalDateTime getTime() {
		return this.timeNow;
	}
	public long getUserID() {
		return this.userID;
	}
	public String getTokenizedUserID() {
		return tokenizedUserID;
	}

}
