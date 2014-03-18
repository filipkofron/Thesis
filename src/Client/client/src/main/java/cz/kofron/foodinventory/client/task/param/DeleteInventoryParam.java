package cz.kofron.foodinventory.client.task.param;

/**
 * Created by kofee on 18.3.14.
 */
public class DeleteInventoryParam
{
	public Runnable successCallback;
	public Runnable failCallback;

	public int id;

	public DeleteInventoryParam(Runnable successCallback, Runnable failCallback, int id)
	{
		this.successCallback = successCallback;
		this.failCallback = failCallback;
		this.id = id;
	}
}
