package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class DeleteImageResponse extends Message
{
	
	/** The success. */
	private boolean success;

	/**
	 * Instantiates a new delete image response.
	 */
	public DeleteImageResponse()
	{
	}

	/**
	 * Instantiates a new delete image response.
	 *
	 * @param success the success
	 */
	public DeleteImageResponse(boolean success)
	{
		this.success = success;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "DeleteImageResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new DeleteImageResponse();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		success = obj.getBoolean("success");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("success", success);
		return obj;
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess()
	{
		return success;
	}
}
