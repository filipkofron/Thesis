package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodItemRequest extends Message
{
	private boolean direct;
	private int id;

	private String name;
	private String gtin;

	private int skip;

	public GetFoodItemRequest()
	{
	}

	public GetFoodItemRequest(boolean direct, int id, String name, String gtin, int skip)
	{
		this.direct = direct;
		this.id = id;
		this.name = name;
		this.gtin = gtin;
		this.skip = skip;
	}

	@Override
	public String getHeader()
	{
		return "GetFoodItemRequest";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodItemRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		direct = obj.getBoolean("direct");
		id = obj.getInt("id");
		name = obj.getString("name");
		gtin = obj.getString("gtin");
		skip = obj.getInt("skip");
	}

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
