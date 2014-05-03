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

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/7/14.
 */
public class FoodSearchAutoCompleteAdapter extends ArrayAdapter implements Filterable
{
	
	/** The last completion. */
	private long lastCompletion = System.currentTimeMillis();
	
	/** The min completion period. */
	private long MIN_COMPLETION_PERIOD = 300; // milis

	/** The result list. */
	private ArrayList<String> resultList = new ArrayList<>();

	/**
	 * Instantiates a new food search auto complete adapter.
	 *
	 * @param context the context
	 * @param resource the resource
	 */
	public FoodSearchAutoCompleteAdapter(Context context, int resource)
	{
		super(context, resource);
	}

	/**
	 * Autocomplete.
	 *
	 * @param constr the constr
	 * @return the array list
	 */
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

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount()
	{
		return resultList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getItem(int)
	 */
	@Override
	public String getItem(int index)
	{
		return resultList.get(index);
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getFilter()
	 */
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
