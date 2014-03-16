package cz.kofron.foodinventory.client.util;

/**
 * Created by kofee on 16.3.14.
 */
public class SearchType
{
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
