package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 23.3.14.
 */
public class SetUserReviewRequest extends Message
{
	private float rating;
	private boolean delete;
	private int foodId;
	private String text;

	public SetUserReviewRequest()
	{
	}

	public SetUserReviewRequest(float rating, boolean delete, int foodId, String text)
	{
		this.rating = rating;
		this.delete = delete;
		this.foodId = foodId;
		this.text = text;
	}

	@Override
	public String getHeader()
	{
		return "SetUserReviewRequest";
	}

	@Override
	public Message newMessage()
	{
		return new SetUserReviewRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		rating = (float) obj.getDouble("rating");
		delete = obj.getBoolean("delete");
		text = obj.getString("text");
		foodId = obj.getInt("foodId");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("rating", rating);
		obj.put("text", text);
		obj.put("delete", delete);
		obj.put("foodId", foodId);

		return obj;
	}
}
