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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.fragment.VendorDialogFragment;
import cz.kofron.foodinventory.client.model.AmountType;
import cz.kofron.foodinventory.client.model.Category;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.model.FoodHelper;
import cz.kofron.foodinventory.client.model.Vendor;
import cz.kofron.foodinventory.client.util.GtinUtil;

/**
 * Created by kofee on 3/4/14.
 */
public class FoodEditActivity extends ActionBarActivity implements VendorDialogFragment.VendorDialogListener
{
	private View view;

	public static FoodDetail initialFoodDetail;

	private FoodDetail foodDetail;
	private ArrayList<Category> categories = FoodHelper.categories;
	private ArrayList<Vendor> vendors = FoodHelper.vendors;

	public void showSelectVendorDialog()
	{
		new VendorDialogFragment(vendors).show(this);
	}

	@Override
	public void onVendorSelected(final int vendorId)
	{
		this.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				((EditText) findViewById(R.id.vendor)).setText(vendors.get(vendorId).getName());
			}
		});
	}

	private void populateEdit()
	{
		EditText name = (EditText) view.findViewById(R.id.name);
		EditText vendor = (EditText) view.findViewById(R.id.vendor);
		Spinner category = (Spinner) view.findViewById(R.id.category_spinner);
		EditText gtin = (EditText) view.findViewById(R.id.gtin);
		EditText description = (EditText) view.findViewById(R.id.description);
		EditText years = (EditText) view.findViewById(R.id.years);
		EditText months = (EditText) view.findViewById(R.id.months);
		EditText days = (EditText) view.findViewById(R.id.days);
		RadioGroup rg = (RadioGroup) view.findViewById(R.id.amount_type);
		EditText amount = (EditText) view.findViewById(R.id.amount);
		EditText price = (EditText) view.findViewById(R.id.price);

		RadioButton [] buttons = new RadioButton[3];
		buttons[AmountType.GRAMS] = (RadioButton) view.findViewById(R.id.grams);
		buttons[AmountType.LITERS] = (RadioButton) view.findViewById(R.id.liters);
		buttons[AmountType.PIECES] = (RadioButton) view.findViewById(R.id.pieces);

		name.setText(foodDetail.getName());
		vendor.setText(foodDetail.getVendor());
		int selection = 0;
		for(Category cat : categories)
		{
			if(cat.getName().equals(foodDetail.getCategory()))
			{
				break;
			}
			selection++;
		}
		category.setSelection(selection);
		gtin.setText(GtinUtil.getReadableGtin(foodDetail.getGtin()));
		description.setText(foodDetail.getDescription());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(foodDetail.getDefaultUseBy()));
		years.setText("" + (calendar.get(Calendar.YEAR) - 1970));
		months.setText("" + calendar.get(Calendar.MONTH));
		days.setText("" + (calendar.get(Calendar.DAY_OF_MONTH) - 1));
		rg.check(buttons[foodDetail.getAmountType()].getId());
		amount.setText("" + foodDetail.getAmount());
		price.setText("" + foodDetail.getUsualPrice());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if(initialFoodDetail != null)
		{
			foodDetail = initialFoodDetail;
			initialFoodDetail = null;
		}

		view = LayoutInflater.from(this).inflate(R.layout.food_edit, null);
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

		for (Category category : categories)
		{
			list.add(category.getName());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		RadioGroup rg = (RadioGroup) view.findViewById(R.id.amount_type);
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i)
			{
				TextView amountName = (TextView) view.findViewById(R.id.amount_name);
				amountName.setText(((RadioButton) view.findViewById(i)).getText());
			}
		});

		if(foodDetail != null)
		{
			populateEdit();
		}

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
