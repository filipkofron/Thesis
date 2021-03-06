package cz.kofron.foodinventory.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.FormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ImageEditAdapter;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.fragment.VendorDialogFragment;
import cz.kofron.foodinventory.client.model.AmountType;
import cz.kofron.foodinventory.client.model.Category;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.model.FoodHelper;
import cz.kofron.foodinventory.client.model.PODResult;
import cz.kofron.foodinventory.client.model.Vendor;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.EditFoodTask;
import cz.kofron.foodinventory.client.task.param.EditFoodParam;
import cz.kofron.foodinventory.client.util.DateUtil;
import cz.kofron.foodinventory.client.util.GtinUtil;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/4/14.
 */
public class FoodEditActivity extends ActionBarActivity implements VendorDialogFragment.VendorDialogListener, OnGtinSelectListener, ReloadCallback
{
	
	/** The view. */
	private View view;

	/** The initial food detail. */
	public static FoodDetail initialFoodDetail;
	
	/** The initial gtin. */
	public static String initialGtin;
	
	/** The initial reload callback. */
	public static ReloadCallback initialReloadCallback;
	
	/** The on saved callback. */
	public static Runnable onSavedCallback;
	
	/** The initial pod result. */
	public static PODResult initialPodResult;

	/** The food detail. */
	private FoodDetail foodDetail;
	
	/** The categories. */
	private ArrayList<Category> categories = FoodHelper.categories;
	
	/** The vendors. */
	private ArrayList<Vendor> vendors = FoodHelper.vendors;
	
	/** The given gtin. */
	private String givenGtin;
	
	/** The reload callback. */
	private ReloadCallback reloadCallback;
	
	/** The pod result. */
	private PODResult podResult;
	
	/** The selected category. */
	private int selectedCategory;
	
	/** The image edit adapter. */
	private ImageEditAdapter imageEditAdapter;

	/**
	 * Show select vendor dialog.
	 */
	public void showSelectVendorDialog()
	{
		new VendorDialogFragment(vendors).show(this);
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.fragment.VendorDialogFragment.VendorDialogListener#onVendorSelected(int)
	 */
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

	/**
	 * Populate edit.
	 */
	private void populateEdit()
	{
		EditText name = (EditText) view.findViewById(R.id.name);
		EditText vendor = (EditText) view.findViewById(R.id.vendor);
		Spinner category = (Spinner) view.findViewById(R.id.category_spinner);
		EditText gtin = (EditText) view.findViewById(R.id.gtin);
		EditText description = (EditText) view.findViewById(R.id.description);
		EditText years = (EditText) view.findViewById(R.id.use_years);
		EditText months = (EditText) view.findViewById(R.id.use_months);
		EditText days = (EditText) view.findViewById(R.id.use_days);
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
				selectedCategory = cat.getId();
				break;
			}
			selection++;
		}

		imageEditAdapter.populate(foodDetail.getImageIds());

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

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		foodDetail = initialFoodDetail;
		initialFoodDetail = null;
		givenGtin = initialGtin;
		initialGtin = null;
		reloadCallback = initialReloadCallback;
		initialReloadCallback = null;
		podResult = initialPodResult;
		initialPodResult = null;

