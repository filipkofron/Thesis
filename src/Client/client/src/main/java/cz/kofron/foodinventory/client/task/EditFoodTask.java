package cz.kofron.foodinventory.client.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.protocol.message.EditFoodResponse;
import cz.kofron.foodinventory.client.task.param.EditFoodParam;
import cz.kofron.foodinventory.client.task.param.EditImagesParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class EditFoodTask extends AsyncTask<Object, Void, Void>
{
	
	/** The pd. */
	private ProgressDialog pd;
	
	/** The param. */
	private EditFoodParam param;
	
	/** The activity. */
	private Activity activity;
	
	/** The result. */
	private boolean result = false;
	
	/** The images param. */
	private EditImagesParam imagesParam;

	/**
	 * Instantiates a new edits the food task.
	 *
	 * @param param the param
	 * @param imagesParam the images param
	 * @param activity the activity
	 */
	public EditFoodTask(EditFoodParam param, EditImagesParam imagesParam, Activity activity)
	{
		this.param = param;
		this.activity = activity;
		this.imagesParam = imagesParam;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
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
		pd.setIndeterminate(false);
		pd.setMax(1 + imagesParam.bitmapToUpload.size() + imagesParam.idsToRemove.size());
		pd.setProgress(0);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.show();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... object)
	{
		final int step = 1;
		final ProgressDialog fpd = pd;
		try
		{
			activity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					fpd.setProgress(0);
				}
			});
			EditFoodResponse efr = NetworkInstance.communicator.editFood(param.adding, param.id, param.name, param.vendor, param.categoryId, param.gtin, param.description, param.defaultUseBy, param.amountType, param.amount, param.usualPrice);
			int foodId = efr.getId();
			result = efr.isSuccess();

			activity.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					fpd.setProgress(fpd.getProgress() + step);
				}
			});
			for(String id : imagesParam.idsToRemove)
			{
				NetworkInstance.communicator.deleteImage(Integer.parseInt(id));
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						fpd.setProgress(fpd.getProgress() + step);
					}
				});
			}

			for(Bitmap bitmap : imagesParam.bitmapToUpload)
			{
				NetworkInstance.communicator.addImage(bitmap, foodId);
				activity.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						fpd.setProgress(fpd.getProgress() + step);
					}
				});
			}
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
			Toast.makeText(activity, "Added.", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(activity, "Saved.", Toast.LENGTH_SHORT).show();
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
