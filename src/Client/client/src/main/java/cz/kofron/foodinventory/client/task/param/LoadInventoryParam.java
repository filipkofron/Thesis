package cz.kofron.foodinventory.client.task.param;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;

/**
 * Created by kofee on 13.3.14.
 */
public class LoadInventoryParam
{
	public String foodName;
	public String foodGtin;
	public WeakReference<InventoryListAdapter> adapter;
	public Runnable successCallback;
	public Runnable failCallback;

	public LoadInventoryParam(String foodName, String foodGtin, InventoryListAdapter adapter, Runnable successCallback, Runnable failCallback)
	{
		this.foodName = foodName;
		this.foodGtin = foodGtin;
		this.adapter = new WeakReference<>(adapter);
		this.successCallback = successCallback;
		this.failCallback = failCallback;
	}
}
