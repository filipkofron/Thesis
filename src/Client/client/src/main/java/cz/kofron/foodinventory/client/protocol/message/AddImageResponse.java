package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 23.3.14.
 */
public class AddImageResponse extends Message
{
	private boolean success;

	public AddImageResponse()
	{
	}

	public AddImageResponse(boolean success)
	{
		this.success = success;
	}

	@Override
	public String getHeader()
	{
		return "AddImageResponse";
	}

	@Override
	public Message newMessage()
	{
		return new AddImageResponse();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		success = obj.getBoolean("success");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("success", success);
		return obj;
	}

	public boolean isSuccess()
	{
		return success;
	}
}
