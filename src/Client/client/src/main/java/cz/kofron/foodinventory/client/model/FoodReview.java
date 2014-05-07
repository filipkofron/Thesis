package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class FoodReview extends AbstractEntity
{
	
	/** The rating. */
	private float rating;
	
	/** The username. */
	private String username;
	
	/** The text. */
	private String text;

	/**
	 * Instantiates a new food review.
	 *
	 * @param id the id
	 */
	public FoodReview(int id)
	{
		super(id, true);
	}

	/**
	 * Instantiates a new food review.
	 *
	 * @param id the id
	 * @param exists the exists
	 * @param rating the rating
	 * @param username the username
	 * @param text the text
	 */
	public FoodReview(int id, boolean exists, float rating, String username, String text)
	{
		super(id, exists);
		this.rating = rating;
		this.username = username;
		this.text = text;
	}

	/**
	 * From json.
	 *
	 * @param obj the obj
	 * @return the food review
	 * @throws JSONException the JSON exception
	 */
	public static FoodReview fromJson(JSONObject obj) throws JSONException
	{
		FoodReview foodReview = new FoodReview(obj.getInt("id"));

		foodReview.rating = (float) obj.getDouble("rating");
		foodReview.username = obj.getString("username");
		foodReview.text = obj.getString("text");

		return foodReview;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.model.AbstractEntity#makeJSONObject()
	 */
	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("rating", rating);
		obj.put("username", username);
		obj.put("test", text);

		return obj;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public float getRating()
	{
		return rating;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}
}
