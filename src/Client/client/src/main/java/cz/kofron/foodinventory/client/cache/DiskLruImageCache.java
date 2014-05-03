package cz.kofron.foodinventory.client.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cz.kofron.foodinventory.client.BuildConfig;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 28.3.14.
 */
public class DiskLruImageCache
{

	/** The m disk cache. */
	private DiskLruCache mDiskCache;
	
	/** The m compress format. */
	private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;
	
	/** The m compress quality. */
	private int mCompressQuality = 70;
	
	/** The Constant APP_VERSION. */
	private static final int APP_VERSION = 1;
	
	/** The Constant VALUE_COUNT. */
	private static final int VALUE_COUNT = 1;
	
	/** The Constant TAG. */
	private static final String TAG = "DiskLruImageCache";

	/**
	 * Instantiates a new disk lru image cache.
	 *
	 * @param context the context
	 * @param uniqueName the unique name
	 * @param diskCacheSize the disk cache size
	 * @param compressFormat the compress format
	 * @param quality the quality
	 */
	public DiskLruImageCache(Context context, String uniqueName, int diskCacheSize,
	                         Bitmap.CompressFormat compressFormat, int quality)
	{
		try
		{
			final File diskCacheDir = getDiskCacheDir(context, uniqueName);
			mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
			mCompressFormat = compressFormat;
			mCompressQuality = quality;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Write bitmap to file.
	 *
	 * @param bitmap the bitmap
	 * @param editor the editor
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException the file not found exception
	 */
	private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor)
			throws IOException, FileNotFoundException
	{
		OutputStream out = null;
		try
		{
			out = new BufferedOutputStream(editor.newOutputStream(0), Util.IO_BUFFER_SIZE);
			return bitmap.compress(mCompressFormat, mCompressQuality, out);
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
		}
	}

	/**
	 * Gets the disk cache dir.
	 *
	 * @param context the context
	 * @param uniqueName the unique name
	 * @return the disk cache dir
	 */
	private File getDiskCacheDir(Context context, String uniqueName)
	{

		// Check if media is mounted or storage is built-in, if so, try and use external cache dir
		// otherwise use internal cache dir
		final String cachePath =
				Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
						!Util.isExternalStorageRemovable() ?
						Util.getExternalCacheDir(context).getPath() :
						context.getCacheDir().getPath();

		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param data the data
	 */
	public void put(String key, Bitmap data)
	{

		DiskLruCache.Editor editor = null;
		try
		{
			editor = mDiskCache.edit(key);
			if (editor == null)
			{
				return;
			}

			if (writeBitmapToFile(data, editor))
			{
				mDiskCache.flush();
				editor.commit();
				if (BuildConfig.DEBUG)
				{
					Log.d("cache_test_DISK_", "image put on disk cache " + key);
				}
			}
			else
			{
				editor.abort();
				if (BuildConfig.DEBUG)
				{
					Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
				}
			}
		}
		catch (IOException e)
		{
			if (BuildConfig.DEBUG)
			{
				Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
			}
			try
			{
				if (editor != null)
				{
					editor.abort();
				}
			}
			catch (IOException ignored)
			{
			}
		}

	}

	/**
	 * Gets the bitmap.
	 *
	 * @param key the key
	 * @return the bitmap
	 */
	public Bitmap getBitmap(String key)
	{

		Bitmap bitmap = null;
		DiskLruCache.Snapshot snapshot = null;
		try
		{

			snapshot = mDiskCache.get(key);
			if (snapshot == null)
			{
				return null;
			}
			final InputStream in = snapshot.getInputStream(0);
			if (in != null)
			{
				final BufferedInputStream buffIn =
						new BufferedInputStream(in, Util.IO_BUFFER_SIZE);
				bitmap = BitmapFactory.decodeStream(buffIn);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (snapshot != null)
			{
				snapshot.close();
			}
		}

		if (BuildConfig.DEBUG)
		{
			Log.d("cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
		}

		return bitmap;

	}

	/**
	 * Contains key.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKey(String key)
	{

		boolean contained = false;
		DiskLruCache.Snapshot snapshot = null;
		try
		{
			snapshot = mDiskCache.get(key);
			contained = snapshot != null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (snapshot != null)
			{
				snapshot.close();
			}
		}

		return contained;

	}

	/**
	 * Clear cache.
	 */
	public void clearCache()
	{
		if (BuildConfig.DEBUG)
		{
			Log.d("cache_test_DISK_", "disk cache CLEARED");
		}
		try
		{
			mDiskCache.delete();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Gets the cache folder.
	 *
	 * @return the cache folder
	 */
	public File getCacheFolder()
	{
		return mDiskCache.getDirectory();
	}

}