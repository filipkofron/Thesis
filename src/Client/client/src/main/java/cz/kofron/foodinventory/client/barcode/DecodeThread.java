package cz.kofron.foodinventory.client.barcode;

import android.hardware.Camera;

/**
 * Created by kofee on 9.3.14.
 */
public class DecodeThread extends Thread
{
	private boolean stopped = false;
	private boolean working = false;

	private boolean rotated;
	private byte [] data;
	private int width;
	private int height;

	private Decoder decoder;

	private Camera camera;

	public DecodeThread(Camera camera, Decoder decoder)
	{
		this.camera = camera;
		this.decoder = decoder;
	}

	public void setStopped(boolean stopped)
	{
		this.stopped = stopped;
	}

	public boolean scheduleJobIfFree(byte [] data, int width, int height, boolean rotated)
	{
		if(working)
		{
			return false;
		}

		synchronized (this)
		{
			this.data = data;
			this.width = width;
			this.height = height;
			this.rotated = rotated;
			working = true;

			notify();
		}

		return true;
	}

	private void doJob()
	{
		decoder.decode(data, width, height, rotated);

		if(camera != null)
		{
			camera.addCallbackBuffer(data);
		}
	}

	@Override
	public void run()
	{
		while(!stopped)
		{
			try
			{
				synchronized (this)
				{
					wait(1000);

					if(stopped)
					{
						continue;
					}
				}

				if(working)
				{
					doJob();
					working = false;
				}
			}
			catch (InterruptedException e)
			{
			}
		}
	}
}
