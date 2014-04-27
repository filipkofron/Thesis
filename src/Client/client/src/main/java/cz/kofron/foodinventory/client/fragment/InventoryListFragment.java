package cz.kofron.foodinventory.client.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.DeleteInventoryListener;
import cz.kofron.foodinventory.client.adapter.ReloadCallback;
import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;
import cz.kofron.foodinventory.client.model.InventoryItem;
import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.task.EditInventoryTask;
import cz.kofron.foodinventory.client.task.LoadInventoryTask;
import cz.kofron.foodinventory.client.task.param.EditInventoryParam;
import cz.kofron.foodinventory.client.task.param.LoadInventoryParam;
import cz.kofron.foodinventory.client.util.DateUtil;
import cz.kofron.foodinventory.client.util.NetworkErrorToast;

/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListFragment extends ListFragment implements ReloadCallback, DeleteInventoryListener
{
	private InventoryListAdapter adapter;
	private ProgressBar progressBar;

	private InventoryItem deletedItem;
	private ReloadCallback reloadCallBackOnDelete;

	private RelativeLayout undoLayout;
	private Button unduButton;
	private TextView undoTextView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		adapter = new InventoryListAdapter(getActivity(), this, this);
		setListAdapter(adapter);

		load();
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
				NetworkInstance.connector.forceCheck();
				NetworkErrorToast.showError(getActivity());
			}
		};

		lit.execute(new LoadInventoryParam("", "", adapter, success, fail));
	}

	private void hideUndo()
	{
		TranslateAnimation animate = new TranslateAnimation(0,0,0, -unduButton.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		undoLayout.startAnimation(animate);
		undoLayout.setVisibility(RelativeLayout.GONE);
	}

	private class FinishedUndoDelete implements Runnable
	{
		@Override
		public void run()
		{
			if(reloadCallBackOnDelete != null)
			{
				reloadCallBackOnDelete.update();
			}
		}
	}

	private FinishedUndoDelete finishedUndoDelete = new FinishedUndoDelete();

	private void undo()
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				hideUndo();
				if(deletedItem != null)
				{
					AsyncTask at = new EditInventoryTask(getActivity(), new EditInventoryParam(finishedUndoDelete, finishedUndoDelete, true, deletedItem.getId(), deletedItem.getFoodId(), deletedItem.getUseBy(), 1));
					at.execute();
				}
			}
		});
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

		undoLayout = (RelativeLayout) view.findViewById(R.id.back_layout);
		unduButton = (Button) view.findViewById(R.id.undo_button);
		undoTextView =  (TextView) view.findViewById(R.id.undo_delete_text);

		unduButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				undo();
			}
		});

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

	@Override
	public void onDeleteItem(InventoryItem item, ReloadCallback reloadCallBackOnDelete)
	{
		this.deletedItem = item;
		this.reloadCallBackOnDelete = reloadCallBackOnDelete;
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if(deletedItem != null)
				{
					String name = deletedItem.getFoodName();
					if(name.length() > 15)
					{
						name = name.substring(0, 15) + "..";

					}
					undoTextView.setText(getText(R.string.undo_delete_food_text) + " " + name + ".");
				}
				TranslateAnimation animate = new TranslateAnimation(0, 0, -unduButton.getHeight(), 0);
				animate.setDuration(500);
				animate.setFillAfter(true);
				undoLayout.startAnimation(animate);
				undoLayout.setVisibility(RelativeLayout.VISIBLE);
			}
		});
	}
}
