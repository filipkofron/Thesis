package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.Category;
import cz.kofron.foodinventory.client.model.Vendor;

/**
 * Created by kofee on 22.3.14.
 */
public class GetFoodBaseResponse extends Message
{
	private ArrayList<Vendor> vendors;
	private ArrayList<Category> categories;

	@Override
	public String getHeader()
	{
		return "GetFoodBaseResponse";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodBaseResponse();
	}

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

	public ArrayList<Vendor> getVendors()
	{
		return vendors;
	}

	public ArrayList<Category> getCategories()
	{
		return categories;
	}
}
