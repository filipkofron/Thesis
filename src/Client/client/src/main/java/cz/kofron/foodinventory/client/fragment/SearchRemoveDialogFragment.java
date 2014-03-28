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
import android.widget.ListView;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodEditActivity;
import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.LoadInventoryTask;
import cz.kofron.foodinventory.client.task.param.LoadInventoryParam;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

/**
 * Created by kofee on 18.3.14.
 */
public class SearchRemoveDialogFragment extends DialogFragment implements ReloadCallback
{
	private View view;
	private Dialog dialog;
	private String gtin;
	private Runnable onDone;
	private InventoryListAdapter adapter;

	public SearchRemoveDialogFragment(String gtin, Runnable onDone)
	{
		this.gtin = gtin;
		this.onDone = onDone;
	}

	private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener()
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
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.remove_food_dialog, null);
		ListView lv = (ListView) view.findViewById(R.id.list_view);

		adapter = new InventoryListAdapter(getActivity(), this);
		lv.setAdapter(adapter);

		this.view = view;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle(R.string.title_remove_food);
		builder.setCancelable(true);
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

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
		{
			@Override
			public void onDismiss(DialogInterface dialogInterface)
			{
				if(onDone != null)
				{
					onDone.run();
				}
			}
		});

		update();

		return dialog;
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "search_remove_food_dialog");
	}

	@Override
	public void update()
	{
		LoadInventoryTask lit = new LoadInventoryTask();
		lit.execute(new LoadInventoryParam("", gtin, adapter, onSuccess, onFail));
	}

	private Runnable onSuccess = new Runnable()
	{
		@Override
		public void run()
		{

		}
	};

	private Runnable onFail = new Runnable()
	{
		@Override
		public void run()
		{
			NetworkInstance.connector.forceCheck();
			NetworkErrorToast.showError(getActivity());
		}
	};
}
