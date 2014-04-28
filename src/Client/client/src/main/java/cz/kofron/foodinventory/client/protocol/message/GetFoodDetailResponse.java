package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.model.FoodDetail;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class GetFoodDetailResponse extends Message
{
	
	/** The food details. */
	private ArrayList<FoodDetail> foodDetails;

	/**
	 * Instantiates a new gets the food detail response.
	 */
	public GetFoodDetailResponse()
	{
	}

	/**
	 * Instantiates a new gets the food detail response.
	 *
	 * @param foodDetails the food details
	 */
	public GetFoodDetailResponse(ArrayList<FoodDetail> foodDetails)
	{
		this.foodDetails = foodDetails;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "GetFoodDetailResponse";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new GetFoodDetailResponse();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
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

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
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

	/**
	 * Gets the food details.
	 *
	 * @return the food details
	 */
	public ArrayList<FoodDetail> getFoodDetails()
	{
		return foodDetails;
	}
}
