package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodDetailActivity;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.EditInventoryTask;
import cz.kofron.foodinventory.client.task.param.EditInventoryParam;
import cz.kofron.foodinventory.client.util.DateUtil;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

/**
 * Created by kofee on 3/5/14.
 */
public class InventoryFoodDialogFragment extends DialogFragment
{
	private FoodItem foodItem;
	private TextView foodName;
	private DatePicker datePicker;
	private TextView count;
	private Button plusButton;
	private Button minusButton;
	private int inventoryId;
	private boolean edit;
	private ReloadCallback reloadCallback;
	private Runnable onDone;

	private int title;
	private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			System.out.println("Canceled.");

			if(onDone != null)
			{
				onDone.run();
			}
		}
	};

	public class Success implements Runnable
	{
		@Override
		public void run()
		{
			if(reloadCallback != null)
			{
				reloadCallback.update();
			}
			if(onDone != null)
			{
				onDone.run();
			}
		}
	}

	public class Fail implements Runnable
	{
		@Override
		public void run()
		{
			if(onDone != null)
			{
				onDone.run();
				NetworkInstance.connector.forceCheck();
				NetworkErrorToast.showError(getActivity());
			}
		}
	}

	private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			System.out.println("Ok.");
			AsyncTask at = new EditInventoryTask(getActivity(), new EditInventoryParam(new Success(), new Fail(), !edit, inventoryId, foodItem.getId(), DateUtil.getDateFromDatePicket(datePicker).getTime(), getCount()));
			at.execute();
			if(onDone != null)
			{
				onDone.run();
			}
		}
	};

	public InventoryFoodDialogFragment(int title, FoodItem foodItem, int inventoryId, boolean edit, ReloadCallback reloadCallback, Runnable onDone)
	{
		this.title = title;
		this.foodItem = foodItem;
		this.edit = edit;
		this.inventoryId = inventoryId;
		this.reloadCallback = reloadCallback;
		this.onDone = onDone;
	}

	private int getCount()
	{
		try
		{
			return Integer.parseInt(count.getText().toString());
		}
		catch(NumberFormatException e)
		{
		}
		return 1;
	}

	private void incrementCount()
	{
		int curr = getCount();
		if(curr < 100)
		{
			curr++;
		}
		count.setText(Integer.toString(curr));
	}

	private void decrementCount()
	{
		int curr = getCount();
		if(curr > 1)
		{
			curr--;
		}
		count.setText(Integer.toString(curr));
	}

	public void setupCount()
	{
		plusButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				incrementCount();
			}
		});

		minusButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				decrementCount();
			}
		});
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.inventory_food_dialog, null);
		foodName = (TextView) view.findViewById(R.id.food_name);
		datePicker = (DatePicker) view.findViewById(R.id.date_picker);

		plusButton = (Button) view.findViewById(R.id.plus_button);
		minusButton = (Button) view.findViewById(R.id.minus_button);
		count = (TextView) view.findViewById(R.id.count);

		if(edit)
		{
			RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.item_count_layout);
			rl.removeAllViews();
			rl.invalidate();
		}
		else
		{
			setupCount();
		}

		foodName.setText(foodItem.getName());
		Date date = new Date(foodItem.getDefaultUseBy() + System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		Button detailsButton = (Button) view.findViewById(R.id.details_button);

		detailsButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
				intent.putExtra("FOOD_ID", foodItem.getId());
				getActivity().startActivity(intent);
			}
		});

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle(title);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.cancel, onCancelListener);
		builder.setPositiveButton(R.string.ok, onOkListener);

		final Dialog dialog = builder.create();

		dialog.setOnKeyListener(new Dialog.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int keyCode,
			                     KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					if(onDone != null)
					{
						onDone.run();
					}
					dialog.dismiss();
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

		return dialog;
	}

	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "invetory_food_dialog");
	}
}
