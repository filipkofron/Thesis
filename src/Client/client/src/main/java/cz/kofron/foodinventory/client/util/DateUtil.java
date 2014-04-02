package cz.kofron.foodinventory.client.util;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kofee on 17.3.14.
 */
public class DateUtil
{
	public final static long ROUGH_MS_PER_DAY = 1000 * 60 * 60 * 24;
	public final static long ROUGH_MS_PER_MONTH = ROUGH_MS_PER_DAY * 30;
	public final static long ROUGH_MS_PER_YEAR = ROUGH_MS_PER_MONTH * 12;

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
		int year = years + 1970;

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime().getTime();
	}
}
