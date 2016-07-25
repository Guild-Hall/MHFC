package mhfc.net.common.util;

import java.util.Calendar;

public class ModCalendar {

	public static boolean isHalloween() {
		Calendar calendar = Calendar.getInstance();
			if(		(calendar.get(Calendar.DAY_OF_MONTH) == 31 && calendar.get(Calendar.MONTH) == Calendar.OCTOBER)
					||	(calendar.get(Calendar.DAY_OF_MONTH) == 1 && calendar.get(Calendar.MONTH) == Calendar.NOVEMBER)
		)	
			return true;
		return false;
    }
}
