package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodEditActivity;
import cz.kofron.foodinventory.client.model.PODResult;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 26.3.14.
 */
public class AddImportedFoodDialogFragment extends DialogFragment
{
	
	/** The view. */
	private View view;
	
	/** The dialog. */
	private Dialog dialog;
	
	/** The on done. */
	private Runnable onDone;
	
	/** The on added. */
	private Runnable onAdded;
	
	/** The pod result. */
	private PODResult podResult;

	/**
	 * Instantiates a new adds the imported food dialog fragment.
	 *
	 * @param onDone the on done
	 * @param onAdded the on added
	 * @param podResult the pod result
	 */
	public AddImportedFoodDialogFragment(Runnable onDone, Runnable onAdded, PODResult podResult)
	{
		this.onDone = onDone;
		this.onAdded = onAdded;
		this.podResult = podResult;
	}

	/** The on cancel listener. */
	private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			if(onDone != null)
			{
				onDone.run();
			}
		}
	};
	
	/** The on ok listener. */
	private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			getActivity().runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					FoodEditActivity.initialPodResult = podResult;
					FoodEditActivity.onSavedCallback = onAdded;
					Intent foodEditIntent = new Intent(getActivity(), FoodEditActivity.class);
					startActivity(foodEditIntent);
					if(onDone != null)
					{
						onDone.run();
					}
				}
			});
		}
	};

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.add_imported_food, null);
		this.view = view;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle(R.string.title_imported_new_food);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, onCancelListener);
		builder.setPositiveButton(R.string.ok, onOkListener);

		this.dialog = builder.create();

		dialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
			                     KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					dialog.dismiss();
					if(onDone != null)
					{
						onDone.run();
					}
				}
				return true;
			}
		});

		return dialog;
	}

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "add_imported_food_dialog");
	}
}
