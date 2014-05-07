package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class SetUserReviewRequest extends Message
{
	
	/** The rating. */
	private float rating;
	
	/** The delete. */
	private boolean delete;
	
	/** The food id. */
	private int foodId;
	
	/** The text. */
	private String text;

	/**
	 * Instantiates a new sets the user review request.
	 */
	public SetUserReviewRequest()
	{
	}

	/**
	 * Instantiates a new sets the user review request.
	 *
	 * @param rating the rating
	 * @param delete the delete
	 * @param foodId the food id
	 * @param text the text
	 */
	public SetUserReviewRequest(float rating, boolean delete, int foodId, String text)
	{
		this.rating = rating;
		this.delete = delete;
		this.foodId = foodId;
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "SetUserReviewRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new SetUserReviewRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		rating = (float) obj.getDouble("rating");
		delete = obj.getBoolean("delete");
		text = obj.getString("text");
		foodId = obj.getInt("foodId");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
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
