package cz.kofron.foodinventory.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.InventoryFoodDialogFragment;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.model.InventoryItem;
import cz.kofron.foodinventory.client.task.DeleteInventoryTask;
import cz.kofron.foodinventory.client.task.param.DeleteInventoryParam;
import cz.kofron.foodinventory.client.util.SimpleDate;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListAdapter extends ArrayAdapter
{
	private Context context;
	private ArrayList<InventoryItem> items;
	private ReloadCallback reloadCallback;

	public InventoryListAdapter(Context context, ReloadCallback fragment)
	{
		super(context, R.layout.inventory_list_item);
		items = new ArrayList<>();
		this.context = context;
		this.reloadCallback = fragment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		InventoryItem item = items.get(position);

		View view = LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
		TextView foodName = (TextView) view.findViewById(R.id.inventory_list_item_food_name);
		TextView date = (TextView) view.findViewById(R.id.date_view);
		ImageButton deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
		View card = view.findViewById(R.id.inventory_list_item_card);

		int d = 255;
		if ((position & 1) == 1)
		{
			d = 240;
		}
		card.setBackgroundColor(Color.argb(255, d, d, d));
		card.setOnClickListener(new OnItemClick(item));

		deleteButton.setOnClickListener(new OnItemDelete(item));

		foodName.setText(item.getFoodName());
		date.setText(SimpleDate.longToDate(item.getUseBy()));

		return view;
	}

	public void updateContent(ArrayList<InventoryItem> items)
	{
		clear();
		this.items = items;

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
				}
			};

			Runnable fail = new Runnable()
			{
				@Override
				public void run()
				{

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
