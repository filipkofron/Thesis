package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

/**
 * Created by kofee on 14.3.14.
 */
public class EditInventoryRequest extends Message
{
	private boolean adding;
	private int id;
	private int foodId;
	private long useBy;
	private int count;

	public EditInventoryRequest()
	{
	}

	public EditInventoryRequest(boolean adding, int id, int foodId, long useBy, int count)
	{
		this.adding = adding;
		this.id = id;
		this.foodId = foodId;
		this.useBy = useBy;
		this.count = count;
	}

	@Override
	public String getHeader()
	{
		return "EditInventoryRequest";
	}

	@Override
	public Message newMessage()
	{
		return new EditInventoryRequest();
	}

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
