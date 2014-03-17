package cz.kofron.foodinventory.client.task.param;

/**
 * Created by kofee on 17.3.14.
 */
public class EditInventoryParam
{
	public Runnable successCallback;
	public Runnable failCallback;

	public boolean adding;
	public int id;
	public int foodId;
	public long useBy;
	public int count;

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
