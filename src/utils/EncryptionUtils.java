package utils;



import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import beans.User;
import enums.ErrorType;
import exceptions.ApplicationException;

public class EncryptionUtils {
	private static Cipher ecipher;
	private static Cipher dcipher;
	private static SecretKey key;

	private static void initalizeEncryptionUtils() throws ApplicationException{
		try {
			key = KeyGenerator.getInstance("DES").generateKey();
			ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher = Cipher.getInstance("DES");
			dcipher.init(Cipher.DECRYPT_MODE, key);
			
		}catch(Exception e) {
			throw new ApplicationException("Couldn't generate enceryption keys", ErrorType.GENERAL_ERROR);
		}

	}
	public static String getHashForString(String stringForHashing) throws ApplicationException{

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(stringForHashing.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to h	hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		}catch (Exception e) {
			throw new ApplicationException("Couldn't encrypte password please try again", ErrorType.GENERAL_ERROR);
		}

	}
	// Encrypting the user object that will be added to the cookie
	public static SealedObject encryptUserObject(User user)throws ApplicationException{
		if(ecipher==null || dcipher==null || key==null) {
			initalizeEncryptionUtils();
		}
		try {
			SealedObject sealed = new SealedObject(String.valueOf(user.getId()), ecipher);
			return sealed;
		} catch (Exception e) {
			throw new ApplicationException("Encryption Data Error", ErrorType.GENERAL_ERROR);
		}
		
	}
	//Decrypting the user object from the SealedObject
	public static String decryptUserFromCookie(SealedObject sealed) throws ApplicationException{
		try {
			String userID = (String) sealed.getObject(dcipher);
			return userID;
		}catch(Exception e) {
			throw new ApplicationException("Couldn't decrypt message", ErrorType.GENERAL_ERROR);
		}
	}
}

