package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.Category;
import cz.kofron.foodinventory.client.model.Vendor;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 22.3.14.
 */
public class GetFoodBaseResponse extends Message
{
	
	/** The vendors. */
	private ArrayList<Vendor> vendors;
	
	/** The categories. */
	private ArrayList<Category> categories;

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetFoodBaseResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetFoodBaseResponse();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray categoryArray;

		if(obj.isNull("categories"))
		{
			categoryArray = new JSONArray();
		}
		else
		{
			categoryArray = obj.getJSONArray("categories");
		}

		categories = new ArrayList<>(categoryArray.length());

		for(int i = 0; i < categoryArray.length(); i++)
		{
			categories.add(Category.fromJSON(categoryArray.getJSONObject(i)));
		}

		JSONArray vendorArray;

		if(obj.isNull("vendors"))
		{
			vendorArray = new JSONArray();
		}
		else
		{
			vendorArray = obj.getJSONArray("vendors");
		}

		vendors = new ArrayList<>(vendorArray.length());

		for(int i = 0; i < vendorArray.length(); i++)
		{
			vendors.add(Vendor.fromJSON(vendorArray.getJSONObject(i)));
		}
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();

		JSONArray categoryArray = new JSONArray();

		int i = 0;
		for(Category category : categories)
		{
			categoryArray.put(i, category.toJSON());
			i++;
		}

		obj.put("categories", categoryArray);

		JSONArray vendorArray = new JSONArray();

		i = 0;
		for(Vendor vendor : vendors)
		{
			vendorArray.put(i, vendor.toJSON());
			i++;
		}

		return obj;
	}

	/**
	 * Gets the vendors.
	 *
	 * @return the vendors
	 */
	public ArrayList<Vendor> getVendors()
	{
		return vendors;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public ArrayList<Category> getCategories()
	{
		return categories;
	}
}
