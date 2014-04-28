package cz.kofron.foodinventory.client.task.param;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 18.3.14.
 */
public class DeleteInventoryParam
{
	
	/** The success callback. */
	public Runnable successCallback;
	
	/** The fail callback. */
	public Runnable failCallback;

	/** The id. */
	public int id;

	/**
	 * Instantiates a new delete inventory param.
	 *
	 * @param successCallback the success callback
	 * @param failCallback the fail callback
	 * @param id the id
	 */
	public DeleteInventoryParam(Runnable successCallback, Runnable failCallback, int id)
	{
		this.successCallback = successCallback;
		this.failCallback = failCallback;
		this.id = id;
	}
}
