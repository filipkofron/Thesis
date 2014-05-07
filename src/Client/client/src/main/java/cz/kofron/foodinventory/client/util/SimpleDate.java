package cz.kofron.foodinventory.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class SimpleDate
{
	
	/** The Constant sdf. */
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * Long to date.
	 *
	 * @param time the time
	 * @return the string
	 */
	public static String longToDate(long time)
	{
		Date date = new Date(time);
		return sdf.format(date);
	}

}
