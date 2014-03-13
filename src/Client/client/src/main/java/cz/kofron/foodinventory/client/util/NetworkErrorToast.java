package cz.kofron.foodinventory.client.util;

import android.content.Context;
import android.widget.Toast;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 13.3.14.
 */
public class NetworkErrorToast
{
	public static void showError(Context context)
	{
		Toast.makeText(context, R.string.network_error_toast, Toast.LENGTH_LONG);
	}
}
