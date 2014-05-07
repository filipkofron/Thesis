package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class EditInventoryRequest extends Message
{
	
	/** The adding. */
	private boolean adding;
	
	/** The id. */
	private int id;
	
	/** The food id. */
	private int foodId;
	
	/** The use by. */
	private long useBy;
	
	/** The count. */
	private int count;

	/**
	 * Instantiates a new edits the inventory request.
	 */
	public EditInventoryRequest()
	{
	}

	/**
	 * Instantiates a new edits the inventory request.
	 *
	 * @param adding the adding
	 * @param id the id
	 * @param foodId the food id
	 * @param useBy the use by
	 * @param count the count
	 */
	public EditInventoryRequest(boolean adding, int id, int foodId, long useBy, int count)
	{
		this.adding = adding;
		this.id = id;
		this.foodId = foodId;
		this.useBy = useBy;
		this.count = count;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "EditInventoryRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new EditInventoryRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		adding = obj.getBoolean("adding");
		id = obj.getInt("id");
		foodId = obj.getInt("foodId");

		java.sql.Timestamp useByTimeStamp = Timestamp.valueOf(obj.getString("useBy"));
		useBy = useByTimeStamp.getTime();
		count = obj.getInt("count");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("adding", adding);
		obj.put("id", id);
		obj.put("foodId", foodId);

		java.sql.Timestamp useByTimeStamp = new Timestamp(useBy);
		obj.put("useBy", useByTimeStamp.toString());
		obj.put("count", count);
		return obj;
	}
}
