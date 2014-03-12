package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by kofee on 11.3.14.
 */
public class InventoryItem extends AbstractEntity
{
	protected int userId;
	protected String userName;

	protected int foodId;
	protected String foodName;

	protected long useBy;

	protected InventoryItem(int id)
	{
		super(id, true);
	}

	public InventoryItem(int id, boolean exists, int foodId, int userId, String userName, String foodName, long useBy)
	{
		super(id, exists);
		this.foodId = foodId;
		this.userId = userId;
		this.userName = userName;
		this.foodName = foodName;
		this.useBy = useBy;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
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

	public long getUseBy()
	{
		return useBy;
	}

	public void setUseBy(long useBy)
	{
		this.useBy = useBy;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject object = new JSONObject();

		object.put("userId", userId);
		object.put("userName", userName);

		object.put("foodId", foodId);
		object.put("foodName", foodName);

		java.sql.Timestamp useByTimeStamp = new Timestamp(useBy);

		object.put("useBy", useByTimeStamp.toString());

		return object;
	}

	public static InventoryItem fromJSON(JSONObject jsonObject) throws JSONException
	{
		InventoryItem ii = new InventoryItem(jsonObject.getInt("id"));

		ii.userId = jsonObject.getInt("userId");
		ii.userName = jsonObject.getString("userName");

		ii.foodId = jsonObject.getInt("foodId");
		ii.foodName = jsonObject.getString("foodName");

		java.sql.Timestamp useByTimeStamp = Timestamp.valueOf(jsonObject.getString("useBy"));
		ii.useBy = useByTimeStamp.getTime();

		return ii;
	}
}
