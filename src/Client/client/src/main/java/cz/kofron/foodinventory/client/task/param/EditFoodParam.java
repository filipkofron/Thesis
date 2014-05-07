package cz.kofron.foodinventory.client.task.param;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class EditFoodParam
{
	
	/** The adding. */
	public boolean adding;
	
	/** The id. */
	public int id;
	
	/** The name. */
	public String name;
	
	/** The vendor. */
	public String vendor;
	
	/** The category id. */
	public int categoryId;
	
	/** The gtin. */
	public String gtin;
	
	/** The description. */
	public String description;
	
	/** The default use by. */
	public long defaultUseBy;
	
	/** The amount type. */
	public int amountType;
	
	/** The amount. */
	public float amount;
	
	/** The usual price. */
	public float usualPrice;
	
	/** The success. */
	public Runnable success;
	
	/** The fail. */
	public Runnable fail;

	/**
	 * Instantiates a new edits the food param.
	 *
	 * @param adding the adding
	 * @param id the id
	 * @param name the name
	 * @param vendor the vendor
	 * @param categoryId the category id
	 * @param gtin the gtin
	 * @param description the description
	 * @param defaultUseBy the default use by
	 * @param amountType the amount type
	 * @param amount the amount
	 * @param usualPrice the usual price
	 * @param success the success
	 * @param fail the fail
	 */
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
