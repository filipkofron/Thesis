package cz.kofron.foodinventory.client.task.param;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by kofee on 23.3.14.
 */
public class EditImagesParam
{
	public ArrayList<String> idsToRemove;
	public ArrayList<Bitmap> bitmapToUpload;

	public EditImagesParam(ArrayList<String> idsToRemove, ArrayList<Bitmap> bitmapToUpload)
	{
		this.idsToRemove = idsToRemove;
		this.bitmapToUpload = bitmapToUpload;
	}
}
