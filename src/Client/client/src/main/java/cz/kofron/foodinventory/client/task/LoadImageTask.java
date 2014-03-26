package cz.kofron.foodinventory.client.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import cz.kofron.foodinventory.client.network.Connector;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;
import cz.kofron.foodinventory.client.util.Download;

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
		bitmap = Download.downloadImage("http://" + Connector.SERVER_ADDR + "/img/" + param.id + ".jpg");

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
