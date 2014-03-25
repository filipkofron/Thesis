package cz.kofron.foodinventory.client.dialog;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by kofee on 25.3.14.
 */
public class ConnectionDialogManager
{
	private static Activity activity;
	private static ProgressDialog pd;

	public static void initialize(Activity activity)
	{
		ConnectionDialogManager.activity = activity;
	}

	public static synchronized void showDialog()
	{
		if (pd == null)
		{
			if (activity != null && !activity.isFinishing())
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						pd = new ProgressDialog(activity);
						pd.setTitle("Connection");

						pd.setMessage("Connecting to server..");
						pd.setCancelable(false);
						pd.setIndeterminate(true);
						pd.show();
					}
				});
			}
		}
	}

	public static synchronized void hideDialog()
	{
		final ProgressDialog thisPd = pd;
		if (thisPd != null)
		{
			if (activity != null && !activity.isFinishing())
			{
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						thisPd.dismiss();

					}
				});
				pd = null;
			}
		}
	}
}
