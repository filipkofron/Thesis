package cz.kofron.foodinventory.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.AddImportedFoodDialogFragment;
import cz.kofron.foodinventory.client.fragment.AddNewFoodDialogFragment;
import cz.kofron.foodinventory.client.fragment.InventoryAddFoodDialogFragment;
import cz.kofron.foodinventory.client.fragment.InventoryFoodDialogFragment;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.model.PODResult;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.SearchPODParam;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 18.3.14.
 */
public class SearchAndAddTask extends AsyncTask<Object, Void, Void> implements SearchPODTask.PODResultListener
{
	
	/** The context. */
	private Context context;
	
	/** The gtin. */
	private String gtin;
	
	/** The on done. */
	private Runnable onDone;
	
	/** The on added food. */
	private Runnable onAddedFood;
	
	/** The pd. */
	private ProgressDialog pd;
	
	/** The food item. */
	private FoodItem foodItem;

	/**
	 * Instantiates a new search and add task.
	 *
	 * @param context the context
	 * @param gtin the gtin
	 * @param onDone the on done
	 * @param onAddedFood the on added food
	 */
	public SearchAndAddTask(Context context, String gtin, Runnable onDone, Runnable onAddedFood)
	{
		this.context = context;
		this.gtin = gtin;
		this.onDone = onDone;
		this.onAddedFood = onAddedFood;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... objects)
	{
		try
		{
			ArrayList<FoodItem> foods = NetworkInstance.communicator.getFoodItem(false, 0, "", gtin, 0).getFoods();
			if(foods.size() > 0)
			{
				foodItem = foods.get(0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			foodItem = null;
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
		pd.setTitle("Searching for " + gtin + " ...");
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

		if(foodItem != null)
		{
			new InventoryFoodDialogFragment(R.string.title_inventory_add_food, foodItem, 0, false, null, onDone).show((FragmentActivity) context);
		}
		else
		{
			SearchPODParam searchPODParam = new SearchPODParam(gtin, this);

			SearchPODTask searchPODTask = new SearchPODTask(searchPODParam, context);
			searchPODTask.execute();
		}
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.task.SearchPODTask.PODResultListener#onResults(java.util.ArrayList)
	 */
	@Override
	public void onResults(ArrayList<PODResult> results)
	{
		try
		{
			if(results == null || results.size() < 1)
			{
				new AddNewFoodDialogFragment(onDone, onAddedFood, gtin).show((FragmentActivity) context);
			}
			else
			{
				new AddImportedFoodDialogFragment(onDone, onAddedFood, results.get(0)).show((FragmentActivity) context);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
