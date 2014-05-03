package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class LoadInventoryParam
{
	
	/** The food name. */
	public String foodName;
	
	/** The food gtin. */
	public String foodGtin;
	
	/** The adapter. */
	public WeakReference<InventoryListAdapter> adapter;
	
	/** The success callback. */
	public Runnable successCallback;
	
	/** The fail callback. */
	public Runnable failCallback;

	/**
	 * Instantiates a new load inventory param.
	 *
	 * @param foodName the food name
	 * @param foodGtin the food gtin
	 * @param adapter the adapter
	 * @param successCallback the success callback
	 * @param failCallback the fail callback
	 */
	public LoadInventoryParam(String foodName, String foodGtin, InventoryListAdapter adapter, Runnable successCallback, Runnable failCallback)
	{
		this.foodName = foodName;
		this.foodGtin = foodGtin;
		this.adapter = new WeakReference<>(adapter);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
