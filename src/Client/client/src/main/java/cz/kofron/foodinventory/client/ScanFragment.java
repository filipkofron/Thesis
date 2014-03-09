package cz.kofron.foodinventory.client;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * Created by kofee on 3/9/14.
 */
public class ScanFragment extends Fragment implements SurfaceHolder.Callback
{

	private SurfaceView surfaceView;
	private Camera camera;

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder)
	{
		initCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
	{
		if (camera != null)
		{
			try
			{
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
		}
		if(camera != null)
		{
			camera.startPreview();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder)
	{
		if(camera != null)
		{
			camera.stopPreview();
		}
		destroyCamera();
	}

	private void initCamera()
	{
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		int cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++)
		{
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
			{
				try
				{
					System.out.println("Opening camera: " + camIdx);
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
			try
			{

				System.out.println("Opening default camera ");
				camera = Camera.open();
			}
			catch (RuntimeException e)
			{
				camera = null;
				e.printStackTrace();
			}
		}
	}

	private void destroyCamera()
	{
		if (camera != null)
		{
			camera.release();
			camera = null;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.scan_fragment, null);

		surfaceView = (SurfaceView) view.findViewById(R.id.surface_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();

		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	@Override
	public void onPause()
	{
		super.onPause();
	}
}
