package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 3/4/14.
 */
public class VendorDialogFragment extends DialogFragment
{
	private final static String[] testVendors;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.pick_vendor);
		builder.setItems(testVendors, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				System.out.println("Selected vendor at " + which);
				((VendorDialogListener) getActivity()).onVendorSelected(which);
			}
		});
		return builder.create();
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "vendors");
	}

	public static interface VendorDialogListener
	{
		public void onVendorSelected(int vendorId);
	}
	static
	{
		final int vendors = 50;
		testVendors = new String[vendors];
		for (int i = 0; i < testVendors.length; i++)
		{
			testVendors[i] = "Vendor #" + (i + 1);
		}
	}
}
