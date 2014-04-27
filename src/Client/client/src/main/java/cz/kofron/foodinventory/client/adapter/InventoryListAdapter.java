package cz.kofron.foodinventory.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.InventoryFoodDialogFragment;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.model.InventoryItem;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.DeleteInventoryTask;
import cz.kofron.foodinventory.client.task.LoadImageTask;
import cz.kofron.foodinventory.client.task.param.DeleteInventoryParam;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;
import cz.kofron.foodinventory.client.util.DateUtil;
import cz.kofron.foodinventory.client.util.Download;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;
import cz.kofron.foodinventory.client.util.SimpleDate;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListAdapter extends ArrayAdapter
{
	private Context context;
	private ArrayList<InventoryItem> items;
	private ReloadCallback reloadCallback;
	private DeleteInventoryListener deleteInventoryListener;

	public InventoryListAdapter(Context context, ReloadCallback fragment, DeleteInventoryListener deleteInventoryListener)
	{
		super(context, R.layout.inventory_list_item);
		items = new ArrayList<>();
		this.context = context;
		this.reloadCallback = fragment;
		this.deleteInventoryListener = deleteInventoryListener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		InventoryItem item = items.get(position);

		View view = LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
		TextView foodName = (TextView) view.findViewById(R.id.inventory_list_item_food_name);
		TextView date = (TextView) view.findViewById(R.id.date_view);
		TextView daysText = (TextView) view.findViewById(R.id.days_text);
		ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
		View card = view.findViewById(R.id.inventory_list_item_card);

		View frameLayout = view.findViewById(R.id.frame_layout);
		View colorLine = view.findViewById(R.id.color_line);

		ImageView image = (ImageView) card.findViewById(R.id.image);
		image.setImageResource(R.drawable.loading);

		LoadImageParam lip = new LoadImageParam(context, String.valueOf(item.getImageId()), image, null);
		LoadImageTask lit = new LoadImageTask(lip);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			lit.executeOnExecutor(Download.getExecutorService());
		}
		else
		{
			lit.execute();
		}

		int d = 255;
		if ((position & 1) == 1)
		{
			d = 240;
		}
		card.setBackgroundColor(Color.argb(255, d, d, d));
		card.setOnClickListener(new OnItemClick(item));

		deleteButton.setOnClickListener(new OnItemDelete(item));

		foodName.setText(item.getFoodName());

		long msToEnd = item.getUseBy() - System.currentTimeMillis() + 2 * DateUtil.ROUGH_MS_PER_DAY;
		int days = (int) (msToEnd / DateUtil.ROUGH_MS_PER_DAY);
		int months = (int) (msToEnd / DateUtil.ROUGH_MS_PER_MONTH);
		int years = (int) (msToEnd / DateUtil.ROUGH_MS_PER_YEAR);

		int lineRed = makeLineRed(days);
		colorLine.setBackgroundColor(Color.argb(255, lineRed, 255 - lineRed, 0));

		if(days > 0)
		{
			String dateText;
			frameLayout.setBackgroundColor(Color.argb(255, d, d, d));
			daysText.setText(getCorrectText(days, months, years));
			dateText = "" + days;
			if(months > 0)
			{
				dateText = "" + months;
			}
			if(years > 0)
			{
				dateText = "" + years;
			}
			date.setText(dateText);
		}
		else
		{
			date.setTextColor(Color.argb(255, 255, 255, 0));
			frameLayout.setBackgroundColor(Color.argb(255, 255, 0, 0));
			daysText.setText("");
			date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
			date.setText(R.string.expired_msg);
		}

		return view;
	}

	private int makeLineRed(int days)
	{
		int red = (days * (-255 / 5) + 255);
		red = Math.min(255, red);
		red = Math.max(0, red);

		return red;
	}

	private int getCorrectText(int days, int months, int years)
	{
		if(years > 0)
		{
			if(years == 1)
			{
				return R.string.days_text_year;
			}
			else
			{
				return R.string.days_text_years;
			}
		}

		if(months > 0)
		{
			if(months == 1)
			{
				return R.string.days_text_month;
			}
			else
			{
				return R.string.days_text_months;
			}
		}

		if(days == 1)
		{
			return R.string.days_text_day;
		}

		return R.string.days_text_days;
	}

	public void updateContent(ArrayList<InventoryItem> items)
	{
		clear();
		this.items = items;

		Collections.sort(items, new Comparator<InventoryItem>()
		{
			@Override
			public int compare(InventoryItem inventoryItem, InventoryItem inventoryItem2)
			{
				long res = inventoryItem.getUseBy() - inventoryItem2.getUseBy();
				if(res < 0)
				{
					return -1;
				}
				if(res > 0)
				{
					return 1;
				}
				return 0;
			}
		});

		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return items.size();
	}

	private class OnItemDelete implements View.OnClickListener
	{
		private InventoryItem item;

		private OnItemDelete(InventoryItem item)
		{
			this.item = item;
		}

		@Override
		public void onClick(View view)
		{
			Runnable success = new Runnable()
			{
				@Override
				public void run()
				{
					if(reloadCallback != null)
					{
						reloadCallback.update();
					}
					if(deleteInventoryListener != null)
					{
						deleteInventoryListener.onDeleteItem(item, reloadCallback);
					}
				}
			};

			Runnable fail = new Runnable()
			{
				@Override
				public void run()
				{
					NetworkInstance.connector.forceCheck();
					NetworkErrorToast.showError(context);
				}
			};

			DeleteInventoryParam dip = new DeleteInventoryParam(success, fail, OnItemDelete.this.item.getId());
			DeleteInventoryTask dit = new DeleteInventoryTask(context, dip);
			dit.execute();
		}
	}

	private class OnItemClick implements View.OnClickListener
	{
		private InventoryItem item;

		private OnItemClick(InventoryItem item)
		{
			this.item = item;
		}

		@Override
		public void onClick(View view)
		{
			FoodItem foodItem = new FoodItem(item.getFoodId(), true, item.getUseBy() - System.currentTimeMillis(), "", item.getFoodName(), "", "");
			new InventoryFoodDialogFragment(R.string.title_inventory_food, foodItem, item.getId(), true, reloadCallback, null).show((FragmentActivity) context);
		}
	}
}
