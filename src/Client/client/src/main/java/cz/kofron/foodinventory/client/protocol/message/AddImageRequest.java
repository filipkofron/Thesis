package cz.kofron.foodinventory.client.protocol.message;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class AddImageRequest extends Message
{
	
	/** The image data. */
	private String imageData;
	
	/** The food id. */
	private int foodId;

	/**
	 * Make image data.
	 *
	 * @param bitmap the bitmap
	 * @return the string
	 */
	private final String makeImageData(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
		byte [] bytes = baos.toByteArray();
		return Base64.encodeToString(bytes, Base64.NO_WRAP);
	}

	/**
	 * Instantiates a new adds the image request.
	 */
	public AddImageRequest()
	{
	}

	/**
	 * Instantiates a new adds the image request.
	 *
	 * @param bitmap the bitmap
	 * @param foodId the food id
	 */
	public AddImageRequest(Bitmap bitmap, int foodId)
	{
		this.imageData = makeImageData(bitmap);
		this.foodId = foodId;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#getHeader()
	 */
	@Override
	public String getHeader()
	{
		return "AddImageRequest";
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#newMessage()
	 */
	@Override
	public Message newMessage()
	{
		return new AddImageRequest();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#dejsonizeContent(org.json.JSONObject)
	 */
	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		imageData = obj.getString("image");
		foodId = obj.getInt("foodId");
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.protocol.message.Message#jsonizeContent()
	 */
	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("image", imageData);
		obj.put("foodId", foodId);
		return obj;
	}
}
