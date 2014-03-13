package cz.kofron.foodinventory.client.protocol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.InventoryItem;

/**
 * Created by kofee on 13.3.14.
 */
public class GetInventoryResponse extends Message
{
	private ArrayList<InventoryItem> intentoryItems;

	public GetInventoryResponse(ArrayList<InventoryItem> intentoryItems)
	{
		this.intentoryItems = intentoryItems;
	}

	public GetInventoryResponse()
	{
	}


	@Override
	public String getHeader()
	{
		return "GetInventoryResponse";
	}

	@Override
	public Message newMessage()
	{
		return new GetInventoryResponse();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray array = obj.getJSONArray("items");
		int len = array.length();
		intentoryItems = new ArrayList<>();

		for(int i = 0; i < len; i++)
		{
			InventoryItem ii = InventoryItem.fromJSON(array.getJSONObject(i));
			intentoryItems.add(ii);
		}
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		int i = 0;
		for(InventoryItem ii : intentoryItems)
		{
			array.put(i, ii.toJSON());
			i++;
		}

		obj.put("items", array);

		return obj;
	}

	public ArrayList<InventoryItem> getIntentoryItems()
	{
		return intentoryItems;
	}
}
