package cz.kofron.foodinventory.client.task.param;

import android.os.AsyncTask;

import java.io.IOException;

import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 24.3.14.
 */
public class SetUserReviewTask extends AsyncTask<Object, Void, Void>
{
	
	/** The param. */
	private SetUserReviewParam param;
	
	/** The reload callback. */
	private ReloadCallback reloadCallback;
	
	/** The result. */
	private boolean result = false;

	/**
	 * Instantiates a new sets the user review task.
	 *
	 * @param param the param
	 * @param reloadCallback the reload callback
	 */
	public SetUserReviewTask(SetUserReviewParam param, ReloadCallback reloadCallback)
	{
		this.param = param;
		this.reloadCallback = reloadCallback;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... objects)
	{
		try
		{
			result = NetworkInstance.communicator.setUserReview(param.rating, param.delete, param.foodId, param.text);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		if(result)
		{
			if (reloadCallback != null)
			{
				reloadCallback.update();
			}
		}
		else
		{
			NetworkInstance.connector.forceCheck();
		}
	}
}
