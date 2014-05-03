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

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/4/14.
 */
public class VendorDialogFragment extends DialogFragment
{
	
	/** The vendors. */
	private final ArrayList<Vendor> vendors;

	/**
	 * Instantiates a new vendor dialog fragment.
	 *
	 * @param vendors the vendors
	 */
	public VendorDialogFragment(ArrayList<Vendor> vendors)
	{
		this.vendors = vendors;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
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
				((VendorDialogListener) getActivity()).onVendorSelected(which);
			}
		});
		return builder.create();
	}

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "vendors");
	}

	/**
	 * The listener interface for receiving vendorDialog events.
	 * The class that is interested in processing a vendorDialog
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addVendorDialogListener<code> method. When
	 * the vendorDialog event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see VendorDialogEvent
	 */
	public static interface VendorDialogListener
	{
		
		/**
		 * On vendor selected.
		 *
		 * @param vendorId the vendor id
		 */
		public void onVendorSelected(int vendorId);
	}
}
