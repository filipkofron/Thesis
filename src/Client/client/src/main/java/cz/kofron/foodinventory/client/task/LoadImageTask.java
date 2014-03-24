package cz.kofron.foodinventory.client.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.kofron.foodinventory.client.network.Connector;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;

/**
 * Created by kofee on 23.3.14.
 */
public class LoadImageTask extends AsyncTask<Object, Void, Void>
{
	private LoadImageParam param;
	private Bitmap bitmap;

	public LoadImageTask(LoadImageParam param)
	{
		this.param = param;
	}

	@Override
	protected Void doInBackground(Object... objects)
	{
		bitmap = downloadImage("http://" + Connector.SERVER_ADDR + "/img/" + param.id + ".jpg");

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

	private Bitmap downloadImage(String url)
	{
		Bitmap bmp = null;
		try
		{
			URL ulrn = new URL(url);
			HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
			InputStream is = con.getInputStream();
			bmp = BitmapFactory.decodeStream(is);
			if (bmp != null)
			{
				return bmp;
			}

		}
		catch (Exception e)
		{

		}
		return bmp;
	}

}
