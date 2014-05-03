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

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/5/14.
 */
public class InventoryFoodDialogFragment extends DialogFragment
{
	
	/** The food item. */
	private FoodItem foodItem;
	
	/** The date picker. */
	private DatePicker datePicker;
	
	/** The count. */
	private TextView count;
	
	/** The plus button. */
	private Button plusButton;
	
	/** The minus button. */
	private Button minusButton;
	
	/** The inventory id. */
	private int inventoryId;
	
	/** The edit. */
	private boolean edit;
	
	/** The reload callback. */
	private ReloadCallback reloadCallback;
	
	/** The on done. */
	private Runnable onDone;

	/** The title. */
	private int title;
	
	/** The on cancel listener. */
	private DialogInterface.OnClickListener onCancelListener = new DialogInterface.OnClickListener()
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

	/**
	 * The Class Success.
	 */
	public class Success implements Runnable
	{
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
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

	/**
	 * The Class Fail.
	 */
	public class Fail implements Runnable
	{
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
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

	/** The on ok listener. */
	private DialogInterface.OnClickListener onOkListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			AsyncTask at = new EditInventoryTask(getActivity(), new EditInventoryParam(new Success(), new Fail(), !edit, inventoryId, foodItem.getId(), DateUtil.getDateFromDatePicket(datePicker).getTime(), getCount()));
			at.execute();
			if(onDone != null)
			{
				onDone.run();
			}
		}
	};

	/**
	 * Instantiates a new inventory food dialog fragment.
	 *
	 * @param title the title
	 * @param foodItem the food item
	 * @param inventoryId the inventory id
	 * @param edit the edit
	 * @param reloadCallback the reload callback
	 * @param onDone the on done
	 */
	public InventoryFoodDialogFragment(int title, FoodItem foodItem, int inventoryId, boolean edit, ReloadCallback reloadCallback, Runnable onDone)
	{
		this.title = title;
		this.foodItem = foodItem;
		this.edit = edit;
		this.inventoryId = inventoryId;
		this.reloadCallback = reloadCallback;
		this.onDone = onDone;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
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

	/**
	 * Increment count.
	 */
	private void incrementCount()
	{
		int curr = getCount();
		if(curr < 100)
		{
			curr++;
		}
		count.setText(Integer.toString(curr));
	}

	/**
	 * Decrement count.
	 */
	private void decrementCount()
	{
		int curr = getCount();
		if(curr > 1)
		{
			curr--;
		}
		count.setText(Integer.toString(curr));
	}

	/**
	 * Setup count.
	 */
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.inventory_food_dialog, null);
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

		Date date = new Date(foodItem.getDefaultUseBy() + System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

		Button detailsButton = (Button) view.findViewById(R.id.details_button);
		detailsButton.setText(foodItem.getName());

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

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		try
		{
			show(activity.getSupportFragmentManager(), "invetory_food_dialog");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
