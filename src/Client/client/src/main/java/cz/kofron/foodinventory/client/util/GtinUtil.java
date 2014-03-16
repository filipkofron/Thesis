package cz.kofron.foodinventory.client.util;

/**
 * Created by kofee on 16.3.14.
 */
public class GtinUtil
{
	public final static String prefixes[] =
	{
		"Z", // 0
		"Z", // 1
		"Z", // 2
		"Z", // 3
		"Z", // 4
		"Z", // 5
		"Z", // 6
		"Z", // 7
		"A", // 8
		"Z", // 9
		"Z", // 10
		"Z", // 11
		"B", // 12
		"C", // 13
		"D", // 14
	};

	public static String getReadableGtin(String src)
	{
		src = src.toUpperCase();
		if(src.length() < 1)
		{
			return "";
		}
		char c = src.charAt(0);
		if(src.length() < 15)
		{
			return src;
		}
		if(c == 'A')
		{
			return src.substring(15 - 8, src.length());
		}
		if(c == 'B')
		{
			return src.substring(15 - 12, src.length());
		}
		if(c == 'C')
		{
			return src.substring(15 - 13, src.length());
		}
		if(c == 'D')
		{
			return src.substring(15 - 14, src.length());
		}

		// 'Z'
		return src.substring(1);
	}
}
