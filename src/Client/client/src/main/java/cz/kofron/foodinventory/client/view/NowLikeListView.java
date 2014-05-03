package cz.kofron.foodinventory.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import cz.kofron.foodinventory.client.R;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/1/14.
 */
public class NowLikeListView extends ListView implements ViewTreeObserver.OnGlobalLayoutListener
{

	/**
	 * Instantiates a new now like list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public NowLikeListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setDividerHeight(0);
		initLayoutObserver();
	}

	/**
	 * Instantiates a new now like list view.
	 *
	 * @param context the context
	 */
	public NowLikeListView(Context context)
	{
		super(context);
		initLayoutObserver();
	}


	/**
	 * Inits the layout observer.
	 */
	private void initLayoutObserver()
	{
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView#onGlobalLayout()
	 */
	@Override
	public void onGlobalLayout()
	{
		getViewTreeObserver().removeGlobalOnLayoutListener(this);

		final int heightPx = getContext().getResources().getDisplayMetrics().heightPixels;

		boolean inversed = false;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++)
		{
			View child = getChildAt(i);

			int[] location = new int[2];

			child.getLocationOnScreen(location);

			if (location[1] > heightPx)
			{
				break;
			}

			if (!inversed)
			{
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.slide_up_left));
			}
			else
			{
				child.startAnimation(AnimationUtils.loadAnimation(getContext(),
						R.anim.slide_up_right));
			}

			inversed = !inversed;
		}

	}
}
