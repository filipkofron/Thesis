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

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class LoadImageTask extends AsyncTask<Object, Void, Void>
{
	
	/** The param. */
	private LoadImageParam param;
	
	/** The bitmap. */
	private Bitmap bitmap = null;
	
	/** The image cache. */
	private static DiskLruImageCache imageCache;
	
	/** The lock. */
	private static Object lock = new Object();
	
	/** The Constant DIR_NAME. */
	private final static String DIR_NAME = "food_images";
	
	/** The Constant MAX_SIZE. */
	private final static int MAX_SIZE = 1024 * 1024 * 16;
	
	/** The Constant QUALITY. */
	private final static int QUALITY = 90;
	
	/** The Constant MAX_BITMAPS_SIZE. */
	private final static int MAX_BITMAPS_SIZE = 8 * 1024 * 1024;
	
	/** The cached bitmaps. */
	private static LruCache<String, Bitmap> cachedBitmaps;

	/**
	 * Gets the image cache.
	 *
	 * @param context the context
	 * @return the image cache
	 */
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

	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
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

	/**
	 * Instantiates a new load image task.
	 *
	 * @param param the param
	 */
	public LoadImageTask(LoadImageParam param)
	{
		this.param = param;
		if (!Preferences.getPreferences(param.context).getBoolean("download_images", true))
		{
			param.id = "0";
		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected Void doInBackground(Object... objects)
	{
		synchronized (getCache())
		{
			bitmap = getCache().get(param.id);
		}


		if (bitmap == null)
		{
			synchronized (lock)
			{
				try
				{
					if (getImageCache(param.context).containsKey(param.id))
					{
						bitmap = imageCache.getBitmap(param.id);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		if (bitmap == null)
		{
			if (Preferences.getPreferences(param.context).getBoolean("download_images", true) || param.id.equals("0"))
			{
				bitmap = Download.downloadImage("http://" + Connector.SERVER_ADDR + "/img/" + param.id + ".jpg");
			}

			if (bitmap != null)
			{
				try
				{
					synchronized (lock)
					{
						getImageCache(param.context).put(param.id, bitmap);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		if (bitmap != null)
		{
			synchronized (getCache())
			{
				if (getCache().get(param.id) == null)
				{
					getCache().put(param.id, bitmap);
				}
			}
		}


		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void aVoid)
	{
		ImageView iv = param.imageView.get();
		if (iv == null)
		{
			return;
		}
		if (bitmap != null)
		{
			iv.setImageBitmap(bitmap);
			if (param.callback != null)
			{
				param.callback.onBitmap(bitmap);
			}
			iv.invalidate();
		}
	}
}
