package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 11.3.14.
 */
public abstract class AbstractEntity implements Comparable
{
	
	/** The id. */
	private int id;
	
	/** The exists. */
	private boolean exists;

	/**
	 * Instantiates a new abstract entity.
	 *
	 * @param id the id
	 * @param exists the exists
	 */
	public AbstractEntity(int id, boolean exists)
	{
		this.id = id;
		this.exists = exists;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Make json object.
	 *
	 * @return the JSON object
	 * @throws JSONException the JSON exception
	 */
	protected abstract JSONObject makeJSONObject() throws JSONException;

	/**
	 * To json.
	 *
	 * @return the JSON object
	 * @throws JSONException the JSON exception
	 */
	public final JSONObject toJSON() throws JSONException
	{
		JSONObject object = makeJSONObject();
		object.put("id", id);

		return object;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
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
