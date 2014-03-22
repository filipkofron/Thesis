package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 22.3.14.
 */
public class GetFoodBaseRequest extends Message
{
	@Override
	public String getHeader()
	{
		return "GetFoodBaseRequest";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodBaseRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{

	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		return new JSONObject();
	}
}
