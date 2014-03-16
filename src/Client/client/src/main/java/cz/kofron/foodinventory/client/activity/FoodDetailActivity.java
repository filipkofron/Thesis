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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.task.LoadFoodDetailTask;
import cz.kofron.foodinventory.client.task.param.LoadFoodDetailParam;
import cz.kofron.foodinventory.client.util.GtinUtil;

/**
 * Created by kofee on 3/3/14.
 */
public class FoodDetailActivity extends ActionBarActivity
{
	private int foodId;
	private FoodDetail food;
	private ProgressBar progressBar;
	private View view;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		view = LayoutInflater.from(this).inflate(R.layout.food_detail, null);
		scrollView = (ScrollView) view.findViewById(R.id.scroll_view);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		LinearLayout imageList = (LinearLayout) view.findViewById(R.id.imageList);

		for (int i = 0; i < 10; i++)
		{
			View imageLayout = LayoutInflater.from(this).inflate(R.layout.food_detail_image, null);

			imageList.addView(imageLayout);
		}

		setContentView(view);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			foodId = extras.getInt("FOOD_ID");
		}
		LoadFoodDetailTask lfdt = new LoadFoodDetailTask();

		Runnable success = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
				showContent(true);
			}
		};

		Runnable fail = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
				showContent(false);
			}
		};

		lfdt.execute(new LoadFoodDetailParam(foodId, this, success, fail));

		setTitle(R.string.food_detail_title);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tv = (TextView) view.findViewById(R.id.description_text);
		tv.setMovementMethod(new ScrollingMovementMethod());
	}

	public void toggleProgressBar(boolean on)
	{
		if (progressBar == null)
		{
			return;
		}
		if (on)
		{
			progressBar.setVisibility(ProgressBar.VISIBLE);
		}
		else
		{
			progressBar.setVisibility(ProgressBar.GONE);
		}
	}

	public void showContent(boolean show)
	{
		if(show)
		{
			scrollView.setVisibility(View.VISIBLE);
		}
		else
		{
			scrollView.setVisibility(View.INVISIBLE);
		}
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

	public void setFoodDetail(final FoodDetail foodDetail)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				setFoodDetailSync(foodDetail);
			}
		});
	}

	private void setFoodDetailSync(FoodDetail foodDetail)
	{
		TextView name = (TextView) findViewById(R.id.name);
		TextView category = (TextView) findViewById(R.id.category);
		TextView vendor = (TextView) findViewById(R.id.vendor);
		TextView gtin = (TextView) findViewById(R.id.gtin);
		TextView description = (TextView) findViewById(R.id.description_text);
		TextView defaultUseBy = (TextView) findViewById(R.id.default_use_by);
		TextView usualPrice = (TextView) findViewById(R.id.usual_price);
		TextView addedBy = (TextView) findViewById(R.id.added_by);
		TextView editedBy = (TextView) findViewById(R.id.edited_by);

		name.setText(foodDetail.getName());
		category.setText(foodDetail.getCategory());
		vendor.setText(foodDetail.getVendor());
		gtin.setText(GtinUtil.getReadableGtin(foodDetail.getGtin()));
		description.setText(foodDetail.getDescription());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(foodDetail.getDefaultUseBy()));
		String useBy = "";
		useBy += (calendar.get(Calendar.YEAR) - 1970) + " " + getString(R.string.year);
		useBy += " " + calendar.get(Calendar.MONTH) + " " + getString(R.string.month);
		useBy += " " + (calendar.get(Calendar.DAY_OF_MONTH) - 1) + " " + getString(R.string.day);
		defaultUseBy.setText(useBy);
		usualPrice.setText("$" + foodDetail.getUsualPrice());
		addedBy.setText(foodDetail.getAddedBy());
		editedBy.setText(foodDetail.getLastEditedBy());
	}
}
