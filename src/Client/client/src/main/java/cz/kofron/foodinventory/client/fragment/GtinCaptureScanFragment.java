package cz.kofron.foodinventory.client.fragment;

import cz.kofron.foodinventory.client.activity.OnGtinSelectListener;

/**
 * Created by kofee on 25.3.14.
 */
public class GtinCaptureScanFragment extends AbstractScanFragment
{
	private OnGtinSelectListener onGtinSelectListener;

	public GtinCaptureScanFragment(OnGtinSelectListener onGtinSelectListener)
	{
		this.onGtinSelectListener = onGtinSelectListener;
	}

	@Override
	public void onGtin(String gtin)
	{
		if(onGtinSelectListener != null)
		{
			onGtinSelectListener.onGtinSelected(gtin);
		}
	}
}
