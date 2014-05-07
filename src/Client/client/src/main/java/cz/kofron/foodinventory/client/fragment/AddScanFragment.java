package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.task.SearchAndAddTask;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 10.3.14.
 */
public class AddScanFragment extends AbstractScanFragment
{
	
	/** The accept. */
	private boolean accept = true;
	
	/** The last gtin. */
	private String lastGtin = "";

	/** The on done. */
	private Runnable onDone = new Runnable()
	{
		@Override
		public void run()
		{
			accept = true;
		}
	};
	
	/** The on added food. */
	private Runnable onAddedFood = new Runnable()
	{
		@Override
		public void run()
		{
			getActivity().runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					onGtin(lastGtin);
				}
			});
		}
	};

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.fragment.AbstractScanFragment#onGtin(java.lang.String)
	 */
	@Override
	public void onGtin(String gtin)
	{
		if(accept)
		{
			accept = false;
			lastGtin = gtin;
			SearchAndAddTask saat = new SearchAndAddTask(getActivity(), gtin, onDone, onAddedFood);

			saat.execute();
		}
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.fragment.AbstractScanFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}
}
