package cz.kofron.foodinventory.client.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kofee on 14.3.14.
 */
public class FoodReview extends AbstractEntity
{
	private float rating;
	private String username;
	private String text;

	public FoodReview(int id)
	{
		super(id, true);
	}

	public FoodReview(int id, boolean exists, float rating, String username, String text)
	{
		super(id, exists);
		this.rating = rating;
		this.username = username;
		this.text = text;
	}

	public static FoodReview fromJson(JSONObject obj) throws JSONException
	{
		FoodReview foodReview = new FoodReview(obj.getInt("id"));

		foodReview.rating = (float) obj.getDouble("rating");
		foodReview.username = obj.getString("username");
		foodReview.text = obj.getString("text");

		return foodReview;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("rating", rating);
		obj.put("username", username);
		obj.put("test", text);

		return obj;
	}

	public float getRating()
	{
		return rating;
	}

	public String getUsername()
	{
		return username;
	}

	public String getText()
	{
		return text;
	}
}
