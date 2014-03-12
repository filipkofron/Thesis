package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 11.3.14.
 */
public abstract class AbstractEntity
{
	private int id;
	private boolean exists;

	public AbstractEntity(int id, boolean exists)
	{
		this.id = id;
		this.exists = exists;
	}

	public int getId()
	{
		return id;
	}

	protected abstract JSONObject makeJSONObject() throws JSONException;

	public final JSONObject toJSON() throws JSONException
	{
		JSONObject object = makeJSONObject();
		object.put("id", id);

		return object;
	}
}
