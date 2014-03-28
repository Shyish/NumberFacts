package com.zdvdev.numberfacts.common;

/**
 * source: http://stackoverflow.com/questions/17416595/date-validation-in-android
 */
public class Util {

	/**
	 * Validate date like mm/dd
	 *
	 * @param date
	 * 		date address for validation
	 * @return true valid date format, false invalid date format
	 */
	public static boolean validateMDDate(final String date) {
		String[] dateSplit = date.split("\\/");

		Integer month;
		Integer day;
		try {
			month = Integer.parseInt(dateSplit[0]);
			day = Integer.parseInt(dateSplit[1]);
		} catch (Exception e ) {
			return false;
		}

		if (month > 12 || month < 1 || day > 31 || day < 1) {
			return false;
		}

		if (day.equals(31) && (month.equals(4) || month.equals(6) || month.equals(9) ||
								 month.equals(11))) {
			return false; // only 1,3,5,7,8,10,12 has 31 days
		} else if (month.equals(2) || month.equals(02)) {
			if (day.equals(30) || day.equals(31)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

}
