package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.FoodListAdapter;

/**
 * Created by kofee on 16.3.14.
 */
public class SearchFoodParam
{
	public String searchString;
	public WeakReference<FoodListAdapter> adapter;
	public Runnable successCallback;
	public Runnable failCallback;

	public SearchFoodParam(String searchString, FoodListAdapter adapter, Runnable successCallback, Runnable failCallback)
	{
		this.searchString = searchString;
		this.adapter = new WeakReference<>(adapter);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
