package cz.kofron.foodinventory.client.fragment;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.barcode.DecodeThread;
import cz.kofron.foodinventory.client.barcode.Decoder;
import cz.kofron.foodinventory.client.barcode.ResultCallback;

/**
 * Created by kofee on 3/9/14.
 */
public abstract class AbstractScanFragment extends Fragment implements SurfaceHolder.Callback, Camera.PreviewCallback
{

	private final static int RESULT_PERIOD_MS = 1500;
	private boolean lockCameraUse = false;
	private SurfaceView surfaceView;
	private Camera camera;
	private boolean previewRunning = false;
	private DecodeThread decodeThread;
	private boolean rotated;
	private long lastResult;

	public abstract void onGtin(String gtin);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
			savedInstanceState)
	{
		View view = inflater.inflate(R.layout.scan_fragment, null);

		surfaceView = (SurfaceView) view.findViewById(R.id.surface_view);
		final SurfaceHolder surfaceHolder = surfaceView.getHolder();

		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		return view;
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder)
	{
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
	{
		previewRunning = false;
		destroyCamera();

		initCamera(surfaceHolder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder)
	{
		previewRunning = false;
		destroyCamera();
	}

	private void initCamera(SurfaceHolder surfaceHolder)
	{
		if (camera != null)
		{
			try
			{
				int size = 4 * camera.getParameters().getPreviewSize().width * camera.getParameters().getPreviewSize().height;
				byte[] buffer = new byte[size];
				byte[] buffer2 = new byte[size];
				camera.addCallbackBuffer(buffer);
				camera.addCallbackBuffer(buffer2);
				camera.setPreviewCallbackWithBuffer(this);
				camera.setPreviewDisplay(surfaceHolder);
			}
			catch (IOException e)
			{
				destroyCamera();
				e.printStackTrace();
			}
			catch (RuntimeException e)
			{
				destroyCamera();
				e.printStackTrace();
			}
			previewRunning = true;
			camera.startPreview();
		}
	}

	private void destroyCamera()
	{
		if (camera != null)
		{
			previewRunning = false;
			camera.setPreviewCallback(null);
			camera.stopPreview();
		}
	}

	private void turnOffCamera()
	{
		if (camera != null)
		{
			camera.release();
			camera = null;
		}
	}

	private void turnOnCamera()
	{
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		int cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++)
		{
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
			{
				try
				{
					System.out.println("Opening cam back " + camIdx);
					camera = Camera.open(camIdx);
					break;
				}
				catch (RuntimeException e)
				{
					camera = null;
					e.printStackTrace();
				}
			}
		}

		if (camera == null)
		{
			for (int camIdx = 0; camIdx < cameraCount; camIdx++)
			{
				Camera.getCameraInfo(camIdx, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
				{
					try
					{
						System.out.println("Opening cam front " + camIdx);
						camera = Camera.open(camIdx);
						break;
					}
					catch (RuntimeException e)
					{
						camera = null;
						e.printStackTrace();
					}
				}
			}
		}

		if (camera == null)
		{
			try
			{
				System.out.println("Opening default camera.");
				camera = Camera.open();
			}
			catch (RuntimeException e)
			{
				camera = null;
				e.printStackTrace();
			}
		}

		if (camera != null)
		{
			Camera.Parameters parameters = camera.getParameters();

			List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
			Camera.Size targetSize = null;
			for (Camera.Size size : previewSizes)
			{
				if (targetSize == null)
				{
					targetSize = size;
				}
				else
				{
					if (size.width * size.height > targetSize.width * targetSize.height)
					{
						targetSize = size;
					}
				}
			}
			if (targetSize != null)
			{
				int width = targetSize.width;
				int height = targetSize.height;

				Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

				switch (display.getRotation())
				{
					case Surface.ROTATION_0:
						parameters.setPreviewSize(width, height);
						camera.setDisplayOrientation(90);
						rotated = true;
						break;
					case Surface.ROTATION_90:
						parameters.setPreviewSize(width, height);
						rotated = false;
						break;
					case Surface.ROTATION_180:
						parameters.setPreviewSize(width, height);
						rotated = true;
						break;
					case Surface.ROTATION_270:
						parameters.setPreviewSize(width, height);
						camera.setDisplayOrientation(180);
						rotated = false;
						break;
				}

				parameters.setPreviewFormat(ImageFormat.NV21);
				camera.setParameters(parameters);

				try
				{
					parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
					camera.setParameters(parameters);
				}
				catch (RuntimeException e)
				{
					e.printStackTrace();
					try
					{
						parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
						camera.setParameters(parameters);
					}
					catch (RuntimeException e2)
					{
						e2.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public synchronized void onPreviewFrame(byte[] bytes, Camera camera)
	{
		if (!previewRunning)
		{
			camera.addCallbackBuffer(bytes);
			return;
		}
		Camera.Parameters params = camera.getParameters();

		DecodeThread dt = decodeThread;

		if (dt != null)
		{
			if (!dt.hasReturnedBuffer())
			{
				camera.addCallbackBuffer(bytes);
				return;
			}
		}
		else
		{
			camera.addCallbackBuffer(bytes);
			return;
		}

		if (lockCameraUse || (System.currentTimeMillis() - lastResult) < RESULT_PERIOD_MS)
		{
			camera.addCallbackBuffer(bytes);
			return;
		}
		lockCameraUse = true;

		if (dt != null)
		{
			if (!dt.scheduleJobIfFree(bytes, params.getPreviewSize().width, params.getPreviewSize().height, rotated))
			{
				camera.addCallbackBuffer(bytes);
			}
		}

		lockCameraUse = false;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		turnOnCamera();
		decodeThread = new DecodeThread(camera, new Decoder(new ResultCallback()
		{
			@Override
			public void presentResult(String result)
			{
				lastResult = System.currentTimeMillis();
				System.out.println("Presenting result: " + result);

				final String res = result;
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						setResult(res);
						try
						{
							Thread.sleep(RESULT_PERIOD_MS - 100);
						}
						catch (InterruptedException e)
						{
						}
						setResult("none");
					}
				}).start();
			}
		}));
		decodeThread.start();
	}

	public void setResult(final String text)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				onGtin(text);
			}
		});
	}

	@Override
	public void onPause()
	{
		super.onPause();
		previewRunning = false;
		DecodeThread dt = decodeThread;
		decodeThread = null;

		if (dt != null)
		{
			dt.setStopped(true);
		}
		turnOffCamera();
	}
}
