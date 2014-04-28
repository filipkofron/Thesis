package cz.kofron.foodinventory.client.model;

import android.graphics.Bitmap;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 26.3.14.
 */
public class PODResult
{
	
	/** The gtin. */
	private String gtin;
	
	/** The image. */
	private Bitmap image = null;
	
	/** The name. */
	private String name = "";
	
	/** The vendor. */
	private String vendor = "";

	/**
	 * Instantiates a new POD result.
	 *
	 * @param gtin the gtin
	 */
	public PODResult(String gtin)
	{
		this.gtin = gtin;
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
	 * Sets the gtin.
	 *
	 * @param gtin the new gtin
	 */
	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Bitmap getImage()
	{
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image the new image
	 */
	public void setImage(Bitmap image)
	{
		this.image = image;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * Sets the vendor.
	 *
	 * @param vendor the new vendor
	 */
	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "PODResult{" +
				"gtin='" + gtin + '\'' +
				", image=" + image +
				", name='" + name + '\'' +
				", vendor='" + vendor + '\'' +
				'}';
	}
}
