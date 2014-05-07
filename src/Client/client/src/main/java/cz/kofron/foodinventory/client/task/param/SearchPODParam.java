package cz.kofron.foodinventory.client.task.param;

import cz.kofron.foodinventory.client.task.SearchPODTask;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 26.3.14.
 */
public class SearchPODParam
{
	
	/** The gtin. */
	public String gtin;
	
	/** The pod result listener. */
	public SearchPODTask.PODResultListener podResultListener;

	/**
	 * Instantiates a new search pod param.
	 *
	 * @param gtin the gtin
	 * @param podResultListener the pod result listener
	 */
	public SearchPODParam(String gtin, SearchPODTask.PODResultListener podResultListener)
	{
		this.gtin = gtin;
		this.podResultListener = podResultListener;
	}
}
