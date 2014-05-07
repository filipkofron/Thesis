package cz.kofron.foodinventory.client.task;

import android.os.AsyncTask;

import java.io.IOException;

import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryResponse;
import cz.kofron.foodinventory.client.task.param.LoadInventoryParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class LoadInventoryTask extends AsyncTask<LoadInventoryParam, Void, Void>
{
	
	/** The param. */
	private LoadInventoryParam param;
	
	/** The gir. */
	private GetInventoryResponse gir;

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(LoadInventoryParam... loadInventoryTasks)
	{
		if (loadInventoryTasks.length > 0)
		{
			this.param = loadInventoryTasks[0];
		}
		else
		{
			return null;
		}

		try
		{
			gir = NetworkInstance.communicator.getInventory(false, 0, param.foodName, param.foodGtin);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			gir = null;
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

		if (gir != null)
		{
			InventoryListAdapter adapter = param.adapter.get();

			if (adapter != null)
			{
				adapter.updateContent(gir.getIntentoryItems());
			}
			param.successCallback.run();
		}
		else
		{
			param.failCallback.run();
		}
	}
}
