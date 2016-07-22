package com.gth.delivery.util;

public class StringUtils {
	public static boolean isNumber(String str) {
		if (str == null)
			return false;
		str = str.trim();
		if (isNull(str))
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public static boolean isNull(String str) {
		if (str == null)
			return true;
		str = str.trim();
		if ("".equals(str))
			return true;
		return false;
	}

	public static int legalGender(String gender) {
		if (gender == null)
			return -1;
		String[] argTrue = new String[] { "1", "true", "True", "TRUE" };
		for (int i = 0; i < argTrue.length; i++)
			if (argTrue[i].equals(gender))
				return 1;
		String[] argFalse = new String[] { "0", "false", "False", "FALSE" };
		for (int i = 0; i < argFalse.length; i++)
			if (argFalse[i].equals(gender))
				return 0;
		return -1;
	}

	public static String phoneSettle(String phone) {
		if (phone == null)
			return null;
		String[] phones = phone.split(";");
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < phones.length; i++) {
			if (isNull(phones[i].trim()))
				return null;
			if (!isNumber(phones[i].trim()))
				return null;
			if (first == false)
				result.append(";");
			result.append(phones[i].trim());
			first = false;
		}
		return result.toString();
	}
	
	public static String addressSettle(String address) {
		if (address == null)
			return null;
		String[] addresses = address.split(";");
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < addresses.length; i++) {
			if (isNull(addresses[i].trim()))
				return null;
			if (first == false)
				result.append(";");
			result.append(addresses[i].trim());
			first = false;
		}
		return result.toString();
	}
}
