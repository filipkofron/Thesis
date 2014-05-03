package cz.kofron.foodinventory.client.barcode;

import android.hardware.Camera;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 9.3.14.
 */
public class DecodeThread extends Thread
{
	
	/** The stopped. */
	private boolean stopped = false;
	
	/** The working. */
	private boolean working = false;

	/** The rotated. */
	private boolean rotated;
	
	/** The data. */
	private byte[] data;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;

	/** The returned buffer. */
	private boolean returnedBuffer = true;

	/** The decoder. */
	private Decoder decoder;

	/** The camera. */
	private Camera camera;

	/**
	 * Instantiates a new decode thread.
	 *
	 * @param camera the camera
	 * @param decoder the decoder
	 */
	public DecodeThread(Camera camera, Decoder decoder)
	{
		this.camera = camera;
		this.decoder = decoder;
	}

	/**
	 * Sets the stopped.
	 *
	 * @param stopped the new stopped
	 */
	public void setStopped(boolean stopped)
	{
		this.stopped = stopped;
	}

	/**
	 * Schedule job if free.
	 *
	 * @param data the data
	 * @param width the width
	 * @param height the height
	 * @param rotated the rotated
	 * @return true, if successful
	 */
	public boolean scheduleJobIfFree(byte[] data, int width, int height, boolean rotated)
	{
		if (working)
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

		returnedBuffer = false;

		return true;
	}

	/**
	 * Do job.
	 */
	private void doJob()
	{
		try
		{
			decoder.decode(data, width, height, rotated);
			decoder.decode(data, width, height, !rotated);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (camera != null)
		{
			camera.addCallbackBuffer(data);
			returnedBuffer = true;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		while (!stopped)
		{
			try
			{
				synchronized (this)
				{
					wait(1000);

					if (stopped)
					{
						continue;
					}
				}

				if (working)
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

	/**
	 * Checks for returned buffer.
	 *
	 * @return true, if successful
	 */
	public boolean hasReturnedBuffer()
	{
		return returnedBuffer;
	}
}
