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
import android.widget.Button;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodDetailActivity;

/**
 * Created by kofee on 3/5/14.
 */
public class InventoryFoodDialogFragment extends DialogFragment
{

	private int title;
	private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			System.out.println("Canceled.");
		}
	};
	private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			System.out.println("Ok.");
		}
	};

	public InventoryFoodDialogFragment(int title)
	{
		this.title = title;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.inventory_food_dialog, null);

		Button detailsButton = (Button) view.findViewById(R.id.details_button);

		detailsButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
				getActivity().startActivity(intent);
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle(title);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, onCancelListener);
		builder.setPositiveButton(R.string.ok, onOkListener);

		return builder.create();
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "invetory_food_dialog");
	}
}
