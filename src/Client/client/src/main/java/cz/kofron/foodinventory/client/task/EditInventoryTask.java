package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.EditInventoryParam;

/**
 * Created by kofee on 17.3.14.
 */
public class EditInventoryTask extends AsyncTask<Object, Void, Void>
{
	private EditInventoryParam param;
	private boolean result;
	private Context context;
	private ProgressDialog pd;

	public EditInventoryTask(Context context, EditInventoryParam param)
	{
		this.context = context;
		this.param = param;
	}

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

	@Override
	protected Void doInBackground(Object... object)
	{
		try
		{
			result = NetworkInstance.communicator.editFoodItem(param.adding, param.id, param.foodId, param.useBy, param.count);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			result = false;
		}

		return null;
	}

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
