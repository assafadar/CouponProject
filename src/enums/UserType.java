package enums;

import java.util.HashMap;

import exceptions.ApplicationException;

public enum UserType {
	Admin,
	Company,
	Customer;
	
	public static HashMap<String,UserType> enamulatorForUserType = null;

	public static UserType getEnamulatorForCouponType(String type) {
		if(enamulatorForUserType==null) {
			enamulatorForUserType = new HashMap<String,UserType>();
			addEnumsToMap();
		}
		return getEnumFromMap(type);
	}

	private static void addEnumsToMap() {
		for(UserType type:UserType.values()) {
			enamulatorForUserType.put(type.toString(), type);
		}
		
	}
	private static UserType getEnumFromMap(String type) {
		return enamulatorForUserType.get(type);
	}
}
