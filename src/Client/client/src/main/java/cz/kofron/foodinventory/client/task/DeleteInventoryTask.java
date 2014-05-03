package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.DeleteInventoryParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 18.3.14.
 */
public class DeleteInventoryTask extends AsyncTask<Object, Void, Void>
{
	
	/** The param. */
	private DeleteInventoryParam param;
	
	/** The context. */
	private Context context;
	
	/** The pd. */
	private ProgressDialog pd;
	
	/** The result. */
	private boolean result;

	/**
	 * Instantiates a new delete inventory task.
	 *
	 * @param context the context
	 * @param param the param
	 */
	public DeleteInventoryTask(Context context, DeleteInventoryParam param)
	{
		this.param = param;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... objects)
	{
		try
		{
			result = NetworkInstance.communicator.deleteInventory(param.id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		pd = new ProgressDialog(context);
		pd.setTitle("Removing...");
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		pd.dismiss();

		Toast.makeText(context, "Deleted.", Toast.LENGTH_SHORT).show();

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
