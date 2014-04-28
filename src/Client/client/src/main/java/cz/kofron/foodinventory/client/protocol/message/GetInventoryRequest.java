package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class GetInventoryRequest extends Message
{
	
	/** The direct. */
	private boolean direct; // only inventory by the id is retrieved
	
	/** The id. */
	private int id;
	
	/** The food. */
	private String food;
	
	/** The gtin. */
	private String gtin;

	/**
	 * Instantiates a new gets the inventory request.
	 */
	public GetInventoryRequest()
	{
	}

	/**
	 * Instantiates a new gets the inventory request.
	 *
	 * @param direct the direct
	 * @param id the id
	 * @param food the food
	 * @param gtin the gtin
	 */
	public GetInventoryRequest(boolean direct, int id, String food, String gtin)
	{
		this.direct = direct;
		this.id = id;
		this.food = food;
		this.gtin = gtin;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetInventoryRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetInventoryRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		direct = obj.getBoolean("direct");
		id = obj.getInt("id");
		food = obj.getString("food");
		gtin = obj.getString("gtin");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("direct", direct);
		obj.put("id", id);
		obj.put("food", food);
		obj.put("gtin", gtin);

		return obj;
	}
}
