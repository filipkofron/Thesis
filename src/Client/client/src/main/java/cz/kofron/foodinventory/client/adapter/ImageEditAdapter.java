package cz.kofron.foodinventory.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.ImageZoomDialogFragment;

/**
 * Created by kofee on 23.3.14.
 */
public class ImageEditAdapter
{
	private LinearLayout layout;
	private Activity activity;
	public final static int REQUEST_CODE = 13463;

	public ImageEditAdapter(LinearLayout layout, Activity activity)
	{
		this.layout = layout;
		this.activity = activity;
	}

	private void addBitmap(Bitmap bitmap)
	{

	}

	private void populateAddNew()
	{
		View newImage = LayoutInflater.from(activity).inflate(R.layout.image_edit_new_item, null);

		Button button = (Button) newImage.findViewById(R.id.add_new_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				activity.startActivityForResult(intent, REQUEST_CODE);
			}
		});

		layout.addView(newImage);
	}

	private void populateImage(Bitmap bitmap)
	{
		View imageLayout = LayoutInflater.from(activity).inflate(R.layout.image_edit_item, null);
		ImageView image = (ImageView) imageLayout.findViewById(R.id.image);
		Button removeButton = (Button) imageLayout.findViewById(R.id.remove_button);

		final View toRemove = imageLayout;

		removeButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				layout.removeView(toRemove);

				// TODO: add removed image to some queue or remove from queue

				System.out.println("TODO: add removed image to some queue or remove from queue");
				layout.invalidate();
			}
		});
		image.setImageBitmap(bitmap);

		final Bitmap bitmapToShow = bitmap;
		image.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				new ImageZoomDialogFragment(bitmapToShow).show((FragmentActivity) activity);
			}
		});

		layout.addView(imageLayout);
		layout.invalidate();
	}

	private void populateImage(String id)
	{

	}

	public void onImageStream(InputStream is)
	{
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int size = w * h;

		if(size < 100)
		{
			return;
		}

		int max = 90000;
		float partRatio = (float) Math.sqrt((float) max / size);

		w = (int) (w * partRatio);
		h = (int) (h * partRatio);

		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
		bitmap.recycle();

		populateImage(resizedBitmap);
	}

	public void populate(ArrayList<String> image)
	{
		layout.removeAllViews();
		populateAddNew();
	}
}
