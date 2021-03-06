package cz.kofron.foodinventory.client.task;

import android.os.AsyncTask;

import java.io.IOException;

import cz.kofron.foodinventory.client.activity.FoodDetailActivity;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.protocol.message.GetFoodDetailRequest;
import cz.kofron.foodinventory.client.protocol.message.GetFoodDetailResponse;
import cz.kofron.foodinventory.client.task.param.LoadFoodDetailParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 16.3.14.
 */
public class LoadFoodDetailTask extends AsyncTask<LoadFoodDetailParam, Void, Void>
{
	
	/** The param. */
	private LoadFoodDetailParam param;
	
	/** The gfdr. */
	private GetFoodDetailResponse gfdr;

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(LoadFoodDetailParam... loadFoodDetailParams)
	{
		if (loadFoodDetailParams.length > 0)
		{
			this.param = loadFoodDetailParams[0];
		}
		else
		{
			return null;
		}

		try
		{
			gfdr = NetworkInstance.communicator.getFoodDetail(param.foodId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gfdr = null;
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

		if (param == null)
		{
			return;
		}

		FoodDetailActivity fda = param.activity.get();

		if (fda != null && gfdr != null)
		{
			if(gfdr.getFoodDetails().size() > 0)
			{
				fda.setFoodDetail(gfdr.getFoodDetails().get(0));
			}

			param.successCallback.run();
		}
		else
		{
			param.failCallback.run();
		}
	}
}
