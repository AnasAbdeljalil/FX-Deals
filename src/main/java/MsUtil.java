package com.xx.fx.deals;

import java.util.Collection;
import java.util.Date;

public class MsUtil {

	private MsUtil() {}

	////////////////////////////////////////////////////////////////////////////////
	public static boolean isAnyEmpty(String... data) {
		for (String info : data)
			if (isEmpty(info))
				return true;
		return false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("");
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.isEmpty();
	}

	///////////////////////////////////////////////////////////////////////
	public static RuntimeException throww(Throwable t) {
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		}
		throw new FxRequestException(t);
	}

	//////////////////////////////////////////////////////////////////////////////
	public static int toInteger(Object value) {
		if (value == null || value.toString().trim().equals("")) {
			return 0;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		return (int) toDouble(value);
	}

	//////////////////////////////////////////////////////////////////////////////
	public static double toDouble(Object value) {
		if (value == null || value.toString().trim().equals("")) {
			return 0;
		}
		if (value instanceof Double) {
			return (double) value;
		}
		if (value instanceof Date) {
			final Date date = (Date) value;
			return date.getTime();
		}
		return Double.parseDouble(value.toString());
	}

}
