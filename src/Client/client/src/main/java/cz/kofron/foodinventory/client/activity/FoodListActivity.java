package cz.kofron.foodinventory.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.FoodListAdapter;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.SearchFoodTask;
import cz.kofron.foodinventory.client.task.param.SearchFoodParam;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

// TODO: Auto-generated Javadoc
/**
 * Created by Filip Kofron on 3/1/14.
 */
public class FoodListActivity extends ListActionBarActivity implements ReloadCallback
{
	
	/** The progress bar. */
	private ProgressBar progressBar;
	
	/** The search string. */
	private String searchString;
	
	/** The adapter. */
	private FoodListAdapter adapter;

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(R.string.title_search_results);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		View view = getLayoutInflater().inflate(R.layout.food_list, null);

		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

		ListView lv = (ListView) view.findViewById(android.R.id.list);

		adapter = new FoodListAdapter(this);
		lv.setAdapter(adapter);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			searchString = extras.getString("SEARCH_VALUE");
		}
		if(searchString == null)
		{
			searchString = "";
		}


		loadContent();


		setContentView(view);
	}

	/**
	 * Load content.
	 */
	private void loadContent()
	{
		SearchFoodTask sft = new SearchFoodTask();

		Runnable success = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
			}
		};

		Runnable fail = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
				NetworkInstance.connector.forceCheck();
				NetworkErrorToast.showError(FoodListActivity.this);
			}
		};

		sft.execute(new SearchFoodParam(searchString, adapter, success, fail));
	}

	/**
	 * Toggle progress bar.
	 *
	 * @param on the on
	 */
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.food_list, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		ConnectionDialogManager.initialize(this);
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.adapter.ReloadCallback#update()
	 */
	@Override
	public void update()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				loadContent();
			}
		});
	}
}
