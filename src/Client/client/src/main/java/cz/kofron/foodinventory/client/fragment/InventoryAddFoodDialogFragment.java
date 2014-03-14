package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodListActivity;
import cz.kofron.foodinventory.client.activity.MainActivity;

/**
 * Created by kofee on 3/5/14.
 */
public class InventoryAddFoodDialogFragment extends DialogFragment
{

	private View view;
	private Dialog dialog;
	private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
		}
	};
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
					RadioGroup rg = (RadioGroup) view.findViewById(R.id.radio_group);
					dialog.cancel();
					Intent intent;
					switch (rg.getCheckedRadioButtonId())
					{
						case R.id.radio_scan_barcode:
							((MainActivity) getActivity()).onNavigationDrawerItemSelected(3);
							break;
						case R.id.radio_search_food:
							intent = new Intent(getActivity(), FoodListActivity.class);
							getActivity().startActivity(intent);
							break;
					}
				}
			});
		}
	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.inventory_add_dialog, null);
		this.view = view;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle(R.string.title_inventory_add_food);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, onCancelListener);
		builder.setPositiveButton(R.string.ok, onOkListener);

		this.dialog = builder.create();

		return dialog;
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "invetory_add_food_dialog");
	}
}
