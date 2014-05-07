package cz.kofron.foodinventory.client.task.param;

import android.graphics.Bitmap;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class EditImagesParam
{
	
	/** The ids to remove. */
	public ArrayList<String> idsToRemove;
	
	/** The bitmap to upload. */
	public ArrayList<Bitmap> bitmapToUpload;

	/**
	 * Instantiates a new edits the images param.
	 *
	 * @param idsToRemove the ids to remove
	 * @param bitmapToUpload the bitmap to upload
	 */
	public EditImagesParam(ArrayList<String> idsToRemove, ArrayList<Bitmap> bitmapToUpload)
	{
		this.idsToRemove = idsToRemove;
		this.bitmapToUpload = bitmapToUpload;
	}
}
