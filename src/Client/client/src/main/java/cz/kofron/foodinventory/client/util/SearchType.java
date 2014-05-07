package cz.kofron.foodinventory.client.util;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 16.3.14.
 */
public class SearchType
{
	
	/**
	 * Checks if is gtin.
	 *
	 * @param str the str
	 * @return true, if is gtin
	 */
	public static boolean isGtin(final String str)
	{
		try
		{
			Integer.parseInt(str);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
}
