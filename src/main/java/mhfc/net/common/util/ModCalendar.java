package mhfc.net.common.util;

import java.util.Calendar;

public class ModCalendar {

	public static boolean halloweenSeason() {
		Calendar calendar = Calendar.getInstance();
			if(		(calendar.get(Calendar.DAY_OF_MONTH) == 31 && calendar.get(Calendar.MONTH) == Calendar.OCTOBER)
					||	(calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.MONTH) == Calendar.NOVEMBER)
		)	
			return true;
		return false;
    }

	public static boolean winterSeason() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.DAY_OF_MONTH) >= 1 && calendar.get(Calendar.DAY_OF_MONTH) <= 25
				&& calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
			return true;
		}
		return false;
	}

	public static boolean summerSeason() {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.MARCH) {
			return true;
		}
		return false;
	}
}
