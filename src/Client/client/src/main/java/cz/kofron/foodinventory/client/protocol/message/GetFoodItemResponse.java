package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.FoodItem;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodItemResponse extends Message
{
	
	/** The foods. */
	private ArrayList<FoodItem> foods;

	/**
	 * Instantiates a new gets the food item response.
	 */
	public GetFoodItemResponse()
	{
	}

	/**
	 * Instantiates a new gets the food item response.
	 *
	 * @param foods the foods
	 */
	public GetFoodItemResponse(ArrayList<FoodItem> foods)
	{
		this.foods = foods;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetFoodItemResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetFoodItemResponse();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray array;

		if(obj.isNull("foods"))
		{
			array = new JSONArray();
		}
		else
		{
			array = obj.getJSONArray("foods");
		}

		foods = new ArrayList<>(array.length());

		for (int i = 0; i < array.length(); i++)
		{
			foods.add(FoodItem.fromJson(array.getJSONObject(i)));
		}
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		int i = 0;
		for (FoodItem foodItem : foods)
		{
			array.put(i, foodItem.toJSON());
		}

		obj.put("foods", array);

		return obj;
	}

	/**
	 * Gets the foods.
	 *
	 * @return the foods
	 */
	public ArrayList<FoodItem> getFoods()
	{
		return foods;
	}
}
