package cz.kofron.foodinventory.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cz.kofron.foodinventory.client.R;

/**
 * Created by kofee on 10.3.14.
 */
public class AddScanFragment extends AbstractScanFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relative_layout);

		View addView = inflater.inflate(R.layout.scan_add, null);
		rl.addView(addView);

		resultView = (TextView) view.findViewById(R.id.gtin_view);

		view.invalidate();

		return view;
	}
}
