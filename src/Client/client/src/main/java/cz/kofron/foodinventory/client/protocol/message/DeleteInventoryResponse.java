package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class DeleteInventoryResponse extends Message
{
	private boolean success;

	public DeleteInventoryResponse()
	{
	}

	public DeleteInventoryResponse(boolean success)
	{
		this.success = success;
	}

	@Override
	public String getHeader()
	{
		return "DeleteInventoryResponse";
	}

	@Override
	public Message newMessage()
	{
		return new DeleteInventoryResponse();
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
