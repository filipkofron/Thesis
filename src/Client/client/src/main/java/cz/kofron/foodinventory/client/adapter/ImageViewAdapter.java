package cz.kofron.foodinventory.client.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.ImageZoomDialogFragment;
import cz.kofron.foodinventory.client.task.LoadImageTask;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;

/**
 * Created by kofee on 24.3.14.
 */
public class ImageViewAdapter
{
	private LinearLayout layout;
	private Activity activity;

	public ImageViewAdapter(LinearLayout layout, Activity activity)
	{
		this.layout = layout;
		this.activity = activity;
	}

	private void populateImage(String id)
	{
		final View imageLayout = LayoutInflater.from(activity).inflate(R.layout.food_detail_image, null);
		ImageView image = (ImageView) imageLayout.findViewById(R.id.image);


		image.setImageResource(R.drawable.loading);

		final ImageView imageToSet = image;
		BitmapLoadedCallback onLoaded = new BitmapLoadedCallback()
		{
			@Override
			public void onBitmap(Bitmap bitmap)
			{
				final Bitmap bitmapToShow = bitmap;
				imageToSet.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						new ImageZoomDialogFragment(bitmapToShow).show((FragmentActivity) activity);
					}
				});
			}
		};

		LoadImageParam lip = new LoadImageParam(id, image, onLoaded);
		LoadImageTask lit = new LoadImageTask(lip);
		lit.execute();

		layout.addView(imageLayout);
		layout.invalidate();
	}
	public void populate(ArrayList<String> images)
	{
		layout.removeAllViews();
		for(String id : images)
		{
			populateImage(id);
		}
	}

}
