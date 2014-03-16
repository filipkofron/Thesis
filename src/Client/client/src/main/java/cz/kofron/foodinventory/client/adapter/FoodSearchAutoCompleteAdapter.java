package cz.kofron.foodinventory.client.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.util.SearchType;

/**
 * Created by kofee on 3/7/14.
 */
public class FoodSearchAutoCompleteAdapter extends ArrayAdapter implements Filterable
{
	private long lastCompletion = System.currentTimeMillis();
	private long MIN_COMPLETION_PERIOD = 300; // milis

	private ArrayList<String> resultList = new ArrayList<>();

	public FoodSearchAutoCompleteAdapter(Context context, int resource)
	{
		super(context, resource);
	}

	public ArrayList<String> autocomplete(String constr)
	{
		constr = constr.toLowerCase();

		ArrayList<String> resultArray = new ArrayList<>();

		Set<String> results = new TreeSet<>();

		try
		{
			ArrayList<FoodItem> foods = NetworkInstance.communicator.getFoodItem(false, 0, constr, "", 0).getFoods();
			for(FoodItem food : foods)
			{
				results.add(food.getName());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(SearchType.isGtin(constr))
		{
			try
			{
				ArrayList<FoodItem> foods = NetworkInstance.communicator.getFoodItem(false, 0, "", constr, 0).getFoods();
				for(FoodItem food : foods)
				{
					results.add(food.getName());
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		resultArray.addAll(results);

		return resultArray;
	}

	@Override
	public int getCount()
	{
		return resultList.size();
	}

	@Override
	public String getItem(int index)
	{
		return resultList.get(index);
	}

	@Override
	public Filter getFilter()
	{
		Filter filter = new Filter()
		{
			@Override
			protected FilterResults performFiltering(CharSequence constraint)
			{
				FilterResults filterResults = new FilterResults();
				if (constraint != null)
				{
					String constr = constraint.toString();
					if(constr.length() > 2 && (System.currentTimeMillis() - lastCompletion) > MIN_COMPLETION_PERIOD)
					{
						// Retrieve the autocomplete results.
						resultList = autocomplete(constr);
						lastCompletion = System.currentTimeMillis();
					}

					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results)
			{
				if (results != null && results.count > 0)
				{
					notifyDataSetChanged();
				}
				else
				{
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}
