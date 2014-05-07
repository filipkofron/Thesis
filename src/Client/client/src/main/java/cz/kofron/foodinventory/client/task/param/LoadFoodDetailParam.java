package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.activity.FoodDetailActivity;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 16.3.14.
 */
public class LoadFoodDetailParam
{
	
	/** The food id. */
	public int foodId;
	
	/** The activity. */
	public WeakReference<FoodDetailActivity> activity;
	
	/** The success callback. */
	public Runnable successCallback;
	
	/** The fail callback. */
	public Runnable failCallback;

	/**
	 * Instantiates a new load food detail param.
	 *
	 * @param foodId the food id
	 * @param activity the activity
	 * @param successCallback the success callback
	 * @param failCallback the fail callback
	 */
	public LoadFoodDetailParam(int foodId, FoodDetailActivity activity, Runnable successCallback, Runnable failCallback)
	{
		this.foodId = foodId;
		this.activity = new WeakReference<>(activity);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
