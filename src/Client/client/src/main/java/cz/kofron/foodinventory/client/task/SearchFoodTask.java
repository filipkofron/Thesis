package cz.kofron.foodinventory.client.task;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import cz.kofron.foodinventory.client.adapter.FoodListAdapter;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.param.SearchFoodParam;
import cz.kofron.foodinventory.client.util.SearchType;

/**
 * Created by kofee on 16.3.14.
 */
public class SearchFoodTask extends AsyncTask<SearchFoodParam, Void, Void>
{
	private SearchFoodParam param;
	private ArrayList<FoodItem> searchResults = new ArrayList<>();

	public ArrayList<FoodItem> search(String constr)
	{
		constr = constr.toLowerCase();

		ArrayList<FoodItem> resultArray = new ArrayList<>();

		try
		{
			resultArray.addAll(NetworkInstance.communicator.getFoodItem(false, 0, constr, "", 0).getFoods());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ArrayList<>();
		}

		if(SearchType.isGtin(constr))
		{
			try
			{
				resultArray.addAll(NetworkInstance.communicator.getFoodItem(false, 0, "", constr, 0).getFoods());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return new ArrayList<>();
			}
		}

		return resultArray;
	}

	@Override
	protected Void doInBackground(SearchFoodParam... searchFoodParams)
	{
		if (searchFoodParams.length > 0)
		{
			this.param = searchFoodParams[0];
		}
		else
		{
			return null;
		}

		searchResults = search(param.searchString);
		TreeSet<FoodItem> set = new TreeSet<>(searchResults);
		searchResults.clear();
		searchResults.addAll(set);

		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid)
	{
		super.onPostExecute(aVoid);

		if (param == null)
		{
			return;
		}

		if (searchResults != null)
		{
			FoodListAdapter adapter = param.adapter.get();

			if (adapter != null)
			{
				adapter.updateContent(searchResults);
			}
			param.successCallback.run();
		}
		else
		{
			param.failCallback.run();
		}
	}
}
