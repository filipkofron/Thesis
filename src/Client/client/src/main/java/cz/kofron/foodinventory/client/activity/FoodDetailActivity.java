package cz.kofron.foodinventory.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 3/3/14.
 */
public class FoodDetailActivity extends ActionBarActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		View view = LayoutInflater.from(this).inflate(R.layout.food_detail, null);

		LinearLayout imageList = (LinearLayout) view.findViewById(R.id.imageList);

		for (int i = 0; i < 10; i++)
		{
			View imageLayout = LayoutInflater.from(this).inflate(R.layout.food_detail_image, null);

			imageList.addView(imageLayout);
		}

		setContentView(view);


		setTitle(R.string.food_detail_title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tv = (TextView) view.findViewById(R.id.description_text);
		tv.setMovementMethod(new ScrollingMovementMethod());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		int menuId = R.menu.food_detail;
		getMenuInflater().inflate(menuId, menu);
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
			case R.id.action_edit:
				Intent foodEditIntent = new Intent(this, FoodEditActivity.class);
				startActivity(foodEditIntent);
				return true;
		}

		return false;
	}
}