		view = LayoutInflater.from(this).inflate(R.layout.food_edit, null);
		((Button) view.findViewById(R.id.vendor_button)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				showSelectVendorDialog();
			}
		});
		((Button) view.findViewById(R.id.scan_barcode)).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				GtinCaptureActivity.initialOnGtinSelectListener = FoodEditActivity.this;
				Intent foodEditIntent = new Intent(FoodEditActivity.this, GtinCaptureActivity.class);
				startActivity(foodEditIntent);
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

		imageEditAdapter = new ImageEditAdapter((LinearLayout) view.findViewById(R.id.image_list), this);

		if(foodDetail != null)
		{
			populateEdit();
		}
		else
		{
			imageEditAdapter.populate(new ArrayList<String>());
			if(givenGtin != null)
			{
				EditText gtin = (EditText) view.findViewById(R.id.gtin);
				gtin.setText(givenGtin);
			}
			else
			{
				if(podResult != null)
				{
					populatePODResult();
				}
			}
			view.invalidate();
		}

		getSupportActionBar().setTitle(R.string.title_food_edit);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(view);
	}

	/**
	 * Populate pod result.
	 */
	private void populatePODResult()
	{
		EditText gtin = (EditText) view.findViewById(R.id.gtin);
		gtin.setText(podResult.getGtin());
		if(podResult.getImage() != null)
		{
			imageEditAdapter.onImageBitmap(podResult.getImage());
		}
		EditText name = (EditText) view.findViewById(R.id.name);
		EditText vendor = (EditText) view.findViewById(R.id.vendor);

		name.setText(podResult.getName());
		vendor.setText(podResult.getVendor());
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		int menuId = R.menu.food_edit;
		getMenuInflater().inflate(menuId, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Gets the selected category id.
	 *
	 * @return the selected category id
	 */
	private int getSelectedCategoryId()
	{
		Spinner category = (Spinner) view.findViewById(R.id.category_spinner);

		return categories.get(category.getSelectedItemPosition()).getId();
	}

	/**
	 * Gets the default use by time.
	 *
	 * @return the default use by time
	 */
	private long getDefaultUseByTime()
	{
		EditText years = (EditText) view.findViewById(R.id.use_years);
		EditText months = (EditText) view.findViewById(R.id.use_months);
		EditText days = (EditText) view.findViewById(R.id.use_days);

		int year = 0;
		int month = 0;
		int day = 0;

		try
		{

			if (years.getText() != null)
			{
				year = Integer.parseInt(years.getText().toString());
			}
			else
			{
				year = 0;
			}

			if (months.getText() != null)
			{
				month = Integer.parseInt(months.getText().toString());
			}
			else
			{
				month = 0;
			}

			if (days.getText() != null)
			{
				day = Integer.parseInt(days.getText().toString());
			}
			else
			{
				day = 0;
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}

		return DateUtil.getTimeFromValues(year, month, day);
	}

	/**
	 * Gets the selected amount type.
	 *
	 * @return the selected amount type
	 */
	private int getSelectedAmountType()
	{
		RadioGroup rg = (RadioGroup) view.findViewById(R.id.amount_type);

		RadioButton [] buttons = new RadioButton[3];
		buttons[AmountType.GRAMS] = (RadioButton) view.findViewById(R.id.grams);
		buttons[AmountType.LITERS] = (RadioButton) view.findViewById(R.id.liters);
		buttons[AmountType.PIECES] = (RadioButton) view.findViewById(R.id.pieces);

		int selected = 0;

		for(int i = 0; i < 3; i++)
		{
			if(rg.getCheckedRadioButtonId() == buttons[i].getId())
			{
				selected = i;
			}
		}

		return selected;
	}

	/**
	 * Make param.
	 *
	 * @return the edits the food param
	 */
	private EditFoodParam makeParam()
	{
		final Runnable onSaved = onSavedCallback;
		onSavedCallback = null;
		Runnable success = new Runnable()
		{
			@Override
			public void run()
			{
				if(onSaved != null)
				{
					onSaved.run();
				}
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
				NetworkInstance.connector.forceCheck();
				NetworkErrorToast.showError(FoodEditActivity.this);
			}
		};

		EditText name = (EditText) view.findViewById(R.id.name);
		EditText vendor = (EditText) view.findViewById(R.id.vendor);
		EditText gtin = (EditText) view.findViewById(R.id.gtin);
		EditText description = (EditText) view.findViewById(R.id.description);

		EditText amount = (EditText) view.findViewById(R.id.amount);
		EditText price = (EditText) view.findViewById(R.id.price);

		boolean addingP = foodDetail == null;
		int idP = addingP ? 0 : foodDetail.getId();
		String nameP = name.getText().toString();
		String vendorP = vendor.getText().toString();
		int categoryIdP = getSelectedCategoryId();
		String gtinP = gtin.getText().toString();
		String descriptionP = description.getText().toString();
		long defaultUseByP = getDefaultUseByTime();
		int amountTypeP = getSelectedAmountType();
		float amountP = Float.parseFloat(amount.getText().toString());
		float usualPriceP = Float.parseFloat(price.getText().toString());

		return new EditFoodParam(addingP, idP, nameP, vendorP, categoryIdP, gtinP, descriptionP, defaultUseByP, amountTypeP, amountP, usualPriceP, success, fail);
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
			case R.id.action_save:

				EditFoodTask eft = new EditFoodTask(makeParam(), imageEditAdapter.makeEditParam(), this);
				eft.execute();
				return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == ImageEditAdapter.REQUEST_CODE && resultCode == Activity.RESULT_OK)
		{
			if(data.getData() != null)
			{
				try
				{
					InputStream stream = getContentResolver().openInputStream(data.getData());
					imageEditAdapter.onImageStream(stream);
					stream.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				imageEditAdapter.onImageBitmap((Bitmap) data.getExtras().get("data"));
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.activity.OnGtinSelectListener#onGtinSelected(java.lang.String)
	 */
	@Override
	public void onGtinSelected(String gtin)
	{
		final String fGtin = gtin;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				EditText gtin = (EditText) view.findViewById(R.id.gtin);
				gtin.setText(fGtin);
				gtin.invalidate();
			}
		});
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

	}
}
