package cz.kofron.foodinventory.client.task.param;

import cz.kofron.foodinventory.client.task.SearchPODTask;

/**
 * Created by kofee on 26.3.14.
 */
public class SearchPODParam
{
	public String gtin;
	public SearchPODTask.PODResultListener podResultListener;

	public SearchPODParam(String gtin, SearchPODTask.PODResultListener podResultListener)
	{
		this.gtin = gtin;
		this.podResultListener = podResultListener;
	}
}
