package cz.kofron.foodinventory.client.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 12.3.14.
 */
public class KeepAlive extends Message
{
	@Override
	public String getHeader()
	{
		return "KeepAlive";
	}

	@Override
	public Message newMessage()
	{
		return new KeepAlive();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		return null;
	}
}
