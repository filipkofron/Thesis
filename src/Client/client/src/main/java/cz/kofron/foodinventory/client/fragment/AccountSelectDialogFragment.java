package cz.kofron.foodinventory.client.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.MainActivity;
import cz.kofron.foodinventory.client.preference.Accounts;
import cz.kofron.foodinventory.client.preference.Preferences;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 19.3.14.
 */
public class AccountSelectDialogFragment extends DialogFragment
{
	
	/** The dialog. */
	private Dialog dialog;

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.title_select_account);
		builder.setCancelable(false);
		final String [] names = Accounts.getAccountNames(getActivity());
		builder.setItems(names, new OnClick(names));

		this.dialog = builder.create();

		dialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
			                     KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					repeat();
					dialog.dismiss();
				}
				return true;
			}
		});

		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialogInterface)
			{
				repeat();
			}
		});

		Window window = dialog.getWindow();

		window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

		return dialog;
	}

	/**
	 * The Class OnClick.
	 */
	private class OnClick implements DialogInterface.OnClickListener
	{
		
		/** The names. */
		final String [] names;

		/**
		 * Instantiates a new on click.
		 *
		 * @param names the names
		 */
		private OnClick(String[] names)
		{
			this.names = names;
		}

		/* (non-Javadoc)
		 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
		 */
		@Override
		public void onClick(DialogInterface dialogInterface, int pos)
		{
			SharedPreferences.Editor editor = Preferences.getPreferences(getActivity()).edit();
			editor.putString("username", names[pos]);
			editor.commit();
			repeat();
		}
	}

	/**
	 * Repeat.
	 */
	public void repeat()
	{
		((MainActivity) getActivity()).tryConnect();
	}

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "select_account_dialog");
	}
}
