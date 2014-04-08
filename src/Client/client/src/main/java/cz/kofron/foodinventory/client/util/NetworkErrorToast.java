package cz.kofron.foodinventory.client.util;

import android.content.Context;
import android.widget.Toast;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 13.3.14.
 */
public class NetworkErrorToast
{
	private static long suppressUntil = 0;

	public static void suppressFor(long milis)
	{
		suppressUntil = System.currentTimeMillis() + milis;
	}

	public static void showError(Context context)
	{
		if(System.currentTimeMillis() >= suppressUntil)
		{
			Toast.makeText(context, R.string.network_error_toast, Toast.LENGTH_LONG).show();
		}
	}
}
