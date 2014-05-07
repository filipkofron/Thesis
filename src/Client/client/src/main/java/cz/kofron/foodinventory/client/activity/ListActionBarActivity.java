package cz.kofron.foodinventory.client.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

import cz.kofron.foodinventory.client.view.NowLikeListView;

// TODO: Auto-generated Javadoc
/**
 * The Class ListActionBarActivity.
 */
public abstract class ListActionBarActivity extends ActionBarActivity
{

	/** The m list view. */
	private NowLikeListView mListView;

	/**
	 * Gets the list view.
	 *
	 * @return the list view
	 */
	protected NowLikeListView getListView()
	{
		if (mListView == null)
		{
			mListView = (NowLikeListView) findViewById(android.R.id.list);
		}
		return mListView;
	}

	/**
	 * Gets the list adapter.
	 *
	 * @return the list adapter
	 */
	protected ListAdapter getListAdapter()
	{
		ListAdapter adapter = getListView().getAdapter();
		if (adapter instanceof HeaderViewListAdapter)
		{
			return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
		}
		else
		{
			return adapter;
		}
	}

	/**
	 * Sets the list adapter.
	 *
	 * @param adapter the new list adapter
	 */
	protected void setListAdapter(ListAdapter adapter)
	{
		getListView().setAdapter(adapter);
	}
}