package cz.kofron.foodinventory.client;

import android.support.v7.app.ActionBarActivity;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

public abstract class ListActionBarActivity extends ActionBarActivity
{

	private NowLikeListView mListView;

	protected NowLikeListView getListView()
	{
		if (mListView == null)
		{
			mListView = (NowLikeListView) findViewById(android.R.id.list);
		}
		return mListView;
	}

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

	protected void setListAdapter(ListAdapter adapter)
	{
		getListView().setAdapter(adapter);
	}
}