package cz.kofron.foodinventory.client.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.Communicator;
import cz.kofron.foodinventory.client.network.NetworkInstance;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 25.3.14.
 */
public class ConnectionDialogManager
{
	
	/** The activity. */
	private static Activity activity;
	
	/** The pd. */
	private static ProgressDialog pd;

	/**
	 * Initialize.
	 *
	 * @param activity the activity
	 */
	public static void initialize(Activity activity)
	{
		ConnectionDialogManager.activity = activity;
		if(disableCheckerThread == null)
		{
			disableCheckerThread = new Thread(disableChecker);
			disableCheckerThread.start();
		}
	}

	/** The disable checker thread. */
	private static Thread disableCheckerThread;

	/** The disable checker. */
	private static Runnable disableChecker = new Runnable()
	{
		private final static int MS_WAIT = 300;

		@Override
		public void run()
		{
			while(true)
			{
				try
				{
					Thread.sleep(MS_WAIT);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				Communicator comm = NetworkInstance.communicator;
				if(comm != null)
				{
					if(comm.isConnected())
					{
						hideDialog();
					}
				}
			}
		}
	};

	/**
	 * Show dialog.
	 */
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
						pd.setOnKeyListener(new DialogInterface.OnKeyListener()
						{
							@Override
							public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent)
							{
								System.exit(0);
								return true;
							}
						});
						pd.setIndeterminate(true);
						pd.show();
					}
				});
			}
		}
	}

	/**
	 * Reload activity.
	 */
	public static synchronized void reloadActivity()
	{
		if(activity != null && !activity.isFinishing())
		{
			if(activity instanceof ReloadCallback)
			{
				((ReloadCallback) activity).update();
			}
		}
	}

	/**
	 * Hide dialog.
	 *
	 * @return true whether the dialog was shown before we hid it.
	 */
	public static synchronized boolean hideDialog()
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
			return true;
		}
		return false;
	}
}
