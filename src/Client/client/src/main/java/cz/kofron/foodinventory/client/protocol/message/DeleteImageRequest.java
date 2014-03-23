package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 23.3.14.
 */
public class DeleteImageRequest extends Message
{
	private int id;

	public DeleteImageRequest()
	{
	}

	public DeleteImageRequest(int id)
	{
		this.id = id;
	}

	@Override
	public String getHeader()
	{
		return "DeleteImageRequest";
	}

	@Override
	public Message newMessage()
	{
		return new DeleteImageRequest();
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
