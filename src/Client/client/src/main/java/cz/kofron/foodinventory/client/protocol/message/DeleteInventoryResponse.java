package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class DeleteInventoryResponse extends Message
{
	
	/** The success. */
	private boolean success;

	/**
	 * Instantiates a new delete inventory response.
	 */
	public DeleteInventoryResponse()
	{
	}

	/**
	 * Instantiates a new delete inventory response.
	 *
	 * @param success the success
	 */
	public DeleteInventoryResponse(boolean success)
	{
		this.success = success;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "DeleteInventoryResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new DeleteInventoryResponse();
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
