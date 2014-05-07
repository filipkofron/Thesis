package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 11.3.14.
 */
public class InventoryItem extends AbstractEntity
{
	
	/** The food id. */
	protected int foodId;
	
	/** The image id. */
	protected int imageId;
	
	/** The food name. */
	protected String foodName;
	
	/** The food gtin. */
	protected String foodGtin;

	/** The use by. */
	protected long useBy;

	/**
	 * Instantiates a new inventory item.
	 *
	 * @param id the id
	 */
	protected InventoryItem(int id)
	{
		super(id, true);
	}

	/**
	 * Instantiates a new inventory item.
	 *
	 * @param id the id
	 * @param exists the exists
	 * @param foodId the food id
	 * @param imageId the image id
	 * @param foodName the food name
	 * @param foodGtin the food gtin
	 * @param useBy the use by
	 */
	public InventoryItem(int id, boolean exists, int foodId, int imageId, String foodName, String foodGtin, long useBy)
	{
		super(id, exists);
		this.foodId = foodId;
		this.imageId = imageId;
		this.foodName = foodName;
		this.foodGtin = foodGtin;
		this.useBy = useBy;
	}

	/**
	 * From json.
	 *
	 * @param jsonObject the json object
	 * @return the inventory item
	 * @throws JSONException the JSON exception
	 */
	public static InventoryItem fromJSON(JSONObject jsonObject) throws JSONException
	{
		InventoryItem ii = new InventoryItem(jsonObject.getInt("id"));

		ii.foodId = jsonObject.getInt("foodId");
		ii.imageId = jsonObject.getInt("imageId");
		ii.foodName = jsonObject.getString("foodName");
		ii.foodGtin = jsonObject.getString("foodGtin");

		java.sql.Timestamp useByTimeStamp = Timestamp.valueOf(jsonObject.getString("useBy"));
		ii.useBy = useByTimeStamp.getTime();

		return ii;
	}

	/**
	 * Gets the food id.
	 *
	 * @return the food id
	 */
	public int getFoodId()
	{
		return foodId;
	}

	/**
	 * Sets the food id.
	 *
	 * @param foodId the new food id
	 */
	public void setFoodId(int foodId)
	{
		this.foodId = foodId;
	}

	/**
	 * Gets the food name.
	 *
	 * @return the food name
	 */
	public String getFoodName()
	{
		return foodName;
	}

	/**
	 * Sets the food name.
	 *
	 * @param foodName the new food name
	 */
	public void setFoodName(String foodName)
	{
		this.foodName = foodName;
	}

	/**
	 * Gets the food gtin.
	 *
	 * @return the food gtin
	 */
	public String getFoodGtin()
	{
		return foodGtin;
	}

	/**
	 * Sets the food gtin.
	 *
	 * @param foodGtin the new food gtin
	 */
	public void setFoodGtin(String foodGtin)
	{
		this.foodGtin = foodGtin;
	}

	/**
	 * Gets the use by.
	 *
	 * @return the use by
	 */
	public long getUseBy()
	{
		return useBy;
	}

	/**
	 * Sets the use by.
	 *
	 * @param useBy the new use by
	 */
	public void setUseBy(long useBy)
	{
		this.useBy = useBy;
	}

	/**
	 * Gets the image id.
	 *
	 * @return the image id
	 */
	public int getImageId()
	{
		return imageId;
	}

	/**
	 * Sets the image id.
	 *
	 * @param imageId the new image id
	 */
	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.model.AbstractEntity#makeJSONObject()
	 */
	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject object = new JSONObject();

		object.put("foodId", foodId);
		object.put("imageId", imageId);
		object.put("foodName", foodName);
		object.put("foodGtin", foodGtin);

		java.sql.Timestamp useByTimeStamp = new Timestamp(useBy);

		object.put("useBy", useByTimeStamp.toString());

		return object;
	}
}
