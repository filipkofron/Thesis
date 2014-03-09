package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListFragment extends ListFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		setListAdapter(new InventoryListAdapter(getActivity()));

		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				new LoginDialogFragment().show(getActivity());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.inventory_list_fragment, container, false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_add)
		{
			new InventoryAddFoodDialogFragment().show(getActivity());
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{

		inflater.inflate(R.menu.inventory_list, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}
}
