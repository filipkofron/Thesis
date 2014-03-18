package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.task.SearchAndAddTask;

/**
 * Created by kofee on 10.3.14.
 */
public class AddScanFragment extends AbstractScanFragment
{
	private boolean accept = true;

	private Runnable onDone = new Runnable()
	{
		@Override
		public void run()
		{
			accept = true;
		}
	};

	@Override
	public void onGtin(String gtin)
	{
		if(accept)
		{
			accept = false;
			SearchAndAddTask saat = new SearchAndAddTask(getActivity(), gtin, onDone);

			saat.execute();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}
}
