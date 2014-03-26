package cz.kofron.foodinventory.client.model;

import android.graphics.Bitmap;

/**
 * Created by kofee on 26.3.14.
 */
public class PODResult
{
	private String gtin;
	private Bitmap image = null;
	private String name = "";
	private String vendor = "";

	public PODResult(String gtin)
	{
		this.gtin = gtin;
	}

	public String getGtin()
	{
		return gtin;
	}

	public void setGtin(String gtin)
	{
		this.gtin = gtin;
	}

	public Bitmap getImage()
	{
		return image;
	}

	public void setImage(Bitmap image)
	{
		this.image = image;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVendor()
	{
		return vendor;
	}

	public void setVendor(String vendor)
	{
		this.vendor = vendor;
	}

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
