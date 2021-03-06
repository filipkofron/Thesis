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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ImageViewAdapter;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.fragment.ReviewDialogFragment;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.model.FoodReview;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.LoadFoodDetailTask;
import cz.kofron.foodinventory.client.task.param.LoadFoodDetailParam;
import cz.kofron.foodinventory.client.util.GtinUtil;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/3/14.
 */
public class FoodDetailActivity extends ActionBarActivity implements ReloadCallback
{
	
	/** The food id. */
	private int foodId;
	
	/** The food. */
	private FoodDetail food;
	
	/** The progress bar. */
	private ProgressBar progressBar;
	
	/** The view. */
	private View view;
	
	/** The scroll view. */
	private ScrollView scrollView;
	
	/** The image view adapter. */
	private ImageViewAdapter imageViewAdapter;
	
	/** The current food review. */
	private FoodReview currentFoodReview;

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		view = LayoutInflater.from(this).inflate(R.layout.food_detail, null);
		scrollView = (ScrollView) view.findViewById(R.id.scroll_view);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		LinearLayout imageList = (LinearLayout) view.findViewById(R.id.imageList);

		imageViewAdapter = new ImageViewAdapter(imageList, this);

		setContentView(view);

		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			foodId = extras.getInt("FOOD_ID");
		}

		loadContent();

		getSupportActionBar().setTitle(R.string.title_food_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tv = (TextView) view.findViewById(R.id.description_text);
		tv.setMovementMethod(new ScrollingMovementMethod());
	}

	/**
	 * Load content.
	 */
	public void loadContent()
	{
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
				NetworkInstance.connector.forceCheck();
				NetworkErrorToast.showError(FoodDetailActivity.this);
			}
		};

		lfdt.execute(new LoadFoodDetailParam(foodId, this, success, fail));
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

	/**
	 * Show content.
	 *
	 * @param show the show
	 */
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		int menuId = R.menu.food_detail;
		getMenuInflater().inflate(menuId, menu);
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
			case R.id.action_edit:
				FoodEditActivity.initialFoodDetail = food;
				FoodEditActivity.initialReloadCallback = this;
				Intent foodEditIntent = new Intent(this, FoodEditActivity.class);
				startActivity(foodEditIntent);
				return true;
		}

		return false;
	}

	/**
	 * Sets the food detail.
	 *
	 * @param foodDetail the new food detail
	 */
	public void setFoodDetail(final FoodDetail foodDetail)
	{
		food = foodDetail;
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				setFoodDetailSync(foodDetail);
			}
		});
	}

	/**
	 * Populate reviews.
	 *
	 * @param foodDetail the food detail
	 */
	private void populateReviews(FoodDetail foodDetail)
	{
		LinearLayout reviewLayout = (LinearLayout) findViewById(R.id.review_layout);
		reviewLayout.removeAllViews();

		for(FoodReview review : foodDetail.getReviews())
		{
			View reviewView = LayoutInflater.from(this).inflate(R.layout.review, null);
			View separator = LayoutInflater.from(this).inflate(R.layout.vertical_line, null);

			String num = String.format("%.1f", review.getRating());

			TextView ratingValue = (TextView) reviewView.findViewById(R.id.rating_value);
			TextView ratingUser = (TextView) reviewView.findViewById(R.id.rating_user);
			TextView ratingText = (TextView) reviewView.findViewById(R.id.rating_text);

			ratingValue.setText(num);
			ratingUser.setText(review.getUsername());
			ratingText.setText(review.getText());

			reviewLayout.addView(reviewView);
			reviewLayout.addView(separator);
		}
	}

	/**
	 * Populate rating bar.
	 *
	 * @param foodDetail the food detail
	 */
	private void populateRatingBar(final FoodDetail foodDetail)
	{
		currentFoodReview = null;
		for(FoodReview review : foodDetail.getReviews())
		{
			if(review.equals(NetworkInstance.communicator.getUsername()))
			{
				currentFoodReview = review;
				break;
			}
		}

		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

		if(currentFoodReview != null)
		{
			ratingBar.setRating(currentFoodReview.getRating());
		}

		ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
		{
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
			{
				if(fromUser)
				{
					new ReviewDialogFragment(foodDetail, FoodDetailActivity.this, ratingBar.getRating(), currentFoodReview).show(FoodDetailActivity.this);
				}
			}
		});

	}

	/**
	 * Sets the food detail sync.
	 *
	 * @param foodDetail the new food detail sync
	 */
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

		populateRatingBar(foodDetail);
		populateReviews(foodDetail);

		name.setText(foodDetail.getName());
		category.setText(" - " + foodDetail.getCategory());
		vendor.setText("Vendor: " + foodDetail.getVendor());
		gtin.setText("GTIN: " + GtinUtil.getReadableGtin(foodDetail.getGtin()));
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

		imageViewAdapter.populate(foodDetail.getImageIds());

		view.invalidate();
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.adapter.ReloadCallback#update()
	 */
	@Override
	public void update()
	{
		loadContent();
	}

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onPostResume()
	 */
	@Override
	protected void onPostResume()
	{
		super.onPostResume();
		ConnectionDialogManager.initialize(this);
	}
}
