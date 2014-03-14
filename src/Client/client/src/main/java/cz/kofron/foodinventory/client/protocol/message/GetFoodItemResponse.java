package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.FoodItem;

/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodItemResponse extends Message
{
	private ArrayList<FoodItem> foods;

	public GetFoodItemResponse()
	{
	}

	public GetFoodItemResponse(ArrayList<FoodItem> foods)
	{
		this.foods = foods;
	}

	@Override
	public String getHeader()
	{
		return "GetFoodItemResponse";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodItemResponse();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray array = obj.getJSONArray("foods");

		foods = new ArrayList<>(array.length());

		for(int i = 0; i < array.length(); i++)
		{
			foods.add(FoodItem.fromJson(array.getJSONObject(i)));
		}
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		int i = 0;
		for(FoodItem foodItem : foods)
		{
			array.put(i, foodItem.toJSON());
		}

		obj.put("foods", array);

		return obj;
	}
}
