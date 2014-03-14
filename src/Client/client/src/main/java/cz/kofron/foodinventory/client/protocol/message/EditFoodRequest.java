package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class EditFoodRequest extends Message
{
	private boolean adding;
	private int id;
	private String name;
	private int vendorId;
	private String gtin;
	private String description;
	private long defaultUseBy;
	private int amountType;
	private float amount;
	private float usualPrice;

	public EditFoodRequest()
	{
	}

	public EditFoodRequest(boolean adding, int id, String name, int vendorId, String gtin, String description, long defaultUseBy, int amountType, float amount, float usualPrice)
	{
		this.adding = adding;
		this.id = id;
		this.name = name;
		this.vendorId = vendorId;
		this.gtin = gtin;
		this.description = description;
		this.defaultUseBy = defaultUseBy;
		this.amountType = amountType;
		this.amount = amount;
		this.usualPrice = usualPrice;
	}

	@Override
	public String getHeader()
	{
		return "EditFoodRequest";
	}

	@Override
	public Message newMessage()
	{
		return new EditFoodRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		adding = obj.getBoolean("adding");
		id = obj.getInt("id");
		name = obj.getString("name");
		vendorId = obj.getInt("vendorId");
		gtin = obj.getString("gtin");
		description = obj.getString("description");
		defaultUseBy = obj.getLong("defaultUseBy");
		amountType = obj.getInt("amountType");
		amount = (float) obj.getDouble("amount");
		usualPrice = (float) obj.getDouble("usualPrice");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("adding", adding);
		obj.put("id", id);
		obj.put("name", name);
		obj.put("vendorId", vendorId);
		obj.put("gtin", gtin);
		obj.put("description", description);
		obj.put("defaultUseBy", defaultUseBy);
		obj.put("amountType", amountType);
		obj.put("amount", amount);
		obj.put("usualPrice", usualPrice);

		return obj;
	}
}
