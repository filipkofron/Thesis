package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.activity.FoodDetailActivity;

/**
 * Created by kofee on 16.3.14.
 */
public class LoadFoodDetailParam
{
	public int foodId;
	public WeakReference<FoodDetailActivity> activity;
	public Runnable successCallback;
	public Runnable failCallback;

	public LoadFoodDetailParam(int foodId, FoodDetailActivity activity, Runnable successCallback, Runnable failCallback)
	{
		this.foodId = foodId;
		this.activity = new WeakReference<>(activity);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
