package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class DeleteInventoryRequest extends Message
{
	private int id;

	public DeleteInventoryRequest()
	{
	}

	public DeleteInventoryRequest(int id)
	{
		this.id = id;
	}

	@Override
	public String getHeader()
	{
		return "DeleteInventoryRequest";
	}

	@Override
	public Message newMessage()
	{
		return new DeleteInventoryRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		id = obj.getInt("id");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		return obj;
	}
}
