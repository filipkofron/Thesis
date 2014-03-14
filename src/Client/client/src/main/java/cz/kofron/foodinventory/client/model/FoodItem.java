package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class FoodItem extends AbstractEntity
{

	private long defaultUseBy;
	private String imageId;
	private String name;
	private String desc;
	private String gtin;

	public FoodItem(int id)
	{
		super(id, true);
	}

	public FoodItem(int id, boolean exists, long defaultUseBy, String imageId, String name, String desc, String gtin)
	{
		super(id, exists);
		this.defaultUseBy = defaultUseBy;
		this.imageId = imageId;
		this.name = name;
		this.desc = desc;
		this.gtin = gtin;
	}

	public static FoodItem fromJson(JSONObject obj) throws JSONException
	{
		FoodItem foodItem = new FoodItem(obj.getInt("id"));
		foodItem.defaultUseBy = obj.getLong("defaultUseBy");
		foodItem.imageId = obj.getString("imageId");
		foodItem.name = obj.getString("name");
		foodItem.desc = obj.getString("desc");
		foodItem.gtin = obj.getString("gtin");

		return foodItem;
	}

	public long getDefaultUseBy()
	{
		return defaultUseBy;
	}

	public void setDefaultUseBy(long defaultUseBy)
	{
		this.defaultUseBy = defaultUseBy;
	}

	public String getImageId()
	{
		return imageId;
	}

	public void setImageId(String imageId)
	{
		this.imageId = imageId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getGtin()
	{
		return gtin;
	}

	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("defaultUseBy", defaultUseBy);
		obj.put("imageId", imageId);
		obj.put("name", name);
		obj.put("desc", desc);
		obj.put("gtin", gtin);

		return obj;
	}
}
