package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 22.3.14.
 */
public class Category extends AbstractEntity
{
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new category.
	 *
	 * @param id the id
	 */
	public Category(int id)
	{
		super(id, true);
	}

	/**
	 * From json.
	 *
	 * @param obj the obj
	 * @return the category
	 * @throws JSONException the JSON exception
	 */
	public static Category fromJSON(JSONObject obj) throws JSONException
	{
		Category category = new Category(obj.getInt("id"));

		category.name = obj.getString("name");

		return category;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.model.AbstractEntity#makeJSONObject()
	 */
	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("name", name);

		return obj;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
}
