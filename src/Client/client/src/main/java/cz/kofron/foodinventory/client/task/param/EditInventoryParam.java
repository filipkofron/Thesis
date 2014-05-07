package cz.kofron.foodinventory.client.task.param;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 17.3.14.
 */
public class EditInventoryParam
{
	
	/** The success callback. */
	public Runnable successCallback;
	
	/** The fail callback. */
	public Runnable failCallback;

	/** The adding. */
	public boolean adding;
	
	/** The id. */
	public int id;
	
	/** The food id. */
	public int foodId;
	
	/** The use by. */
	public long useBy;
	
	/** The count. */
	public int count;

	/**
	 * Instantiates a new edits the inventory param.
	 *
	 * @param successCallback the success callback
	 * @param failCallback the fail callback
	 * @param adding the adding
	 * @param id the id
	 * @param foodId the food id
	 * @param useBy the use by
	 * @param count the count
	 */
	public EditInventoryParam(Runnable successCallback, Runnable failCallback, boolean adding, int id, int foodId, long useBy, int count)
	{
		this.successCallback = successCallback;
		this.failCallback = failCallback;
		this.adding = adding;
		this.id = id;
		this.foodId = foodId;
		this.useBy = useBy;
		this.count = count;
	}
}
