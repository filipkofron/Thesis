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

/**
 * Created by kofee on 18.3.14.
 */
public class SearchAndAddTask extends AsyncTask<Object, Void, Void> implements SearchPODTask.PODResultListener
{
	private Context context;
	private String gtin;
	private Runnable onDone;
	private Runnable onAddedFood;
	private ProgressDialog pd;
	private FoodItem foodItem;

	public SearchAndAddTask(Context context, String gtin, Runnable onDone, Runnable onAddedFood)
	{
		this.context = context;
		this.gtin = gtin;
		this.onDone = onDone;
		this.onAddedFood = onAddedFood;
	}

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

	@Override
	public void onResults(ArrayList<PODResult> results)
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
}
