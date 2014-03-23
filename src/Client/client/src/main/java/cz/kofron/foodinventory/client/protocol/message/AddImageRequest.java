package cz.kofron.foodinventory.client.protocol.message;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by kofee on 23.3.14.
 */
public class AddImageRequest extends Message
{
	private String imageData;
	private int foodId;

	private final String makeImageData(Bitmap bitmap)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
		byte [] bytes = baos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	public AddImageRequest()
	{
	}

	public AddImageRequest(Bitmap bitmap, int foodId)
	{
		this.imageData = makeImageData(bitmap);
		this.foodId = foodId;
	}

	@Override
	public String getHeader()
	{
		return "AddImageRequest";
	}

	@Override
	public Message newMessage()
	{
		return new AddImageRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		imageData = obj.getString("image");
		foodId = obj.getInt("foodId");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("image", imageData);
		obj.put("foodId", foodId);
		return obj;
	}
}
