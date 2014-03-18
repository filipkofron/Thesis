package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.DeleteInventoryParam;

/**
 * Created by kofee on 18.3.14.
 */
public class DeleteInventoryTask extends AsyncTask<Object, Void, Void>
{
	private DeleteInventoryParam param;
	private Context context;
	private ProgressDialog pd;
	private boolean result;

	public DeleteInventoryTask(Context context, DeleteInventoryParam param)
	{
		this.param = param;
		this.context = context;
	}

	@Override
	protected Void doInBackground(Object... objects)
	{
		try
		{
			result = NetworkInstance.communicator.deleteInventory(param.id);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result = false;
		}

		return null;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		pd = new ProgressDialog(context);
		pd.setTitle("Deleting...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		pd.dismiss();

		Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT);

		if (param == null)
		{
			return;
		}

		if (result)
		{
			param.successCallback.run();
		}
		else
		{
			param.failCallback.run();
		}
	}
}
