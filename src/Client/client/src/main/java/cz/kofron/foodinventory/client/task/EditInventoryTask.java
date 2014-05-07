package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.EditInventoryParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 17.3.14.
 */
public class EditInventoryTask extends AsyncTask<Object, Void, Void>
{
	
	/** The param. */
	private EditInventoryParam param;
	
	/** The result. */
	private boolean result;
	
	/** The context. */
	private Context context;
	
	/** The pd. */
	private ProgressDialog pd;

	/**
	 * Instantiates a new edits the inventory task.
	 *
	 * @param context the context
	 * @param param the param
	 */
	public EditInventoryTask(Context context, EditInventoryParam param)
	{
		this.context = context;
		this.param = param;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute()
	{
		pd = new ProgressDialog(context);
		if(param.adding)
		{
			pd.setTitle("Adding...");
		}
		else
		{
			pd.setTitle("Saving...");
		}
		pd.setMessage("Please wait.");
		pd.setCancelable(false);
		pd.setIndeterminate(true);
		pd.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... object)
	{
		try
		{
			result = NetworkInstance.communicator.editFoodItem(param.adding, param.id, param.foodId, param.useBy, param.count);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
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

		pd.dismiss();

		if(param.adding)
		{
			Toast.makeText(context, "Added.", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(context, "Saved.", Toast.LENGTH_SHORT).show();
		}

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
