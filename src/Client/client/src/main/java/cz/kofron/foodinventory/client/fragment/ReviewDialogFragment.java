package cz.kofron.foodinventory.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import java.util.ArrayList;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.model.FoodReview;
import cz.kofron.foodinventory.client.task.param.SetUserReviewParam;
import cz.kofron.foodinventory.client.task.param.SetUserReviewTask;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 24.3.14.
 */
public class ReviewDialogFragment extends DialogFragment
{
	
	/** The food detail. */
	private FoodDetail foodDetail;
	
	/** The reload callback. */
	private ReloadCallback reloadCallback;
	
	/** The initial rating. */
	private float initialRating;
	
	/** The food review. */
	private FoodReview foodReview;

	/**
	 * Instantiates a new review dialog fragment.
	 *
	 * @param foodDetail the food detail
	 * @param reloadCallback the reload callback
	 * @param initialRating the initial rating
	 * @param foodReview the food review
	 */
	public ReviewDialogFragment(FoodDetail foodDetail, ReloadCallback reloadCallback, float initialRating, FoodReview foodReview)
	{
		this.foodDetail = foodDetail;
		this.reloadCallback = reloadCallback;
		this.initialRating = initialRating;
		this.foodReview = foodReview;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.user_review_dialog, null);
		final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
		final EditText ratingText = (EditText) view.findViewById(R.id.rating_text);

		ratingBar.setRating(initialRating);
		ArrayList<FoodReview> reviews = foodDetail.getReviews();

		if(foodReview != null)
		{
			ratingText.setText(foodReview.getText());
		}

		DialogInterface.OnClickListener onDeleteListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				SetUserReviewParam surp = new SetUserReviewParam(0, true, foodDetail.getId(), "");
				SetUserReviewTask surt = new SetUserReviewTask(surp, reloadCallback);
				surt.execute();
			}
		};

		DialogInterface.OnClickListener onRateListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				if(ratingText.getText() != null)
				{
					SetUserReviewParam surp = new SetUserReviewParam(ratingBar.getRating(), false, foodDetail.getId(), ratingText.getText().toString());
					SetUserReviewTask surt = new SetUserReviewTask(surp, reloadCallback);
					surt.execute();
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);
		builder.setTitle("Write a review");
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.delete, onDeleteListener);
		builder.setPositiveButton(R.string.rate, onRateListener);

		final Dialog dialog = builder.create();

		return dialog;
	}

	/**
	 * Show.
	 *
	 * @param activity the activity
	 */
	public void show(FragmentActivity activity)
	{
		show(activity.getSupportFragmentManager(), "set_user_review_dialog");
	}
}
