package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by kofee on 11.3.14.
 */
public class InventoryItem extends AbstractEntity
{
	protected int foodId;
	protected int imageId;
	protected String foodName;
	protected String foodGtin;

	protected long useBy;

	protected InventoryItem(int id)
	{
		super(id, true);
	}

	public InventoryItem(int id, boolean exists, int foodId, int imageId, String foodName, String foodGtin, long useBy)
	{
		super(id, exists);
		this.foodId = foodId;
		this.imageId = imageId;
		this.foodName = foodName;
		this.foodGtin = foodGtin;
		this.useBy = useBy;
	}

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

	public int getFoodId()
	{
		return foodId;
	}

	public void setFoodId(int foodId)
	{
		this.foodId = foodId;
	}

	public String getFoodName()
	{
		return foodName;
	}

	public void setFoodName(String foodName)
	{
		this.foodName = foodName;
	}

	public String getFoodGtin()
	{
		return foodGtin;
	}

	public void setFoodGtin(String foodGtin)
	{
		this.foodGtin = foodGtin;
	}

	public long getUseBy()
	{
		return useBy;
	}

	public void setUseBy(long useBy)
	{
		this.useBy = useBy;
	}

	public int getImageId()
	{
		return imageId;
	}

	public void setImageId(int imageId)
	{
		this.imageId = imageId;
	}

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
