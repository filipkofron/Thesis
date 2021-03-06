package cz.kofron.foodinventory.client.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.barcode.DecodeThread;
import cz.kofron.foodinventory.client.barcode.Decoder;
import cz.kofron.foodinventory.client.barcode.ResultCallback;
import cz.kofron.foodinventory.client.preference.Preferences;
import cz.kofron.foodinventory.client.view.SuspendableSurfaceView;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/9/14.
 */
public abstract class AbstractScanFragment extends Fragment implements SurfaceHolder.Callback, Camera.PreviewCallback
{

	/** The Constant RESULT_PERIOD_MS. */
	private final static int RESULT_PERIOD_MS = 1500;
	
	/** The Constant MAX_AREA. */
	private final static int MAX_AREA = 1000 * 1000;
	
	/** The lock camera use. */
	private boolean lockCameraUse = false;
	
	/** The surface view. */
	private SuspendableSurfaceView surfaceView;
	
	/** The cont. */
	private ViewGroup cont;
	
	/** The camera. */
	private Camera camera;
	
	/** The preview running. */
	private boolean previewRunning = false;
	
	/** The decode thread. */
	private DecodeThread decodeThread;
	
	/** The rotated. */
	private boolean rotated;
	
	/** The last result. */
	private long lastResult;

	/** The focus interval ms. */
	private int FOCUS_INTERVAL_MS = 2000;
	
	/** The focus callback. */
	private Camera.AutoFocusCallback focusCallback;
	
	/** The last focus. */
	private long lastFocus = 0;

	/**
	 * On gtin.
	 *
	 * @param gtin the gtin
	 */
	public abstract void onGtin(String gtin);

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
			savedInstanceState)
	{
		View view = inflater.inflate(R.layout.scan_fragment, null);

		cont = (ViewGroup) view.findViewById(R.id.container);

		View content = view.findViewById(R.id.content);


		initializeContent(content);

		return view;
	}

	/**
	 * Initialize content.
	 *
	 * @param content the content
	 */
	private void initializeContent(View content)
	{
		surfaceView = (SuspendableSurfaceView) content.findViewById(R.id.surface_view);
		initializeSurfaceView();

		final ImageButton cameraSwapButton = (ImageButton) content.findViewById(R.id.camera_swap_button);
		cameraSwapButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				boolean prev = Preferences.getPreferences(AbstractScanFragment.this.getActivity()).getBoolean("camera_back", true);
				SharedPreferences.Editor editor = Preferences.getPreferences(AbstractScanFragment.this.getActivity()).edit();
				editor.putBoolean("camera_back", !prev);
				editor.commit();


				stopCameraScan();
				startCameraScan();
				recreateView();
			}
		});

		final EditText editText = (EditText) content.findViewById(R.id.gtin);

		Button button = (Button) content.findViewById(R.id.search_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Editable text = editText.getText();
				if(text != null)
				{
					onManualSearch(text.toString());
				}
			}
		});
	}

	/**
	 * Recreate view.
	 */
	private void recreateView()
	{
		LayoutInflater li = LayoutInflater.from(getActivity());
		ViewGroup viewGroup = (ViewGroup) li.inflate(R.layout.scan_fragment, null);

		View content = viewGroup.findViewById(R.id.content);
		viewGroup.removeView(content);

		cont.removeAllViews();

		initializeContent(content);

		RelativeLayout.LayoutParams layoutParams =
				new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
						ViewGroup.LayoutParams.FILL_PARENT);

		cont.addView(content, layoutParams);
	}

	/**
	 * Initialize surface view.
	 */
	private void initializeSurfaceView()
	{
		surfaceView.setKeepScreenOn(true);
		final SurfaceHolder surfaceHolder = surfaceView.getHolder();

		surfaceHolder.removeCallback(this);

		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * On manual search.
	 *
	 * @param text the text
	 */
	public void onManualSearch(String text)
	{
		if(text == null)
		{
			return;
		}
		text = text.trim();
		if(text.length() < 1)
		{
			return;
		}
		setResult(text);
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder)
	{
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
	{
		previewRunning = false;
		destroyCamera();

		initCamera(surfaceHolder);
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder)
	{
		previewRunning = false;
		destroyCamera();
	}

	/**
	 * Inits the camera.
	 *
	 * @param surfaceHolder the surface holder
	 */
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

	/**
	 * Destroy camera.
	 */
	private void destroyCamera()
	{
		if (camera != null)
		{
			previewRunning = false;
			camera.setPreviewCallback(null);
			camera.stopPreview();
		}
	}

	/**
	 * Turn off camera.
	 */
	private void turnOffCamera()
	{
		if (camera != null)
		{
			camera.release();
			camera = null;
		}
	}

	/**
	 * Turn on camera.
	 */
	private void turnOnCamera()
	{
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		int cameraCount = Camera.getNumberOfCameras();
		boolean back = Preferences.getPreferences(AbstractScanFragment.this.getActivity()).getBoolean("camera_back", true);

		if(back)
		{
			for (int camIdx = 0; camIdx < cameraCount; camIdx++)
			{
				Camera.getCameraInfo(camIdx, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
				{
					try
					{
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
			for (int camIdx = 0; camIdx < cameraCount; camIdx++)
			{
				Camera.getCameraInfo(camIdx, cameraInfo);
				if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
				{
					try
					{
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
					int area = size.width * size.height;
					if(area < MAX_AREA)
					{
						if (size.width * size.height > targetSize.width * targetSize.height)
						{
							targetSize = size;
						}
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
						focusCallback = new Camera.AutoFocusCallback()
						{
							@Override
							public void onAutoFocus(boolean success, Camera camera)
							{
								if(success)
								{
									FOCUS_INTERVAL_MS = 4500;
								}
								else
								{
									FOCUS_INTERVAL_MS = 1000;
								}
							}
						};
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

	/* (non-Javadoc)
	 * @see android.hardware.Camera.PreviewCallback#onPreviewFrame(byte[], android.hardware.Camera)
	 */
	@Override
	public synchronized void onPreviewFrame(byte[] bytes, Camera camera)
	{
		if (!previewRunning)
		{
			camera.addCallbackBuffer(bytes);
			return;
		}

		if(focusCallback != null && (System.currentTimeMillis() - lastFocus) > FOCUS_INTERVAL_MS)
		{
			lastFocus = System.currentTimeMillis();
			try
			{
				camera.autoFocus(focusCallback);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

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
			try
			{
				Camera.Parameters params = camera.getParameters();
				if (!dt.scheduleJobIfFree(bytes, params.getPreviewSize().width, params.getPreviewSize().height, rotated))
				{
					camera.addCallbackBuffer(bytes);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				camera.addCallbackBuffer(bytes);
			}
		}

		lockCameraUse = false;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume()
	{
		super.onResume();
		startCameraScan();
	}

	/**
	 * Sets the result.
	 *
	 * @param text the new result
	 */
	public void setResult(final String text)
	{
		Activity activity = getActivity();
		if(activity != null)
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
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause()
	{
		super.onPause();
		stopCameraScan();
	}

	/**
	 * Start camera scan.
	 */
	public void startCameraScan()
	{
		turnOnCamera();
		decodeThread = new DecodeThread(camera, new Decoder(new ResultCallback()
		{
			@Override
			public void presentResult(String result)
			{
				lastResult = System.currentTimeMillis();

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

	/**
	 * Stop camera scan.
	 */
	public void stopCameraScan()
	{
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
