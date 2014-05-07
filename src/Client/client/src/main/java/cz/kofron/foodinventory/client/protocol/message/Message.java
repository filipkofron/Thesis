package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Message.
 *
 * @author kofee
 */
public abstract class Message
{

	/** The Constant messageChilds. */
	private final static Map<String, Message> messageChilds = new HashMap<String, Message>();

	/**
	 * Register message child.
	 *
	 * @param message the message
	 */
	public static void registerMessageChild(Message message)
	{
		messageChilds.put(message.getHeader(), message);
	}

	/**
	 * Dejsonize.
	 *
	 * @param obj the obj
	 * @return the message
	 */
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

	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public abstract String getHeader();

	/**
	 * New message.
	 *
	 * @return the message
	 */
	public abstract Message newMessage();

	/**
	 * Dejsonize content.
	 *
	 * @param obj the obj
	 * @throws JSONException the JSON exception
	 */
	protected abstract void dejsonizeContent(JSONObject obj) throws JSONException;

	/**
	 * Jsonize content.
	 *
	 * @return the JSON object
	 * @throws JSONException the JSON exception
	 */
	protected abstract JSONObject jsonizeContent() throws JSONException;

	/**
	 * Jsonize.
	 *
	 * @return the JSON object
	 */
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
