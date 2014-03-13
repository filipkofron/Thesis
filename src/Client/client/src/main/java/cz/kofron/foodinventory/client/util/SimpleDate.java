package cz.kofron.foodinventory.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kofee on 13.3.14.
 */
public class SimpleDate
{
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public static String longToDate(long time)
	{
		Date date = new Date(time);
		return sdf.format(date);
	}
}
