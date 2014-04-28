package cz.kofron.foodinventory.client.fragment;

import cz.kofron.foodinventory.client.activity.OnGtinSelectListener;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 25.3.14.
 */
public class GtinCaptureScanFragment extends AbstractScanFragment
{
	
	/** The on gtin select listener. */
	private OnGtinSelectListener onGtinSelectListener;

	/**
	 * Instantiates a new gtin capture scan fragment.
	 *
	 * @param onGtinSelectListener the on gtin select listener
	 */
	public GtinCaptureScanFragment(OnGtinSelectListener onGtinSelectListener)
	{
		this.onGtinSelectListener = onGtinSelectListener;
	}

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.fragment.AbstractScanFragment#onGtin(java.lang.String)
	 */
	@Override
	public void onGtin(String gtin)
	{
		if(onGtinSelectListener != null)
		{
			onGtinSelectListener.onGtinSelected(gtin);
		}
	}
}
