package cz.kofron.foodinventory.client.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.VendorDialogFragment;

/**
 * Created by kofee on 3/4/14.
 */
public class FoodEditActivity extends ActionBarActivity implements VendorDialogFragment.VendorDialogListener
{

	public void showSelectVendorDialog()
	{
		new VendorDialogFragment().show(this);
	}

	@Override
	public void onVendorSelected(final int vendorId)
	{
		this.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((TextView) findViewById(R.id.vendor_text)).setText("Vendor #" + (vendorId + 1));
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(this).inflate(R.layout.food_edit, null);
		((Button) view.findViewById(R.id.vendor_button)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showSelectVendorDialog();
			}
		});
		Spinner spinner = (Spinner) view.findViewById(R.id.category_spinner);
		List<String> list = new ArrayList<>();

		for (int i = 0; i < 10; i++)
		{
			list.add("Category #" + i);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		int menuId = R.menu.food_edit;
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
			case R.id.action_save:
				Toast.makeText(this, "Saving", 1000).show();
				AsyncTask at = new AsyncTask<Object, Object, Object>()
				{
					@Override
					protected Object doInBackground(Object... objects)
					{
						try
						{
							Thread.sleep(1500);
						}
						catch (InterruptedException e)
						{
						}
						return null;
					}

					@Override
					protected void onPostExecute(Object o)
					{
						Toast.makeText(FoodEditActivity.this, "Saved", 1000).show();
						FoodEditActivity.this.finish();
					}
				};
				at.execute();
				return true;
		}

		return false;
	}
}
