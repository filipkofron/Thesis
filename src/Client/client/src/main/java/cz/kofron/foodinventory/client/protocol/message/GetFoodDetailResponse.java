package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.FoodDetail;

/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodDetailResponse extends Message
{
	private ArrayList<FoodDetail> foodDetails;

	public GetFoodDetailResponse()
	{
	}

	public GetFoodDetailResponse(ArrayList<FoodDetail> foodDetails)
	{
		this.foodDetails = foodDetails;
	}

	@Override
	public String getHeader()
	{
		return "GetFoodDetailResponse";
	}

	@Override
	public Message newMessage()
	{
		return new GetFoodDetailResponse();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		JSONArray array;

		if(obj.isNull("foodDetails"))
		{
			array = new JSONArray();
		}
		else
		{
			array = obj.getJSONArray("foodDetails");
		}

		foodDetails = new ArrayList<>(array.length());

		for (int i = 0; i < array.length(); i++)
		{
			foodDetails.add(FoodDetail.fromJson(array.getJSONObject(i)));
		}
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();

		int i = 0;
		for (FoodDetail fd : foodDetails)
		{
			array.put(i, fd.toJSON());
			i++;
		}

		obj.put("foodDetails", array);

		return obj;
	}

	public ArrayList<FoodDetail> getFoodDetails()
	{
		return foodDetails;
	}
}
