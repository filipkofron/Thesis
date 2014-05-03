package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.FoodListAdapter;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 16.3.14.
 */
public class SearchFoodParam
{
	
	/** The search string. */
	public String searchString;
	
	/** The adapter. */
	public WeakReference<FoodListAdapter> adapter;
	
	/** The success callback. */
	public Runnable successCallback;
	
	/** The fail callback. */
	public Runnable failCallback;

	/**
	 * Instantiates a new search food param.
	 *
	 * @param searchString the search string
	 * @param adapter the adapter
	 * @param successCallback the success callback
	 * @param failCallback the fail callback
	 */
	public SearchFoodParam(String searchString, FoodListAdapter adapter, Runnable successCallback, Runnable failCallback)
	{
		this.searchString = searchString;
		this.adapter = new WeakReference<>(adapter);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
