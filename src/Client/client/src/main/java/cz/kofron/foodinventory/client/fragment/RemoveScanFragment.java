package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;
import cz.kofron.foodinventory.client.adapter.InventoryListAdapter;

/**
 * Created by kofee on 10.3.14.
 */
public class RemoveScanFragment extends AbstractScanFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relative_layout);

		View addView = inflater.inflate(R.layout.scan_remove, null);
		rl.addView(addView);

		resultView = (TextView) view.findViewById(R.id.gtin_view);

		view.invalidate();

		ListView lv = (ListView) view.findViewById(R.id.listView);

		lv.setAdapter(new InventoryListAdapter(getActivity(), null));

		return view;
	}
}
