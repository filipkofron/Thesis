package cz.kofron.foodinventory.client.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 26.3.14.
 */
public class Download
{
	
	/** The Constant MAX_DOWNLOADS. */
	private final static int MAX_DOWNLOADS = 3;
	
	/** The downloads. */
	private static Integer downloads = new Integer(0);

	/** The executor service. */
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * Gets the executor service.
	 *
	 * @return the executor service
	 */
	public static ExecutorService getExecutorService()
	{
		return executorService;
	}

	/**
	 * Out.
	 */
	private static void out()
	{
		synchronized (downloads)
		{
			downloads--;
			try
			{
				downloads.notifyAll();
			}
			catch(IllegalMonitorStateException e)
			{

			}
		}
	}

	/**
	 * In.
	 */
	private static void in()
	{
		synchronized (downloads)
		{
			while(downloads > 4)
			{
				try
				{
					downloads.wait();
				}
				catch (InterruptedException e)
				{
				}
			}
			downloads++;
		}
	}

	/**
	 * Download image.
	 *
	 * @param urlStr the url str
	 * @return the bitmap
	 */
	public static Bitmap downloadImage(String urlStr)
	{
		in();
		Bitmap bmp = null;
		try
		{
			URL ulr = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) ulr.openConnection();
			InputStream is = conn.getInputStream();
			bmp = BitmapFactory.decodeStream(is);
			try
			{
				is.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out();
			return null;
		}
		out();
		return bmp;
	}

	/**
	 * Download string.
	 *
	 * @param urlStr the url str
	 * @return the string
	 */
	public static String downloadString(String urlStr)
	{
		StringBuffer res = new StringBuffer();
		try
		{
			URL url = new URL(urlStr);
			InputStreamReader isr = new InputStreamReader(url.openStream());

			while(true)
			{
				int r = isr.read();
				if(r < 0)
				{
					break;
				}
				res.append((char) r);
			}

			isr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		return res.toString();
	}
}
