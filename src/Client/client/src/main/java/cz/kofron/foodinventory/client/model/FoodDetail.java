package cz.kofron.foodinventory.client.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kofee on 14.3.14.
 */
public class FoodDetail extends AbstractEntity
{
	private String name;
	private String vendor;
	private int vendorId;
	private String gtin;
	private ArrayList<String> imageIds;
	private String description;
	private long defaultUseBy;
	private float usualPrice;
	private ArrayList<FoodReview> reviews;
	private String addedBy;
	private String lastEditedBy;

	public FoodDetail(int id)
	{
		super(id, true);
	}

	public static FoodDetail fromJson(JSONObject obj) throws JSONException
	{
		FoodDetail fd = new FoodDetail(obj.getInt("id"));

		fd.name = obj.getString("name");
		fd.vendor = obj.getString("vendor");
		fd.vendorId = obj.getInt("vendorId");
		fd.gtin = obj.getString("gtin");

		JSONArray imageArray = obj.getJSONArray("imageIds");
		fd.imageIds = new ArrayList<>(imageArray.length());

		for(int i = 0; i < imageArray.length(); i++)
		{
			fd.imageIds.add(imageArray.getString(i));
		}

		fd.description = obj.getString("description");
		fd.defaultUseBy = obj.getLong("defaultUseBy");
		fd.usualPrice = (float) obj.getDouble("usualPrice");

		JSONArray reviewArray = obj.getJSONArray("reviews");
		fd.reviews = new ArrayList<>(reviewArray.length());

		for(int i = 0; i < reviewArray.length(); i++)
		{
			fd.reviews.add(FoodReview.fromJson(reviewArray.getJSONObject(i)));
		}

		fd.addedBy = obj.getString("addedBy");
		fd.lastEditedBy = obj.getString("lastEditedBy");

		return fd;
	}

	@Override
	protected JSONObject makeJSONObject() throws JSONException
	{
		JSONObject obj = new JSONObject();

		obj.put("name", name);
		obj.put("vendor", vendor);
		obj.put("vendorId", vendorId);
		obj.put("gtin", gtin);

		JSONArray imageArray = new JSONArray();

		int i = 0;
		for(String imageId : imageIds)
		{
			imageArray.put(i, imageId);
			i++;
		}

		obj.put("imageIds", imageArray);
		obj.put("description", description);
		obj.put("defaultUseBy", defaultUseBy);
		obj.put("usualPrice", usualPrice);

		JSONArray reviewArray = new JSONArray();

		i = 0;
		for(FoodReview review : reviews)
		{
			reviewArray.put(i, review.toJSON());
			i++;
		}

		obj.put("reviews", reviewArray);
		obj.put("addedBy", addedBy);
		obj.put("lastEditedBy", lastEditedBy);

		return obj;
	}
}
