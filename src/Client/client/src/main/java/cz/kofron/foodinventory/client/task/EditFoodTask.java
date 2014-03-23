package cz.kofron.foodinventory.client.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.EditFoodParam;

/**
 * Created by kofee on 23.3.14.
 */
public class EditFoodTask extends AsyncTask<Object, Void, Void>
{
	private ProgressDialog pd;
	private EditFoodParam param;
	private Activity activity;
	private boolean result = false;

	public EditFoodTask(EditFoodParam param, Activity activity)
	{
		this.param = param;
		this.activity = activity;
	}

	@Override
	protected void onPreExecute()
	{
		pd = new ProgressDialog(activity);
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
			result = NetworkInstance.communicator.editFood(param.adding, param.id, param.name, param.vendor, param.categoryId, param.gtin, param.description, param.defaultUseBy, param.amountType, param.amount, param.usualPrice);
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
			Toast.makeText(activity, "Added.", Toast.LENGTH_SHORT);
		}
		else
		{
			Toast.makeText(activity, "Saved.", Toast.LENGTH_SHORT);
		}

		if (param == null)
		{
			return;
		}

		if (result)
		{
			param.success.run();
		}
		else
		{
			param.fail.run();
		}
		activity.finish();
	}
}
