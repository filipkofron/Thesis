package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 3/5/14.
 */
public class LoginDialogFragment extends DialogFragment
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
					System.out.println("Would login: " + LoginDialogFragment.this);
				}
			});
		}
	};

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.login_dialog, null);
		this.view = view;

		Button newAccountButton = (Button) view.findViewById(R.id.new_account);
		newAccountButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				getActivity().runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						new NewAccountDialogFragment().show(getActivity());
					}
				});
			}
		});

		Button lostPasswordButton = (Button) view.findViewById(R.id.lost_password);
		lostPasswordButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				getActivity().runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						new LostPasswordDialogFragment().show(getActivity());
					}
				});
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("Login");
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, onCancelListener);
		builder.setPositiveButton(R.string.ok, onOkListener);

		this.dialog = builder.create();

		return dialog;
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "login_dialog");
	}
}
