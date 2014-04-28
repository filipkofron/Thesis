package cz.kofron.foodinventory.client.task.param;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.BitmapLoadedCallback;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class LoadImageParam
{
	
	/** The id. */
	public String id;
	
	/** The image view. */
	public WeakReference<ImageView> imageView;
	
	/** The callback. */
	public BitmapLoadedCallback callback;
	
	/** The context. */
	public Context context;

	/**
	 * Instantiates a new load image param.
	 *
	 * @param context the context
	 * @param id the id
	 * @param imageView the image view
	 * @param callback the callback
	 */
	public LoadImageParam(Context context, String id, ImageView imageView, BitmapLoadedCallback callback)
	{
		this.context = context;
		this.id = id;
		this.imageView = new WeakReference<>(imageView);
		this.callback = callback;
	}
}
