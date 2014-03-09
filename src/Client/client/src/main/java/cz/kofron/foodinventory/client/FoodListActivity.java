package cz.kofron.foodinventory.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Filip Kofron on 3/1/14.
 */
public class FoodListActivity extends ListActionBarActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		View view = getLayoutInflater().inflate(R.layout.food_list, null);

		ListView lv = (ListView) view.findViewById(android.R.id.list);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				System.out.println("onItemClick: " + this + " i: " + i + " l: " + l);
			}
		});

		lv.setAdapter(new FoodListAdapter(this));

		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.food_list, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case android.R.id.home:
				this.finish();
				return true;
			case R.id.action_add:
				Intent foodEditIntent = new Intent(this, FoodEditActivity.class);
				startActivity(foodEditIntent);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
