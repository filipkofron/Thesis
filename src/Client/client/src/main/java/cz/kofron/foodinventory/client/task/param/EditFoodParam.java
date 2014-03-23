package cz.kofron.foodinventory.client.task.param;

/**
 * Created by kofee on 23.3.14.
 */
public class EditFoodParam
{
	public boolean adding;
	public int id;
	public String name;
	public String vendor;
	public int categoryId;
	public String gtin;
	public String description;
	public long defaultUseBy;
	public int amountType;
	public float amount;
	public float usualPrice;
	public Runnable success;
	public Runnable fail;

	public EditFoodParam(boolean adding, int id, String name, String vendor, int categoryId, String gtin, String description, long defaultUseBy, int amountType, float amount, float usualPrice, Runnable success, Runnable fail)
	{
		this.adding = adding;
		this.id = id;
		this.name = name;
		this.vendor = vendor;
		this.categoryId = categoryId;
		this.gtin = gtin;
		this.description = description;
		this.defaultUseBy = defaultUseBy;
		this.amountType = amountType;
		this.amount = amount;
		this.usualPrice = usualPrice;
		this.success = success;
		this.fail = fail;
	}
}
