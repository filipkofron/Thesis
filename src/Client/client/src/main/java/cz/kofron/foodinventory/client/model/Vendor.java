package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 22.3.14.
 */
public class Vendor extends AbstractEntity
{
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new vendor.
	 *
	 * @param id the id
	 */
	public Vendor(int id)
	{
		super(id, true);
	}

	/**
	 * From json.
	 *
	 * @param obj the obj
	 * @return the vendor
	 * @throws JSONException the JSON exception
	 */
	public static Vendor fromJSON(JSONObject obj) throws JSONException
	{
		Vendor vendor = new Vendor(obj.getInt("id"));

		vendor.name = obj.getString("name");

		return vendor;
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
