package cz.kofron.foodinventory.client.task.param;

import android.os.AsyncTask;

import java.io.IOException;

import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.NetworkInstance;

/**
 * Created by kofee on 24.3.14.
 */
public class SetUserReviewTask extends AsyncTask<Object, Void, Void>
{
	private SetUserReviewParam param;
	private ReloadCallback reloadCallback;
	private boolean result = false;

	public SetUserReviewTask(SetUserReviewParam param, ReloadCallback reloadCallback)
	{
		this.param = param;
		this.reloadCallback = reloadCallback;
	}

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
	}
}
