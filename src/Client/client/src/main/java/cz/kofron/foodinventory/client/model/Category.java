package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 22.3.14.
 */
public class Category extends AbstractEntity
{
	private String name;

	public Category(int id)
	{
		super(id, true);
	}

	public static Category fromJSON(JSONObject obj) throws JSONException
	{
		Category category = new Category(obj.getInt("id"));

		category.name = obj.getString("name");

		return category;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("name", name);

		return obj;
	}
}
