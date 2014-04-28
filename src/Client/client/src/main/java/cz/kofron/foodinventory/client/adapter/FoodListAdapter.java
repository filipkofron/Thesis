package cz.kofron.foodinventory.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.activity.FoodDetailActivity;
import cz.kofron.foodinventory.client.fragment.InventoryFoodDialogFragment;
import cz.kofron.foodinventory.client.model.FoodItem;
import cz.kofron.foodinventory.client.model.InventoryItem;
import cz.kofron.foodinventory.client.task.LoadImageTask;
import cz.kofron.foodinventory.client.task.param.LoadImageParam;
import cz.kofron.foodinventory.client.util.Download;
import cz.kofron.foodinventory.client.util.GtinUtil;

// TODO: Auto-generated Javadoc
/**
 * Created by Filip Kofron on 3/1/14.
 */
public class FoodListAdapter extends ArrayAdapter
{
	
	/** The items. */
	private ArrayList<FoodItem> items;
	
	/** The context. */
	private Context context;

	/**
	 * Instantiates a new food list adapter.
	 *
	 * @param context the context
	 */
	public FoodListAdapter(Context context)
	{
		super(context, R.layout.food_list_item);
		items = new ArrayList<>();
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false);
		FoodItem foodItem = items.get(position);

		TextView foodName = (TextView) view.findViewById(R.id.food_list_item_food_name);
		TextView foodGtin = (TextView) view.findViewById(R.id.food_list_item_food_gtin);
		TextView foodDesc = (TextView) view.findViewById(R.id.food_description);

		View card = view.findViewById(R.id.food_list_item_card);

		ImageView image = (ImageView) card.findViewById(R.id.image);
		image.setImageResource(R.drawable.loading);

		LoadImageParam lip = new LoadImageParam(context, foodItem.getImageId(), image, null);
		LoadImageTask lit = new LoadImageTask(lip);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			lit.executeOnExecutor(Download.getExecutorService());
		}
		else
		{
			lit.execute();
		}

		ImageButton ob = (ImageButton) view.findViewById(R.id.add_button);
		final int positionNow = position;
		ob.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				new InventoryFoodDialogFragment(R.string.title_inventory_add_food, items.get(positionNow), 0, false, null, null).show((FragmentActivity) context);
			}
		});

		card.setOnClickListener(new RowClickListener(position));

		int d = 255;
		if ((position & 1) == 1)
		{
			d = 240;
		}
		card.setBackgroundColor(Color.argb(255, d, d, d));

		foodName.setText(foodItem.getName());
		foodGtin.setText(GtinUtil.getReadableGtin(foodItem.getGtin()));

		String desc = foodItem.getDesc().replace("\r", "").replace("\n", "");

		if(desc.length() > 30)
		{
			desc = desc.substring(0, 30) + "..";
		}

		foodDesc.setText(desc);

		return view;
	}

	/**
	 * Update content.
	 *
	 * @param items the items
	 */
	public void updateContent(ArrayList<FoodItem> items)
	{
		clear();
		this.items = items;

		notifyDataSetChanged();
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount()
	{
		return items.size();
	}

	/**
	 * The listener interface for receiving rowClick events.
	 * The class that is interested in processing a rowClick
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addRowClickListener<code> method. When
	 * the rowClick event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see RowClickEvent
	 */
	private class RowClickListener implements View.OnClickListener
	{
		
		/** The position. */
		private int position;

		/**
		 * Instantiates a new row click listener.
		 *
		 * @param position the position
		 */
		private RowClickListener(int position)
		{
			this.position = position;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View view)
		{
			Intent intent = new Intent(context, FoodDetailActivity.class);
			intent.putExtra("FOOD_ID", items.get(position).getId());
			context.startActivity(intent);
		}
	}
}
