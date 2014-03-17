package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;
import cz.kofron.foodinventory.client.task.LoadInventoryTask;
import cz.kofron.foodinventory.client.task.param.LoadInventoryParam;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListFragment extends ListFragment implements ReloadCallback
{
	private InventoryListAdapter adapter;
	private ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		adapter = new InventoryListAdapter(getActivity(), this);
		setListAdapter(adapter);

		load();

		/*getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				new LoginDialogFragment().show(getActivity());
			}
		});*/
	}

	private void load()
	{
		LoadInventoryTask lit = new LoadInventoryTask();

		Runnable success = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
			}
		};

		Runnable fail = new Runnable()
		{
			@Override
			public void run()
			{
				toggleProgressBar(false);
				NetworkErrorToast.showError(getActivity());
			}
		};

		lit.execute(new LoadInventoryParam("", "", adapter, success, fail));
	}

	public void update()
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				adapter.clear();
				adapter.notifyDataSetChanged();
				load();
			}
		});
	}

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.inventory_list_fragment, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		return view;
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
