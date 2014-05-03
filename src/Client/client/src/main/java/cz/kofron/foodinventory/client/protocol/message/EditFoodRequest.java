package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class EditFoodRequest extends Message
{
	
	/** The adding. */
	private boolean adding;
	
	/** The id. */
	private int id;
	
	/** The name. */
	private String name;
	
	/** The vendor. */
	private String vendor;
	
	/** The category id. */
	private int categoryId;
	
	/** The gtin. */
	private String gtin;
	
	/** The description. */
	private String description;
	
	/** The default use by. */
	private long defaultUseBy;
	
	/** The amount type. */
	private int amountType;
	
	/** The amount. */
	private float amount;
	
	/** The usual price. */
	private float usualPrice;

	/**
	 * Instantiates a new edits the food request.
	 */
	public EditFoodRequest()
	{
	}

	/**
	 * Instantiates a new edits the food request.
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
	 */
	public EditFoodRequest(boolean adding, int id, String name, String vendor, int categoryId, String gtin, String description, long defaultUseBy, int amountType, float amount, float usualPrice)
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
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "EditFoodRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new EditFoodRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		adding = obj.getBoolean("adding");
		id = obj.getInt("id");
		name = obj.getString("name");
		vendor = obj.getString("vendor");
		categoryId = obj.getInt("categoryId");
		gtin = obj.getString("gtin");
		description = obj.getString("description");
		defaultUseBy = obj.getLong("defaultUseBy");
		amountType = obj.getInt("amountType");
		amount = (float) obj.getDouble("amount");
		usualPrice = (float) obj.getDouble("usualPrice");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("adding", adding);
		obj.put("id", id);
		obj.put("name", name);
		obj.put("vendor", vendor);
		obj.put("categoryId", categoryId);
		obj.put("gtin", gtin);
		obj.put("description", description);
		obj.put("defaultUseBy", defaultUseBy);
		obj.put("amountType", amountType);
		obj.put("amount", amount);
		obj.put("usualPrice", usualPrice);

		return obj;
	}
}
