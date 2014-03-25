package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kofee
 */
public abstract class Message
{

	private final static Map<String, Message> messageChilds = new HashMap<String, Message>();

	public static void registerMessageChild(Message message)
	{
		messageChilds.put(message.getHeader(), message);
	}

	public final static Message dejsonize(JSONObject obj)
	{
		try
		{
			String header = (String) obj.get("header");
			Message factoryMessage = messageChilds.get(header);
			Message message = factoryMessage.newMessage();
			if(!obj.isNull("content"))
			{
				message.dejsonizeContent((JSONObject) obj.get("content"));
			}
			return message;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public abstract String getHeader();

	public abstract Message newMessage();

	protected abstract void dejsonizeContent(JSONObject obj) throws JSONException;

	protected abstract JSONObject jsonizeContent() throws JSONException;

	public final JSONObject jsonize()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("header", getHeader());
			obj.put("content", jsonizeContent());
			return obj;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
