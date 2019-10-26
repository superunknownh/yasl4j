package mx.digitalbusiness.lib.yasl4j.utils;

public final class StringUtils {

	private StringUtils() {}

	public static String getString(String rawString, String defaultValue) {
		return rawString != null ? rawString : defaultValue;
	}

	public static String getString(String rawString) {
		return getString(rawString, null);
	}

	public static int getInt(String rawString, int defaultValue) {
		if (rawString != null) {
			try {
				return Integer.parseInt(rawString);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static boolean getBoolean(String rawString, boolean defaultValue) {
		if (rawString != null && rawString.trim().length() > 0) {
			try {
				return Boolean.valueOf(rawString);
			} catch (NumberFormatException ex) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public static boolean getBoolean(String rawString) {
		return getBoolean(rawString, false);
	}

	public static String cutString(String stringToCut, int length) {
		return stringToCut.length() <= length ? stringToCut : stringToCut.substring(0, length) + "...";
	}

	public static boolean isNullOrEmpty(String string) {
		return string == null || string.trim().length() == 0;
	}

}
