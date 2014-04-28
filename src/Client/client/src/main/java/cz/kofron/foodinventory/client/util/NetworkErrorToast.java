package cz.kofron.foodinventory.client.util;

import android.content.Context;
import android.widget.Toast;

import cz.kofron.foodinventory.client.R;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class NetworkErrorToast
{
	
	/** The suppress until. */
	private static long suppressUntil = 0;

	/**
	 * Suppress for.
	 *
	 * @param milis the milis
	 */
	public static void suppressFor(long milis)
	{
		suppressUntil = System.currentTimeMillis() + milis;
	}

	/**
	 * Show error.
	 *
	 * @param context the context
	 */
	public static void showError(Context context)
	{
		if(System.currentTimeMillis() >= suppressUntil)
		{
			Toast.makeText(context, R.string.network_error_toast, Toast.LENGTH_LONG).show();
		}
	}
}
