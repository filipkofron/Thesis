package cz.kofron.foodinventory.client.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;

import cz.kofron.foodinventory.client.view.NowLikeListView;

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