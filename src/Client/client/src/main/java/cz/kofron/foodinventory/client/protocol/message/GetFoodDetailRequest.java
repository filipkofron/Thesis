package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodDetailRequest extends Message
{
	private int id;

	public GetFoodDetailRequest()
	{
	}

	public GetFoodDetailRequest(int id)
	{
		this.id = id;
	}

	@Override
	public String getHeader()
	{
		return "GetFoodDetailRequest";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodDetailRequest();
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
