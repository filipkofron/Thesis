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
	String category;
	int categoryId;
	private String vendor;
	private int vendorId;
	private String gtin;
	private ArrayList<String> imageIds;
	private String description;
	private long defaultUseBy;
	private int amountType;
	private float amount;
	private float usualPrice;
	private ArrayList<FoodReview> reviews;
	private String addedBy;
	private String lastEditedBy;

	public FoodDetail(int id)
	{
		super(id, true);
	}

	public FoodDetail(int id, boolean exists, String name, String category, int categoryId, String vendor, int vendorId, String gtin, ArrayList<String> imageIds, String description, long defaultUseBy, int amountType, float amount, float usualPrice, ArrayList<FoodReview> reviews, String addedBy, String lastEditedBy)
	{
		super(id, exists);
		this.name = name;
		this.category = category;
		this.categoryId = categoryId;
		this.vendor = vendor;
		this.vendorId = vendorId;
		this.gtin = gtin;
		this.imageIds = imageIds;
		this.description = description;
		this.defaultUseBy = defaultUseBy;
		this.amountType = amountType;
		this.amount = amount;
		this.usualPrice = usualPrice;
		this.reviews = reviews;
		this.addedBy = addedBy;
		this.lastEditedBy = lastEditedBy;
	}

	public static FoodDetail fromJson(JSONObject obj) throws JSONException
	{
		FoodDetail fd = new FoodDetail(obj.getInt("id"));

		fd.name = obj.getString("name");
		fd.category = obj.getString("category");
		fd.categoryId = obj.getInt("categoryId");
		fd.vendor = obj.getString("vendor");
		fd.vendorId = obj.getInt("vendorId");
		fd.gtin = obj.getString("gtin");

		JSONArray imageArray;

		if(obj.isNull("imageIds"))
		{
			imageArray = new JSONArray();
		}
		else
		{
			imageArray = obj.getJSONArray("imageIds");
		}


		fd.imageIds = new ArrayList<>(imageArray.length());

		for (int i = 0; i < imageArray.length(); i++)
		{
			fd.imageIds.add(imageArray.getString(i));
		}

		fd.description = obj.getString("description");
		fd.defaultUseBy = obj.getLong("defaultUseBy");
		fd.amountType = obj.getInt("amountType");
		fd.amount = (float) obj.getDouble("amount");
		fd.usualPrice = (float) obj.getDouble("usualPrice");

		JSONArray reviewArray = obj.getJSONArray("reviews");
		fd.reviews = new ArrayList<>(reviewArray.length());

		for (int i = 0; i < reviewArray.length(); i++)
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
		obj.put("category", category);
		obj.put("categoryId", categoryId);
		obj.put("vendor", vendor);
		obj.put("vendorId", vendorId);
		obj.put("gtin", gtin);

		JSONArray imageArray = new JSONArray();

		int i = 0;
		for (String imageId : imageIds)
		{
			imageArray.put(i, imageId);
			i++;
		}

		obj.put("imageIds", imageArray);
		obj.put("description", description);
		obj.put("defaultUseBy", defaultUseBy);
		obj.put("amountType", amountType);
		obj.put("amount", amount);
		obj.put("usualPrice", usualPrice);

		JSONArray reviewArray = new JSONArray();

		i = 0;
		for (FoodReview review : reviews)
		{
			reviewArray.put(i, review.toJSON());
			i++;
		}

		obj.put("reviews", reviewArray);
		obj.put("addedBy", addedBy);
		obj.put("lastEditedBy", lastEditedBy);

		return obj;
	}

	public String getName()
	{
		return name;
	}

	public String getVendor()
	{
		return vendor;
	}

	public int getVendorId()
	{
		return vendorId;
	}

	public String getGtin()
	{
		return gtin;
	}

	public ArrayList<String> getImageIds()
	{
		return imageIds;
	}

	public String getDescription()
	{
		return description;
	}

	public long getDefaultUseBy()
	{
		return defaultUseBy;
	}

	public int getAmountType()
	{
		return amountType;
	}

	public float getAmount()
	{
		return amount;
	}

	public float getUsualPrice()
	{
		return usualPrice;
	}

	public ArrayList<FoodReview> getReviews()
	{
		return reviews;
	}

	public String getAddedBy()
	{
		return addedBy;
	}

	public String getLastEditedBy()
	{
		return lastEditedBy;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
}
