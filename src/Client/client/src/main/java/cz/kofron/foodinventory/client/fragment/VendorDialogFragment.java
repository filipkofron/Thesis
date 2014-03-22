package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.model.Vendor;

/**
 * Created by kofee on 3/4/14.
 */
public class VendorDialogFragment extends DialogFragment
{
	private final ArrayList<Vendor> vendors;

	public VendorDialogFragment(ArrayList<Vendor> vendors)
	{
		this.vendors = vendors;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.pick_vendor);
		String [] vendorStrings = new String[vendors.size()];
		int i = 0;
		for(Vendor vendor : vendors)
		{
			vendorStrings[i] = vendor.getName();
			i++;
		}

		builder.setItems(vendorStrings, new DialogInterface.OnClickListener()
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
}
