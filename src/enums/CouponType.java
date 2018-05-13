package enums;

import java.util.HashMap;


public enum CouponType {
	Resturants, Food, Electricity, Health, Sports, Camping, Traveling,Pets;

	public static HashMap<String,CouponType> enamulatorForCouponType = null;

	public static CouponType getEnamulatorForCouponType(String type) {
		if(enamulatorForCouponType==null) {
			enamulatorForCouponType = new HashMap<String,CouponType>();
			addEnumsToMap();
		}
		return getEnumFromMap(type);
	}

	private static void addEnumsToMap() {
		for(CouponType type:CouponType.values()) {
			enamulatorForCouponType.put(type.toString(), type);
		}
		
	}
	private static CouponType getEnumFromMap(String type) {
		return enamulatorForCouponType.get(type);
	}
}
