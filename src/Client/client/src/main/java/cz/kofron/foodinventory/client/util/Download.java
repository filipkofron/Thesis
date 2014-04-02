package cz.kofron.foodinventory.client.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kofee on 26.3.14.
 */
public class Download
{
	private final static int MAX_DOWNLOADS = 4;
	private static Integer downloads = new Integer(0);

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
