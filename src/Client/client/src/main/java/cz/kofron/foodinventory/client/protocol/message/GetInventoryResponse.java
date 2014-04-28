package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.InventoryItem;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 13.3.14.
 */
public class GetInventoryResponse extends Message
{
	
	/** The intentory items. */
	private ArrayList<InventoryItem> intentoryItems;

	/**
	 * Instantiates a new gets the inventory response.
	 *
	 * @param intentoryItems the intentory items
	 */
	public GetInventoryResponse(ArrayList<InventoryItem> intentoryItems)
	{
		this.intentoryItems = intentoryItems;
	}

	/**
	 * Instantiates a new gets the inventory response.
	 */
	public GetInventoryResponse()
	{
	}


	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetInventoryResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetInventoryResponse();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray array;
		if(!obj.isNull("items"))
		{
			array = obj.getJSONArray("items");
		}
		else
		{
			array = new JSONArray();
		}

		int len = array.length();
		intentoryItems = new ArrayList<>(len);

		for (int i = 0; i < len; i++)
		{
			InventoryItem ii = InventoryItem.fromJSON(array.getJSONObject(i));
			intentoryItems.add(ii);
		}
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		int i = 0;
		for (InventoryItem ii : intentoryItems)
		{
			array.put(i, ii.toJSON());
			i++;
		}

		obj.put("items", array);

		return obj;
	}

	/**
	 * Gets the intentory items.
	 *
	 * @return the intentory items
	 */
	public ArrayList<InventoryItem> getIntentoryItems()
	{
		return intentoryItems;
	}
}
