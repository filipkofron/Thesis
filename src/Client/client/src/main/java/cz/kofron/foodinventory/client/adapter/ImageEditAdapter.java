package cz.kofron.foodinventory.client.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeSet;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.ImageZoomDialogFragment;
import cz.kofron.foodinventory.client.task.LoadImageTask;
import cz.kofron.foodinventory.client.task.param.EditImagesParam;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;
import cz.kofron.foodinventory.client.util.Download;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class ImageEditAdapter
{
	
	/** The layout. */
	private LinearLayout layout;
	
	/** The activity. */
	private Activity activity;
	
	/** The deleted ids. */
	private TreeSet<String> deletedIds = new TreeSet<>();
	
	/**
	 * The Class Pair.
	 */
	private class Pair
	{
		
		/** The bitmap. */
		public Bitmap bitmap;
		
		/** The view. */
		public View view;

		/**
		 * Instantiates a new pair.
		 *
		 * @param bitmap the bitmap
		 * @param view the view
		 */
		private Pair(Bitmap bitmap, View view)
		{
			this.bitmap = bitmap;
			this.view = view;
		}
	}
	
	/** The new bitmaps. */
	private ArrayList<Pair> newBitmaps = new ArrayList<>();
	
	/** The Constant REQUEST_CODE. */
	public final static int REQUEST_CODE = 13463;

	/**
	 * Instantiates a new image edit adapter.
	 *
	 * @param layout the layout
	 * @param activity the activity
	 */
	public ImageEditAdapter(LinearLayout layout, Activity activity)
	{
		this.layout = layout;
		this.activity = activity;
	}

	/**
	 * Adds the bitmap.
	 *
	 * @param bitmap the bitmap
	 */
	private void addBitmap(Bitmap bitmap)
	{

	}

	/**
	 * Populate add new.
	 */
	private void populateAddNew()
	{
		View newImage = LayoutInflater.from(activity).inflate(R.layout.image_edit_new_item, null);

		Button button = (Button) newImage.findViewById(R.id.add_new_button);
		button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent imageIntent = new Intent();
				imageIntent.setType("image/*");
				imageIntent.setAction(Intent.ACTION_GET_CONTENT);
				imageIntent.addCategory(Intent.CATEGORY_OPENABLE);

				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

				Intent chooser = new Intent(Intent.ACTION_CHOOSER);
				chooser.putExtra(Intent.EXTRA_INTENT, imageIntent);
				chooser.putExtra(Intent.EXTRA_TITLE, activity.getString(R.string.title_select_image));

				Intent[] intentArray =  {cameraIntent};
				chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

				activity.startActivityForResult(chooser, REQUEST_CODE);
			}
		});

		layout.addView(newImage);
	}

	/**
	 * Populate image.
	 *
	 * @param bitmap the bitmap
	 */
	private void populateImage(Bitmap bitmap)
	{
		final View imageLayout = LayoutInflater.from(activity).inflate(R.layout.image_edit_item, null);
		ImageView image = (ImageView) imageLayout.findViewById(R.id.image);
		Button removeButton = (Button) imageLayout.findViewById(R.id.remove_button);

		final View toRemove = imageLayout;

		removeButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				layout.removeView(toRemove);

				Pair remove = null;
				for(Pair pair : newBitmaps)
				{
					if(pair.view == toRemove)
					{
						remove = pair;
						break;
					}
				}
				if(remove != null)
				{
					newBitmaps.remove(remove);
				}

				layout.invalidate();
			}
		});
		newBitmaps.add(new Pair(bitmap, toRemove));
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

	/**
	 * Populate image.
	 *
	 * @param id the id
	 */
	private void populateImage(String id)
	{
		final View imageLayout = LayoutInflater.from(activity).inflate(R.layout.image_edit_item, null);
		ImageView image = (ImageView) imageLayout.findViewById(R.id.image);
		Button removeButton = (Button) imageLayout.findViewById(R.id.remove_button);

		final View toRemove = imageLayout;
		final String idToRemove = id;

		removeButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				layout.removeView(toRemove);

				deletedIds.add(idToRemove);

				layout.invalidate();
			}
		});
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

		LoadImageParam lip = new LoadImageParam(activity, id, image, onLoaded);
		LoadImageTask lit = new LoadImageTask(lip);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			lit.executeOnExecutor(Download.getExecutorService());
		}
		else
		{
			lit.execute();
		}

		layout.addView(imageLayout);
		layout.invalidate();
	}

	/**
	 * On image bitmap.
	 *
	 * @param bitmap the bitmap
	 */
	public void onImageBitmap(Bitmap bitmap)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int size = w * h;

		if(size < 100)
		{
			return;
		}

		int max = 200000;
		float partRatio = (float) Math.sqrt((float) max / size);

		w = (int) (w * partRatio);
		h = (int) (h * partRatio);

		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, w, h, false);
		bitmap.recycle();

		populateImage(resizedBitmap);
	}

	/**
	 * On image stream.
	 *
	 * @param is the is
	 */
	public void onImageStream(InputStream is)
	{
		onImageBitmap(BitmapFactory.decodeStream(is));
	}

	/**
	 * Populate.
	 *
	 * @param images the images
	 */
	public void populate(ArrayList<String> images)
	{
		layout.removeAllViews();
		populateAddNew();
		for(String id : images)
		{
			populateImage(id);
		}
	}

	/**
	 * Make edit param.
	 *
	 * @return the edits the images param
	 */
	public EditImagesParam makeEditParam()
	{
		ArrayList<String> deleteIds = new ArrayList<>();
		deleteIds.addAll(deletedIds);

		ArrayList<Bitmap> bitmaps = new ArrayList<>();
		for(Pair pair : newBitmaps)
		{
			bitmaps.add(pair.bitmap);
		}

		return new EditImagesParam(deleteIds, bitmaps);
	}
}
