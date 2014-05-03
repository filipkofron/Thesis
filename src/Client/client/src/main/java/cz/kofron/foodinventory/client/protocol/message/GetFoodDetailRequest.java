package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodDetailRequest extends Message
{
	
	/** The id. */
	private int id;

	/**
	 * Instantiates a new gets the food detail request.
	 */
	public GetFoodDetailRequest()
	{
	}

	/**
	 * Instantiates a new gets the food detail request.
	 *
	 * @param id the id
	 */
	public GetFoodDetailRequest(int id)
	{
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetFoodDetailRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetFoodDetailRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		id = obj.getInt("id");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		return obj;
	}
}
