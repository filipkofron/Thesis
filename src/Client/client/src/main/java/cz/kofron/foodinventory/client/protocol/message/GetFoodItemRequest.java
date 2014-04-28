package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodItemRequest extends Message
{
	
	/** The direct. */
	private boolean direct;
	
	/** The id. */
	private int id;

	/** The name. */
	private String name;
	
	/** The gtin. */
	private String gtin;

	/** The skip. */
	private int skip;

	/**
	 * Instantiates a new gets the food item request.
	 */
	public GetFoodItemRequest()
	{
	}

	/**
	 * Instantiates a new gets the food item request.
	 *
	 * @param direct the direct
	 * @param id the id
	 * @param name the name
	 * @param gtin the gtin
	 * @param skip the skip
	 */
	public GetFoodItemRequest(boolean direct, int id, String name, String gtin, int skip)
	{
		this.direct = direct;
		this.id = id;
		this.name = name;
		this.gtin = gtin;
		this.skip = skip;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetFoodItemRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetFoodItemRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		direct = obj.getBoolean("direct");
		id = obj.getInt("id");
		name = obj.getString("name");
		gtin = obj.getString("gtin");
		skip = obj.getInt("skip");
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
		obj.put("name", name);
		obj.put("gtin", gtin);
		obj.put("skip", skip);

		return obj;
	}
}
