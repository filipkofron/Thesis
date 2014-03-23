package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class EditFoodResponse extends Message
{
	private boolean success;
	private int id;

	public EditFoodResponse()
	{
	}

	public EditFoodResponse(boolean success, int id)
	{
		this.success = success;
		this.id = id;
	}

	@Override
	public String getHeader()
	{
		return "EditFoodResponse";
	}

	@Override
	public Message newMessage()
	{
		return new EditFoodResponse();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		success = obj.getBoolean("success");
		id = obj.getInt("id");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("success", success);
		obj.put("id", id);
		return obj;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public int getId()
	{
		return id;
	}
}
