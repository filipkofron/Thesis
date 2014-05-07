package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class FoodItem extends AbstractEntity
{

	/** The default use by. */
	private long defaultUseBy;
	
	/** The image id. */
	private String imageId;
	
	/** The name. */
	private String name;
	
	/** The desc. */
	private String desc;
	
	/** The gtin. */
	private String gtin;

	/**
	 * Instantiates a new food item.
	 *
	 * @param id the id
	 */
	public FoodItem(int id)
	{
		super(id, true);
	}

	/**
	 * Instantiates a new food item.
	 *
	 * @param id the id
	 * @param exists the exists
	 * @param defaultUseBy the default use by
	 * @param imageId the image id
	 * @param name the name
	 * @param desc the desc
	 * @param gtin the gtin
	 */
	public FoodItem(int id, boolean exists, long defaultUseBy, String imageId, String name, String desc, String gtin)
	{
		super(id, exists);
		this.defaultUseBy = defaultUseBy;
		this.imageId = imageId;
		this.name = name;
		this.desc = desc;
		this.gtin = gtin;
	}

	/**
	 * From json.
	 *
	 * @param obj the obj
	 * @return the food item
	 * @throws JSONException the JSON exception
	 */
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

	/**
	 * Gets the default use by.
	 *
	 * @return the default use by
	 */
	public long getDefaultUseBy()
	{
		return defaultUseBy;
	}

	/**
	 * Sets the default use by.
	 *
	 * @param defaultUseBy the new default use by
	 */
	public void setDefaultUseBy(long defaultUseBy)
	{
		this.defaultUseBy = defaultUseBy;
	}

	/**
	 * Gets the image id.
	 *
	 * @return the image id
	 */
	public String getImageId()
	{
		return imageId;
	}

	/**
	 * Sets the image id.
	 *
	 * @param imageId the new image id
	 */
	public void setImageId(String imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * Sets the desc.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	/**
	 * Gets the gtin.
	 *
	 * @return the gtin
	 */
	public String getGtin()
	{
		return gtin;
	}

	/**
	 * Sets the gtin.
	 *
	 * @param gtin the new gtin
	 */
	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.model.AbstractEntity#makeJSONObject()
	 */
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
