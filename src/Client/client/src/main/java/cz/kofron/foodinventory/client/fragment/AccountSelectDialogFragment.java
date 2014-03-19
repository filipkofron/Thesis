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

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.MainActivity;

/**
 * Created by kofee on 19.3.14.
 */
public class AccountSelectDialogFragment extends DialogFragment
{
	private Dialog dialog;

	private Account [] getAccounts()
	{
		return AccountManager.get(getActivity()).getAccounts();
	}

	private String [] getAccountNames()
	{
		Account [] accounts = getAccounts();
		String [] names = new String[accounts.length];

		int i = 0;
		for(Account account : accounts)
		{
			names[i++] = account.name;
		}

		return names;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.title_select_account);
		builder.setCancelable(false);
		final String [] names = getAccountNames();
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

	private class OnClick implements DialogInterface.OnClickListener
	{
		final String [] names;

		private OnClick(String[] names)
		{
			this.names = names;
		}

		@Override
		public void onClick(DialogInterface dialogInterface, int pos)
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("account", MainActivity.MODE_PRIVATE).edit();
			editor.putString("username", names[pos]);
			editor.commit();
			repeat();
		}
	}

	public void repeat()
	{
		((MainActivity) getActivity()).tryConnect();
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "select_account_dialog");
	}
}
