package cz.kofron.foodinventory.client.protocol;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 13.3.14.
 */
public class GetInventoryRequest extends Message
{
	private boolean direct; // only inventory by the id is retrieved
	private int id;
	private String food;
	private String gtin;

	public GetInventoryRequest()
	{
	}

	public GetInventoryRequest(boolean direct, int id, String food, String gtin)
	{
		this.direct = direct;
		this.id = id;
		this.food = food;
		this.gtin = gtin;
	}

	@Override
	public String getHeader()
	{
		return "GetInventoryRequest";
	}

	@Override
	public Message newMessage()
	{
		return new GetInventoryRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		direct = obj.getBoolean("direct");
		id = obj.getInt("id");
		food = obj.getString("food");
		gtin = obj.getString("gtin");
	}

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
