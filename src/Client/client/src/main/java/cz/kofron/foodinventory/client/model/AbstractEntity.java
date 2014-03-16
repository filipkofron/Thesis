package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 11.3.14.
 */
public abstract class AbstractEntity implements Comparable
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

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof AbstractEntity))
		{
			return false;
		}
		int res = id - ((AbstractEntity) o).id;

		return res == 0;
	}

	@Override
	public int compareTo(Object o)
	{
		if(!(o instanceof AbstractEntity))
		{
			return -1;
		}
		int res = id - ((AbstractEntity) o).id;

		if(res < 0)
		{
			return -1;
		}
		if(res > 0)
		{
			return 1;
		}
		return 0;
	}
}
