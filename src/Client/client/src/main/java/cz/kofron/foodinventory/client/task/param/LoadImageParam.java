package cz.kofron.foodinventory.client.task.param;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

import cz.kofron.foodinventory.client.adapter.BitmapLoadedCallback;

/**
 * Created by kofee on 23.3.14.
 */
public class LoadImageParam
{
	public String id;
	public WeakReference<ImageView> imageView;
	public BitmapLoadedCallback callback;

	public LoadImageParam(String id, ImageView imageView, BitmapLoadedCallback callback)
	{
		this.id = id;
		this.imageView = new WeakReference<>(imageView);
		this.callback = callback;
	}
}
