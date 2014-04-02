package cz.kofron.foodinventory.client.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import cz.kofron.foodinventory.client.cache.DiskLruImageCache;
import cz.kofron.foodinventory.client.network.Connector;
import cz.kofron.foodinventory.client.preference.Preferences;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;
import cz.kofron.foodinventory.client.util.Download;

/**
 * Created by kofee on 23.3.14.
 */
public class LoadImageTask extends AsyncTask<Object, Void, Void>
{
	private LoadImageParam param;
	private Bitmap bitmap = null;
	private static DiskLruImageCache imageCache;
	private static Object lock = new Object();
	private final static String DIR_NAME = "food_images";
	private final static int MAX_SIZE = 1024 * 1024 * 16;
	private final static int QUALITY = 90;
	private final static int MAX_BITMAPS_SIZE = 8 * 1024 * 1024;
	private static LruCache<String, Bitmap> cachedBitmaps;

	private static DiskLruImageCache getImageCache(Context context)
	{
		synchronized (lock)
		{
			if (imageCache == null)
			{
				imageCache = new DiskLruImageCache(context, DIR_NAME, MAX_SIZE, Bitmap.CompressFormat.PNG, QUALITY);
			}
		}
		return imageCache;
	}

	private static LruCache<String, Bitmap> getCache()
	{
		synchronized (lock)
		{
			if (cachedBitmaps == null)
			{
				cachedBitmaps = new LruCache<String, Bitmap>(MAX_BITMAPS_SIZE)
				{
					protected int sizeOf(String key, Bitmap value)
					{
						return value.getWidth() * value.getHeight() * 4;
					}
				};
			}
		}
		return cachedBitmaps;
	}

	public LoadImageTask(LoadImageParam param)
	{
		this.param = param;
	}

	@Override
	protected Void doInBackground(Object... objects)
	{
		synchronized (getCache())
		{
			bitmap = getCache().get(param.id);
		}

		if(bitmap == null)
		{
			synchronized (lock)
			{
				if (getImageCache(param.context).containsKey(param.id))
				{
					bitmap = imageCache.getBitmap(param.id);
				}
			}
		}
		if(bitmap == null)
		{
			if(Preferences.getPreferences(param.context).getBoolean("download_images", true))
			{
				bitmap = Download.downloadImage("http://" + Connector.SERVER_ADDR + "/img/" + param.id + ".jpg");
			}

			if(bitmap != null)
			{
				synchronized (lock)
				{
					getImageCache(param.context).put(param.id, bitmap);
				}
			}
		}

		if(bitmap != null)
		{
			synchronized (getCache())
			{
				if(getCache().get(param.id) == null)
				{
					getCache().put(param.id, bitmap);
				}
			}
		}


		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid)
	{
		ImageView iv = param.imageView.get();
		if(iv == null)
		{
			return;
		}
		if(bitmap != null)
		{
			iv.setImageBitmap(bitmap);
			if(param.callback != null)
			{
				param.callback.onBitmap(bitmap);
			}
			iv.invalidate();
		}
	}
}
