package cz.kofron.foodinventory.client.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 14.3.14.
 */
public class FoodDetail extends AbstractEntity
{
	
	/** The name. */
	private String name;
	
	/** The category. */
	String category;
	
	/** The category id. */
	int categoryId;
	
	/** The vendor. */
	private String vendor;
	
	/** The vendor id. */
	private int vendorId;
	
	/** The gtin. */
	private String gtin;
	
	/** The image ids. */
	private ArrayList<String> imageIds;
	
	/** The description. */
	private String description;
	
	/** The default use by. */
	private long defaultUseBy;
	
	/** The amount type. */
	private int amountType;
	
	/** The amount. */
	private float amount;
	
	/** The usual price. */
	private float usualPrice;
	
	/** The reviews. */
	private ArrayList<FoodReview> reviews;
	
	/** The added by. */
	private String addedBy;
	
	/** The last edited by. */
	private String lastEditedBy;

	/**
	 * Instantiates a new food detail.
	 *
	 * @param id the id
	 */
	public FoodDetail(int id)
	{
		super(id, true);
	}

	/**
	 * Instantiates a new food detail.
	 *
	 * @param id the id
	 * @param exists the exists
	 * @param name the name
	 * @param category the category
	 * @param categoryId the category id
	 * @param vendor the vendor
	 * @param vendorId the vendor id
	 * @param gtin the gtin
	 * @param imageIds the image ids
	 * @param description the description
	 * @param defaultUseBy the default use by
	 * @param amountType the amount type
	 * @param amount the amount
	 * @param usualPrice the usual price
	 * @param reviews the reviews
	 * @param addedBy the added by
	 * @param lastEditedBy the last edited by
	 */
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

	/**
	 * From json.
	 *
	 * @param obj the obj
	 * @return the food detail
	 * @throws JSONException the JSON exception
	 */
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

		JSONArray reviewArray;

		if(obj.isNull("reviews"))
		{
			reviewArray = new JSONArray();
		}
		else
		{
			reviewArray = obj.getJSONArray("reviews");
		}

		fd.reviews = new ArrayList<>(reviewArray.length());

		for (int i = 0; i < reviewArray.length(); i++)
		{
			fd.reviews.add(FoodReview.fromJson(reviewArray.getJSONObject(i)));
		}

		fd.addedBy = obj.getString("addedBy");
		fd.lastEditedBy = obj.getString("lastEditedBy");

		return fd;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.model.AbstractEntity#makeJSONObject()
	 */
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

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the vendor.
	 *
	 * @return the vendor
	 */
	public String getVendor()
	{
		return vendor;
	}

	/**
	 * Gets the vendor id.
	 *
	 * @return the vendor id
	 */
	public int getVendorId()
	{
		return vendorId;
	}

	/**
	 * Gets the gtin.
	 *
	 * @return the gtin
	 */
	public String getGtin()
	{
		return gtin;
	}

	/**
	 * Gets the image ids.
	 *
	 * @return the image ids
	 */
	public ArrayList<String> getImageIds()
	{
		return imageIds;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Gets the default use by.
	 *
	 * @return the default use by
	 */
	public long getDefaultUseBy()
	{
		return defaultUseBy;
	}

	/**
	 * Gets the amount type.
	 *
	 * @return the amount type
	 */
	public int getAmountType()
	{
		return amountType;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public float getAmount()
	{
		return amount;
	}

	/**
	 * Gets the usual price.
	 *
	 * @return the usual price
	 */
	public float getUsualPrice()
	{
		return usualPrice;
	}

	/**
	 * Gets the reviews.
	 *
	 * @return the reviews
	 */
	public ArrayList<FoodReview> getReviews()
	{
		return reviews;
	}

	/**
	 * Gets the added by.
	 *
	 * @return the added by
	 */
	public String getAddedBy()
	{
		return addedBy;
	}

	/**
	 * Gets the last edited by.
	 *
	 * @return the last edited by
	 */
	public String getLastEditedBy()
	{
		return lastEditedBy;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public int getCategoryId()
	{
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
}
