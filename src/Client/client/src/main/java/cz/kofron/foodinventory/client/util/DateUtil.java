package cz.kofron.foodinventory.client.util;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kofee on 17.3.14.
 */
public class DateUtil
{
	public static Date getDateFromDatePicket(DatePicker datePicker){
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year =  datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime();
	}

	public static long getTimeFromValues(int years, int months, int days)
	{
		int day = days + 1;
		int month = months;
		int year = days + 1970;

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime().getTime();
	}
}
