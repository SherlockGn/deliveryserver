package com.gth.delivery.util;

import java.util.Random;

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

	public static boolean checkPrice(String price) {
		int dotNumber = 0;
		if (isNull(price))
			return false;
		price = price.trim();
		if (price.charAt(0) == '.')
			return false;
		if (price.charAt(price.length() - 1) == '.')
			return false;
		for (int i = 0; i < price.length(); i++) {
			char c = price.charAt(i);
			if (Character.isDigit(c))
				continue;
			if (c == '.') {
				if (dotNumber == 0) {
					dotNumber++;
					continue;
				} else
					return false;
			}
			return false;
		}
		return true;
	}

	private static final char[] map = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	public static String genRandom(int number) {

		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < number; i++) {
			int x = r.nextInt(map.length);
			sb.append(map[x]);
		}
		return sb.toString();
	}

	public static String getBase64(byte[] buffer) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buffer.length; i++) {
			byte b0 = buffer[i++], b1 = 0, b2 = 0;

			int bytes = 3;
			if (i < buffer.length) {
				b1 = buffer[i++];
				if (i < buffer.length) {
					b2 = buffer[i];
				} else {
					bytes = 2;
				}
			} else {
				bytes = 1;
			}

			int total = (b0 << 16) | (b1 << 8) | b2;

			switch (bytes) {
			case 3:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append(map[(total >> 6) & 0x3f]);
				sb.append(map[total & 0x3f]);
				break;

			case 2:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append(map[(total >> 6) & 0x3f]);
				sb.append('=');
				break;

			case 1:
				sb.append(map[(total >> 18) & 0x3f]);
				sb.append(map[(total >> 12) & 0x3f]);
				sb.append('=');
				sb.append('=');
				break;
			}
		}

		return sb.toString();
	}
}
