package cz.kofron.foodinventory.client.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cz.kofron.foodinventory.client.R;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 23.3.14.
 */
public class ImageZoomDialogFragment extends DialogFragment
{
	
	/** The bitmap. */
	private Bitmap bitmap;

	/**
	 * Instantiates a new image zoom dialog fragment.
	 *
	 * @param bitmap the bitmap
	 */
	public ImageZoomDialogFragment(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.image_zoom_dialog, null);
		view.setClickable(true);


		ImageView image = (ImageView) view.findViewById(R.id.image);
		image.setImageBitmap(bitmap);

		Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_NoTitleBar);

		final Dialog dialogToDismiss = dialog;
		view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				dialogToDismiss.dismiss();
			}
		});

		dialog.setCancelable(true);
		dialog.setContentView(view);

		return dialog;
	}

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "image_zoom_dialog");
	}
}
