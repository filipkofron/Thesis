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

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 3/2/14.
 */
public class InventoryListFragment extends ListFragment implements ReloadCallback, DeleteInventoryListener
{
	
	/** The adapter. */
	private InventoryListAdapter adapter;
	
	/** The progress bar. */
	private ProgressBar progressBar;

	/** The deleted item. */
	private InventoryItem deletedItem;
	
	/** The reload call back on delete. */
	private ReloadCallback reloadCallBackOnDelete;

	/** The undo layout. */
	private RelativeLayout undoLayout;
	
	/** The undu button. */
	private Button unduButton;
	
	/** The undo text view. */
	private TextView undoTextView;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		adapter = new InventoryListAdapter(getActivity(), this, this);
		setListAdapter(adapter);

		load();
	}

	/**
	 * Load.
	 */
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

	/**
	 * Hide undo.
	 */
	private void hideUndo()
	{
		TranslateAnimation animate = new TranslateAnimation(0,0,0, -unduButton.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		undoLayout.startAnimation(animate);
		undoLayout.setVisibility(RelativeLayout.GONE);
	}

	/**
	 * The Class FinishedUndoDelete.
	 */
	private class FinishedUndoDelete implements Runnable
	{
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run()
		{
			if(reloadCallBackOnDelete != null)
			{
				reloadCallBackOnDelete.update();
			}
		}
	}

	/** The finished undo delete. */
	private FinishedUndoDelete finishedUndoDelete = new FinishedUndoDelete();

	/**
	 * Undo.
	 */
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

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.adapter.ReloadCallback#update()
	 */
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
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

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == R.id.action_add)
		{
			new InventoryAddFoodDialogFragment().show(getActivity());
		}

		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{

		inflater.inflate(R.menu.inventory_list, menu);

		super.onCreateOptionsMenu(menu, inflater);
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.adapter.DeleteInventoryListener#onDeleteItem(cz.kofron.foodinventory.client.model.InventoryItem, cz.kofron.foodinventory.client.adapter.ReloadCallback)
	 */
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
