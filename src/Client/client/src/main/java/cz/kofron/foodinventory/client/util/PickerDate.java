package cz.kofron.foodinventory.client.util;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kofee on 17.3.14.
 */
public class PickerDate
{
	public static Date getDateFromDatePicket(DatePicker datePicker){
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year =  datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		return calendar.getTime();
	}
}
