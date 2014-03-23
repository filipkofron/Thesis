package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 23.3.14.
 */
public class SetUserReviewRequest extends Message
{
	private float rating;
	private String text;

	public SetUserReviewRequest()
	{
	}

	public SetUserReviewRequest(float rating, String text)
	{
		this.rating = rating;
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
		text = obj.getString("text");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("rating", rating);
		obj.put("text", text);

		return obj;
	}
}
