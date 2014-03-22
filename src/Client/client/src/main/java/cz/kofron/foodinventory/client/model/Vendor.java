package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 22.3.14.
 */
public class Vendor extends AbstractEntity
{
	private String name;

	public Vendor(int id)
	{
		super(id, true);
	}

	public static Vendor fromJSON(JSONObject obj) throws JSONException
	{
		Vendor vendor = new Vendor(obj.getInt("id"));

		vendor.name = obj.getString("name");

		return vendor;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("name", name);

		return obj;
	}
}
